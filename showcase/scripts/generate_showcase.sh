#!/bin/bash
# Generates the showcase library using the docker image, which is built
# from the current state of the repo in order to test local changes.
set -ex

readonly ROOT_DIR="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/../../"
pushd "${ROOT_DIR}"

while [[ $# -gt 0 ]]; do
key="$1"
case "${key}" in
  --replace)
    replace="$2"
    shift
    ;;
  *)
    echo "Invalid option: [$1]"
    exit 1
    ;;
esac
shift
done

if [ -z "${replace}" ]; then
  replace="false"
fi

# download api definitions from googleapis repository
googleapis_commitish=$(grep googleapis_commitish generation_config.yaml | cut -d ":" -f 2 | xargs)
api_def_dir=$(mktemp -d)
git clone https://github.com/googleapis/googleapis.git "${api_def_dir}"

pushd "${api_def_dir}"
git checkout "${googleapis_commitish}"
# for local setups, we avoid permission issues when the docker image
# performs version-dependent operations.
rm -rf ".git/"
popd

# append showcase definitions to googleapis repository
showcase_def_dir=$(mktemp -d)
git clone https://github.com/googleapis/gapic-showcase.git "${showcase_def_dir}"
get_version_from_pom() {
  target_pom="$1"
  key="$2"
  # prints the result to stdout
  grep -e "<${key}>" "${target_pom}" | cut -d'>' -f2 | cut -d'<' -f1
}
# looks at sdk-platform-java/showcase/gapic-showcase/pom.xml to extract the
# version of gapic-showcase
# see https://github.com/googleapis/gapic-showcase/releases
showcase_version=$(get_version_from_pom \
  "showcase/gapic-showcase/pom.xml" "gapic-showcase.version"
)
# compliance_suite.json is a symbolic link outside of the schema folder, so we
# replace it with the actual contents in its original location.
compliance_suite_path="${showcase_def_dir}/schema/google/showcase/v1beta1/compliance_suite.json"
unlink "${compliance_suite_path}"
cp "${showcase_def_dir}/server/services/compliance_suite.json" "${compliance_suite_path}"
# we complete the BUILD.bazel in gapic-showcase with our java_library in order
# to generate the gapic portion.
cat showcase/scripts/resources/BUILD.partial.bazel >> "${showcase_def_dir}/schema/google/showcase/v1beta1/BUILD.bazel"
cp -r "${showcase_def_dir}/schema" "${api_def_dir}/"

echo "building docker image"
DOCKER_BUILDKIT=1 docker build --file .cloudbuild/library_generation/library_generation.Dockerfile --iidfile image-id .

if [[ "${replace}" == "true" ]]; then
  generated_files_dir="${ROOT_DIR}"
else
  generated_files_dir=$(mktemp -d)
  # here we store the generated library location for upstream scripts to use
  # it.
  echo "${generated_files_dir}/showcase" > "${ROOT_DIR}/generated-showcase-location"
  # we prepare the temp folder with the minimal setup to perform an incremental
  # generation.
  cp -r generation_config.yaml showcase/ versions.txt "${generated_files_dir}"
fi

GENERATOR_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout -pl gapic-generator-java)

echo "generating showcase"
workspace_name="/workspace"
docker run \
  --rm \
  -u "$(id -u):$(id -g)" \
  -v "${generated_files_dir}:${workspace_name}" \
  -v "${api_def_dir}:${workspace_name}/googleapis" \
  -e GENERATOR_VERSION="${GENERATOR_VERSION}" \
  "$(cat image-id)" \
  --generation-config-path="${workspace_name}/generation_config.yaml" \
  --library-names="showcase" \
  --api-definitions-path="${workspace_name}/googleapis"

echo "generated showcase library in ${generated_files_dir}"
