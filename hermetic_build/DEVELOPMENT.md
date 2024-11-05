> [!IMPORTANT]
> All examples assume you are inside the repository root folder.


# Linting

When contributing, ensure your changes to python code have a valid format.

```
python -m pip install black
black {source_file_or_directory}
```

# Install package dependencies

```shell
python -m pip install --require-hashes -r hermetic_build/common/requirements.txt
python -m pip install hermetic_build/common
python -m pip install --require-hashes -r hermetic_build/library_generation/requirements.txt
python -m pip install hermetic_build/library_generation
python -m pip install --require-hashes -r hermetic_build/release_note_generation/requirements.txt
python -m pip install hermetic_build/release_note_generation
```

# Run the integration tests

The integration tests build the docker image declared in
`.cloudbuild/library_generation/library_generation.Dockerfile`, pull GAPIC
repositories, generate the libraries and compare the results with the source
code declared in a "golden branch" of the repo.

It requires docker and python (>= 3.12.0) to be installed.

```shell
python -m unittest hermetic_build/library_generation/tests/integration_tests.py
```

# Run the unit tests

There is one unit test file per component.
Every unit test script ends with `unit_tests.py`.
To avoid specifying them individually, we can use the following command:

```shell
python -m unittest discover -s hermetic_build -p "*unit_tests.py"
```

> [!NOTE]
> The output of this command may look erratic during the first 30 seconds.
> This is normal. After the tests are done, an "OK" message should be shown.

# Run the library generation scripts in your local environment

Although the scripts are designed to run in a Docker container, you can also
run them directly.
This section explains how to run the entrypoint script
(`hermetic_build/library_generation/cli/entry_point.py`).

## Assumptions made by the scripts

### The Hermetic Build's well-known folder
Located in `${HOME}/.library_generation`, this folder is assumed by the scripts
to contain certain tools.

Developers must make sure this folder is properly configured before running the
scripts locally.
Note that this relies on the `HOME` environment variable which is always defined
as per [POSIX env var definition](https://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap08.html).

#### Put the gapic-generator-java jar in its well-known location

1. Run the following command to install gapic-generator-java.

   ```shell
   mvn install -B -ntp -DskipTests -Dclirr.skip -Dcheckstyle.skip
   ```
   This will generate a jar located in `~/.m2/repository/com/google/api/gapic-generator-java/{version}/gapic-generator-java-{version}.jar`

2. Move the jar into its well-known location.

   ```shell
   mv /path/to/jar "${HOME}/.library_generation/gapic-generator-java.jar"
   ```

#### Put the java formatter jar in its well-known location

1. Download google-java-format-{version}-all-deps.jar from [Maven Central](https://central.sonatype.com/artifact/com.google.googlejavaformat/google-java-format)
or [GitHub releases](https://github.com/google/google-java-format/releases).
2. Move the jar into its well-known location.

   ```shell
   mv /path/to/jar "${HOME}/.library_generation/google-java-format.jar"
   ```

## Installing prerequisites

In order to run the generation scripts directly, there are a few tools we
need to install beforehand.

### Install the owl-bot CLI

This requires node.js to be installed.
Check this [installation guide](https://github.com/nvm-sh/nvm?tab=readme-ov-file#install--update-script)
for NVM, Node.js's version manager.

After you install it, you can install the owl-bot CLI with the following
commands:

```shell
git clone https://github.com/googleapis/repo-automation-bots
cd repo-automation-bots/packages/owl-bot
npm i && npm run compile && npm link
owl-bot copy-code --version
```

The key step is `npm link`, which will make the command available in you current
shell session.

## Run the script
The entrypoint script (`hermetic_build/library_generation/cli/entry_point.py`)
allows you to generate a GAPIC repository with a given api definition (proto,
service yaml).

### Download the api definition
For example, from googleapis

```shell
git clone https://github.com/googleapis/googleapis
export api_definitions_path="$(pwd)/googleapis"
```

### Download the repo
For example, google-cloud-java
```shell
git clone https://github.com/googleapis/google-cloud-java
export path_to_repo="$(pwd)/google-cloud-java"
```

### Install the scripts

You can skip this step if you've installed the packages in [Install package dependencies](#install-package-dependencies).

```shell
python -m pip install --require-hashes -r hermetic_build/common/requirements.txt
python -m pip install hermetic_build/common
python -m pip install --require-hashes -r hermetic_build/library_generation/requirements.txt
python -m pip install hermetic_build/library_generation
```

### Run the script

```shell
python hermetic_build/library_generation/cli/entry_point.py generate \
   --repository-path="${path_to_repo}" \
   --api-definitions-path="${api_definitions_path}"
```

# Build the image from source

1. Run the following command to build the image from source

   ```shell
   docker build \
     -f .cloudbuild/library_generation/library_generation.Dockerfile \
     -t local:image-tag \
     .
   ```
   
2. Set the version of gapic-generator-java

   ```shell
   LOCAL_GENERATOR_VERSION=$(mvn \
     org.apache.maven.plugins:maven-help-plugin:evaluate \
     -Dexpression=project.version \
     -pl gapic-generator-java \
     -DforceStdout \
     -q)
   ```

3. Run the image

   ```shell
      # Assume you want to generate the library in the current working directory
      # and the generation configuration is in the same directory.
      docker run \
        --rm \
        --quiet \
        -u "$(id -u):$(id -g)" \
        -v "$(pwd):/workspace" \
        -v /path/to/api-definitions:/workspace/apis \
        -e GENERATOR_VERSION="${LOCAL_GENERATOR_VERSION}" \
        local:image-tag \
        --generation-config-path=/workspace/generation_config_file \
        --library-names=apigee-connect,asset \
        --repository-path=/workspace \
        --api-definitions-path=/workspace/apis
      ```
   Note that if you specify the generator version using environment variable,
   `-e GENERATOR_VERSION="${LOCAL_GENERATOR_VERSION}"` in the above example,
   you should not set `gapic_generator_version` and `protoc_version` in the
   generation configuration because values in the generation configuration will
   take precedence.

# Debug the library generation container
If you are working on changing the way the containers are created, you may want
to inspect the containers to check the setup.
It would be convenient in such case to have a text editor/viewer available. 
You can achieve this by modifying the Dockerfile as follows:

```dockerfile
# install OS tools
RUN apk update && apk add \
	unzip curl rsync openjdk11 jq bash nodejs npm git less vim
```

We add `less` and `vim` as text tools for further inspection.

You can also run a shell in a new container by running:

```shell
docker run \
  --rm \
  -it \
  -u $(id -u):$(id -g) \
  -v /path/to/google-cloud-java:/workspace \
  --entrypoint="bash" \
  $(cat image-id)
```
