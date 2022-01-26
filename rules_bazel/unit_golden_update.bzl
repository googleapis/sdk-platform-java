# Macro expanding to two rules:
#   (1) java_library(): compiles given .java files
#   (2) sh_binary(): script to update .golden (depends on (1))
def golden_update(name, srcs, test_class, data, deps):
    srcs_name = "%s_srcs" % name
    native.java_library(
        name = srcs_name,
        srcs = srcs,
        deps = deps,
    )

    native.sh_binary(
        name = name,
        srcs = ["//scripts:update_unit_golden.sh"],
        args = [test_class],
        data = data + deps + [
            "//src/test/java/com/google/api/generator/test/framework:junit_runner",
            srcs_name,
        ],
    )
