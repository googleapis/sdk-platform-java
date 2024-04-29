# Linting

When contributing, ensure your changes to python code have a valid
format.

```
python -m pip install black
black library_generation
```

# Running the integration tests

The integration tests build the docker image declared in
`.cloudbuild/library_generation/library_generation.Dockerfile`, pull GAPIC
repositories, generate the libraries and compares the results with the source
code declared in a "golden branch" of the repo.

It requires docker and python 3.x to be installed.

```
cd library_generation
python -m pip install .
python -m pip install -r requirements.txt
python -m unittest test/integration_tests.py
```

# Running the scripts in your local environment

Although the scripts are designed to be run in a Docker container, you can also
run them directly. This section explains how to run the entrypoint script
(`library_generation/cli/entry_point.py`).

## Installing prerequisites

In order to run the generation scripts directly, there are a few tools we
need to install beforehand.

### Install synthtool

It requires python 3.x to be installed.
You will need to specify a committish of the synthtool repo in order to have
your generation results matching exactly what the docker image would produce.
You can achieve this by inspecting `SYNTHTOOL_COMMITISH` in
`.cloudbuild/library_generation/library_generation.Dockerfile`.

```bash
# obtained from .cloudbuild/library_generation/library_generation.Dockerfile
export SYNTHTOOL_COMMITTISH=6612ab8f3afcd5e292aecd647f0fa68812c9f5b5
```

```bash
git clone https://github.com/googleapis/synthtool
cd synthtool
git checkout "${SYNTHTOOL_COMMITTISH}"
python -m pip install --require-hashes -r requirements.txt
python -m pip install --no-deps -e .
python -m synthtool --version
```

### Install the owl-bot CLI

Requires node.js to be installed.
Check this [installation guide](https://github.com/nvm-sh/nvm?tab=readme-ov-file#install--update-script)
for NVM, Node.js's version manager.

After you install it, you can install the owl-bot CLI with the following
commands:
```bash
git clone https://github.com/googleapis/repo-automation-bots
cd repo-automation-bots/packages/owl-bot
npm i && npm run compile && npm link
owl-bot copy-code --version
```

The key step is `npm link`, which will make the command available in you current
shell session.

## Running the script
The entrypoint script (`library_generation/cli/entry_point.py`) allows you to
update the target repository with the latest changes starting from the
googleapis committish declared in `generation_config.yaml`.

### Download the repo
For example, google-cloud-java
```
git clone https://github.com/googleapis/google-cloud-java
export path_to_repo="$(pwd)/google-cloud-java"
```

### Install the scripts
```
cd library_generation
python -m pip install .
```

### Run the script
```
cd library_generation
python cli/entry_point.py generate --repository-path="${path_to_repo}"
```

