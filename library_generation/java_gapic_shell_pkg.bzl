def java_gapic_assembly_shell_pkg(
        name,
        data = [],
        args = []):
    native.sh_library(
        name = "utilities",
        srcs = ["@gapic_generator_java//library_generation:utilities.sh"],
        deps = ["@bazel_tools//tools/bash/runfiles"],
    )

    native.sh_binary(
        name = name,
        srcs = ["@gapic_generator_java//library_generation:generate_library.sh"],
        deps = [
            ":utilities",
            "@bazel_tools//tools/bash/runfiles",
        ],
        data = data,
        args = args,
    )
