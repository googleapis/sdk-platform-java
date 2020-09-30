def _junit_output_impl(ctx):
    test_class_name = ctx.attr.test_class_name
    inputs = ctx.files.srcs
    output = ctx.outputs.output
    test_runner = ctx.executable.test_runner

    command = """
    mkdir local_tmp  
    TEST_OUTPUT_HOME="$(pwd)/local_tmp" \
    {test_runner_path} $@
    cd local_tmp 
    # Zip all files under local_tmp with all nested parent folders except for local_tmp itself.
    # Zip files because there are cases that one Junit test can produce multiple goldens.
    zip -r ../{output} .
    """.format(
        test_runner_path = test_runner.path,
        output=output.path,
    )

    ctx.actions.run_shell(
        inputs = inputs,
        outputs = [output],
        arguments = [test_class_name],
        tools = [test_runner],
        command = command,
    )

junit_output_zip = rule(
    attrs = {
        "test_class_name": attr.string(mandatory=True),
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
        "output": "%{name}%.zip",
    },
    implementation = _junit_output_impl,  
)
    
def _overwritten_golden_impl(ctx):
    script_content = """
    #!/bin/bash
    cd ${{BUILD_WORKSPACE_DIRECTORY}}
    unzip -ao {unit_test_results} -d src/test/java
    """.format(
        unit_test_results = ctx.file.unit_test_results.path,
    )
    ctx.actions.write(
        output = ctx.outputs.bin,
        content = script_content,
        is_executable = True,
    )
    return [DefaultInfo(executable = ctx.outputs.bin)]


overwritten_golden = rule(
    attrs = {
        "unit_test_results": attr.label(
            mandatory = True,
            allow_single_file = True),
    },
    outputs = {
        "bin": "%{name}.sh",
    },
    executable = True,
    implementation = _overwritten_golden_impl,
)

def update_golden(name, test_class_name, srcs):
    junit_output_name = "%s_output" % name
    junit_output_zip(
        name = junit_output_name,
        test_class_name = test_class_name,
        test_runner = "//:junit_runner",
        srcs = srcs,
    )
    overwritten_golden(
        name = name,
        unit_test_results = ":%s" % junit_output_name
    )
