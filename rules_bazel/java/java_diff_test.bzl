def _overwrite_golden_impl(ctx):
    test_class = ctx.attr.test_class
    inputs = ctx.files.srcs
    goldens_output_zip = ctx.outputs.goldens_output_zip
    test_runner = ctx.executable.test_runner

    # Generate the goldens from tests.
    generate_goldens_script = """
    mkdir local_tmp
    TEST_OUTPUT_HOME="$(pwd)/local_tmp" {test_runner_path} $@
    cd local_tmp
    # Zip all files under local_tmp with all nested parent folders except for local_tmp itself.
    # Zip files because there are cases that one Junit test can produce multiple goldens.
    zip -r ../{goldens_output_zip} .
    """.format(
        test_runner_path = test_runner.path,
        goldens_output_zip = goldens_output_zip.path,
    )

    ctx.actions.run_shell(
        inputs = inputs,
        outputs = [goldens_output_zip],
        arguments = [test_class],
        tools = [test_runner],
        command = generate_goldens_script,
    )

    # Overwrite the goldens.
    golden_update_script_content = """
    #!/bin/bash
    cd ${{BUILD_WORKSPACE_DIRECTORY}}
    unzip -ao {goldens_output_zip} -d src/test/java
    """.format(
        goldens_output_zip = goldens_output_zip.path,
    )
    ctx.actions.write(
        output = ctx.outputs.golden_update_script,
        content = golden_update_script_content,
        is_executable = True,
    )
    return [DefaultInfo(executable = ctx.outputs.golden_update_script)]

overwrite_golden = rule(
    attrs = {
        "test_class": attr.string(mandatory = True),
        "srcs": attr.label_list(
            allow_files = True,
            mandatory = True,
        ),
        "test_runner": attr.label(
            mandatory = True,
            executable = True,
            cfg = "host",
        ),
    },
    outputs = {
        "goldens_output_zip": "%{name}.zip",
        "golden_update_script": "%{name}.sh",
    },
    executable = True,
    implementation = _overwrite_golden_impl,
)

def golden_update(
        name,
        srcs,
        test_class,
        data = [],
        deps = []):
    golden_junit_runner_name = "%s_junit_runner" % name
    native.java_binary(
        name = golden_junit_runner_name,
        srcs = srcs,
        data = data,
        main_class = "com.google.api.generator.test.framework.SingleJUnitTestRunner",
        deps = ["//src/test/java/com/google/api/generator/test/framework:junit_runner"] + deps,
    )

    overwrite_golden(
        name = name,
        test_class = test_class,
        test_runner = ":%s" % golden_junit_runner_name,
        srcs = srcs + data,
    )
