# Generate a repository containing GAPIC Client Libraries

The script, `generate_repo.py`, allows you to generate a repository containing
GAPIC client libraries (a monorepo, for example, google-cloud-java) from a
configuration file.

## Environment

- OS: Linux
- Java runtime environment (8 or above)
- Apache Maven (used in formatting source code)
- Python (3.11.6 or above)

## Prerequisite

In order to generate a version for each library, a versions.txt has to exist
in `repository_path`.
Please refer to [Repository path](#repository-path--repositorypath---optional) for more information.

## Parameters to generate a repository using `generate_repo.py`

### Generation configuration yaml (`generation_config_yaml`)

A path to a configuration file containing parameters to generate the repository.
Please refer [Configuration to generate a repository](#configuration-to-generate-a-repository) 
for more information.

### Target library API shortname (`target_library_api_shortname`), optional

If specified, the libray whose `api_shortname` equals to `target_library_api_shortname`
will be generated; otherwise all libraries in the configuration file will be
generated.
This can be useful when you just want to generate one library for debugging
purposes.

The default value is an empty string, which means all libraries will be generated.

### Repository path (`repository_path`), optional

The path to where the generated repository goes.

The default value is the current working directory when running the script.
For example, `cd google-cloud-java && python generate_repo.py ...` without
specifying the `--repository_path` option will modify the `google-cloud-java`
repository the user `cd`'d into.

Note that versions.txt has to exist in `repository_path` in order to generate
right version for each library.
Please refer [here](go/java-client-releasing#versionstxt-manifest) for more info
of versions.txt.

## Output of `generate_repo.py`

For each module (e.g. `google-cloud-java/java-asset`), the following files/folders
will be created/modified:

| Name                        | Notes                                                                    |
|:----------------------------|:-------------------------------------------------------------------------|
| google-*/                   | Source code generated by gapic-generator-java                            |
| google-*/pom.xml            | Only be generated if it does not exist                                   |
| grpc-*/                     | Source code generated by grpc generator, one per each version            |
| grpc-*/pom.xml              | Only be generated if it does not exist                                   |
| proto-*/                    | Source code generated by Protobuf default compiler, one per each version |
| proto-*/pom.xml             | Only be generated if it does not exist                                   |
| samples/snippets/generated/ | Only be generated if `include_samples` is set to true                    |
| google-*-bom/pom.xml        | Library BOM, only be generated if it does not exist                      |
| pom.xml                     | Library parent BOM, only be generated if it does not exist               |
| .repo-metadata.json         | Always generated from inputs                                             |
| .OwlBot.yaml                | Only be generated from a template if it does not exist                   |
| owlbot.py                   | Only be generated from a template if it does not exist                   |
| README.md                   | Always generated from inputs                                             |
| gapic-libraries-bom/pom.xml | Always generated from inputs                                             |
| pom.xml (repo root dir)     | Always generated from inputs                                             |
| versions.txt                | New entries will be added if they don’t exist                            |


## Configuration to generate a repository

There are three levels of parameters in the configuration: repository level,
library level and GAPIC level.

### Repository level parameters

The repository level parameters define the version of API definition (proto)
and tools.
They are shared by library level parameters.

| Name                    | Required | Notes                                        |
|:------------------------|:--------:|:---------------------------------------------|
| gapic_generator_version |   Yes    |                                              |
| protobuf_version        |    No    | inferred from the generator if not specified |
| grpc_version            |    No    | inferred from the generator if not specified |
| googleapis-commitish    |   Yes    |                                              |
| owlbot-cli-image        |   Yes    |                                              |
| synthtool-commitish     |   Yes    |                                              |
| template_excludes       |   Yes    |                                              |

### Library level parameters

The library level parameters define how to generate a (multi-versions) GAPIC
library.
They are shared by all GAPICs of a library.

| Name                  | Required | Notes                                                                              |
|:----------------------|:--------:|:-----------------------------------------------------------------------------------|
| api_shortname         |   Yes    |                                                                                    |
| api_description       |   Yes    |                                                                                    |
| name_pretty           |   Yes    |                                                                                    |
| product_docs          |   Yes    |                                                                                    |
| library_type          |    No    | `GAPIC_AUTO` if not specified                                                      |
| release_level         |    No    | `preview` if not specified                                                         |
| api_id                |    No    | `{api_shortname}.googleapis.com` if not specified                                  |
| api_reference         |    No    |                                                                                    |
| codeowner_team        |    No    |                                                                                    |
| client_documentation  |    No    |                                                                                    |
| distribution_name     |    No    | `{group_id}:google-{cloud_prefix}{library_name}` if not specified                  |
| excluded_poms         |    No    |                                                                                    |
| excluded_dependencies |    No    |                                                                                    |
| googleapis_commitish  |    No    | use repository level `googleapis_commitish` if not specified.                      |
| group_id              |    No    | `com.google.cloud` if not specified                                                |
| issue_tracker         |    No    |                                                                                    |
| library_name          |    No    | `api_shortname` is not specified. This value should be unique among all libraries. |
| rest_documentation    |    No    |                                                                                    |
| rpc_documentation     |    No    |                                                                                    |
| cloud_api             |    No    | `true` if not specified                                                            |
| requires-billing      |    No    | `true` if not specified                                                            |

Note that `cloud_prefix` is `cloud-` if `cloud_api` is `true`; empty otherwise.

### GAPIC level parameters

The GAPIC level parameters define how to generate a GAPIC library.

| Name       | Required | Notes                                     |
|:-----------|:--------:|:------------------------------------------|
| proto_path |   Yes    | versioned proto_path starts with `google` |

### An example of generation configuration

```yaml
gapic_generator_version: 2.34.0
protobuf_version: 25.2
googleapis_commitish: 1a45bf7393b52407188c82e63101db7dc9c72026
owlbot_cli_image: sha256:623647ee79ac605858d09e60c1382a716c125fb776f69301b72de1cd35d49409
synthtool_commitish: 6612ab8f3afcd5e292aecd647f0fa68812c9f5b5
destination_path: google-cloud-java
template_excludes:
  - ".github/*"
  - ".kokoro/*"
  - "samples/*"
  - "CODE_OF_CONDUCT.md"
  - "CONTRIBUTING.md"
  - "LICENSE"
  - "SECURITY.md"
  - "java.header"
  - "license-checks.xml"
  - "renovate.json"
  - ".gitignore"
libraries:
  - api_shortname: apigeeconnect
    name_pretty: Apigee Connect
    product_documentation: "https://cloud.google.com/apigee/docs/hybrid/v1.3/apigee-connect/"
    api_description: "allows the Apigee hybrid management plane to connect securely to the MART service in the runtime plane without requiring you to expose the MART endpoint on the internet."
    release_level: "stable"
    library_name: "apigee-connect"
    GAPICs:
      - proto_path: google/cloud/apigeeconnect/v1

  - api_shortname: cloudasset
    name_pretty: Cloud Asset Inventory
    product_documentation: "https://cloud.google.com/resource-manager/docs/cloud-asset-inventory/overview"
    api_description: "provides inventory services based on a time series database. This database keeps a five week history of Google Cloud asset metadata. The Cloud Asset Inventory export service allows you to export all asset metadata at a certain timestamp or export event change history during a timeframe."
    library_name: "asset"
    client_documentation: "https://cloud.google.com/java/docs/reference/google-cloud-asset/latest/overview"
    distribution_name: "com.google.cloud:google-cloud-asset"
    release_level: "stable"
    issue_tracker: "https://issuetracker.google.com/issues/new?component=187210&template=0"
    api_reference: "https://cloud.google.com/resource-manager/docs/cloud-asset-inventory/overview"
    GAPICs:
      - proto_path: google/cloud/asset/v1
      - proto_path: google/cloud/asset/v1p1beta1
      - proto_path: google/cloud/asset/v1p2beta1
      - proto_path: google/cloud/asset/v1p5beta1
      - proto_path: google/cloud/asset/v1p7beta1
```

## An example to generate a repository using `generate_repo.py`

```bash
# install python module (allows the `library_generation` module to be imported from anywhere)
python -m pip install -r library_generation/requirements.in
# generate the repository
python -m library_generation/generate_repo.py generate \
--generation-config-yaml=/path/to/config-file \
--repository-path=/path/to/repository
```

## An example of generated repository using `generate_repo.py`

If you run `generate_repo.py` with the example [configuration](#an-example-of-generation-configuration)
shown above, the repository structure is:
```
$repository_path
|_gapic-libraries-bom
|    |_pom.xml
|_java-apigee-connect
|    |_google-cloud-apigee-connect
|    |    |_src
|    |    |_pom.xml
|    |_google-cloud-apigee-connect-bom
|    |    |_pom.xml
|    |_grpc-google-cloud-apigee-connect-v1
|    |    |_src
|    |    |_pom.xml
|    |_proto-google-cloud-apigee-connect-v1
|    |    |_src
|    |    |_pom.xml
|    |_samples
|    |    |_snippets
|    |    |    |_generated
|    |_.OwlBot.yaml
|    |_.repo-metadata.json
|    |_owlbot.py
|    |_pom.xml
|    |_README.md
|_java-asset
|    |_google-cloud-asset
|    |    |_src
|    |    |_pom.xml
|    |_google-cloud-asset-bom
|    |    |_pom.xml
|    |_grpc-google-cloud-asset-v1
|    |    |_src
|    |    |_pom.xml
|    |_grpc-google-cloud-asset-v1p1beta1
|    |    |_src
|    |    |_pom.xml
|    |_grpc-google-cloud-asset-v1p2beta1
|    |    |_src
|    |    |_pom.xml
|    |_grpc-google-cloud-asset-v1p5beta1
|    |    |_src
|    |    |_pom.xml
|    |_grpc-google-cloud-asset-v1p7beta1
|    |    |_src
|    |    |_pom.xml
|    |_proto-google-cloud-asset-v1
|    |    |_src
|    |    |_pom.xml
|    |_proto-google-cloud-asset-v1p1beta1
|    |    |_src
|    |    |_pom.xml
|    |_proto-google-cloud-asset-v1p2beta1
|    |    |_src
|    |    |_pom.xml
|    |_proto-google-cloud-asset-v1p5beta1
|    |    |_src
|    |    |_pom.xml
|    |_proto-google-cloud-asset-v1p7beta1
|    |    |_src
|    |    |_pom.xml
|    |_samples
|    |    |_snippets
|    |    |    |_generated
|    |_.OwlBot.yaml
|    |_.repo-metadata.json
|    |_owlbot.py
|    |_pom.xml
|    |_README.md
|_pom.xml
|_versions.txt
```

# Owlbot Java Postprocessor

We have transferred the
[implementation](https://github.com/googleapis/synthtool/tree/59fe44fde9866a26e7ee4e4450fd79f67f8cf599/docker/owlbot/java)
of Java Owlbot Postprocessor into `sdk-platform-java/library_generation`. The
implementation in synthtool is still valid and used by other services, so we
have two versions during a transition period.

## Reflecting changes in synthtool/docker/owlbot/java into this repository
The transfer was not a verbatim copy, it rather had modifications:
 * `format-source.sh` was replaced by a call to `mvn fmt:format`
 * `entrypoint.sh` was modified to have input arguments and slightly modified
   the way the helper scripts are called
 * Other helper scripts were modified to have input arguments.
 * `fix-poms.py` modified the way the monorepo is detected

All these modifications imply that whenever we want to reflect a change from the
original owlbot in synthtool we may be better off modifying the affected source
files one by one. The mapping is from
[`synthtool/docker/owlbot/java`](https://github.com/googleapis/synthtool/tree/59fe44fde9866a26e7ee4e4450fd79f67f8cf599/docker/owlbot/java)
to
[`sdk-platform-java/library_generation/owlbot`](https://github.com/googleapis/sdk-platform-java/tree/move-java-owlbot/library_generation/owlbot)
