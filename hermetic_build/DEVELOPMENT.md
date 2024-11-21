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

# Build the image from source

1. Run the following command to build the image from source

   ```shell
   DOCKER_BUILDKIT=1 docker build \
     -f .cloudbuild/library_generation/library_generation.Dockerfile \
     -t local:image-tag \
     .
   ```
   Please note that the build only works when using the new [Docker BuildKit](https://docs.docker.com/build/buildkit/)
   (enabled through the `DOCKER_BUILDKIT` variable).
   
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
