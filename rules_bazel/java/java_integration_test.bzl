def _compare_with_goldens_impl(ctx):
    codegen_srcjar = ctx.outputs.codegen_srcjar
    codegen_comparison_script = ctx.outputs.codegen_comparison_script
    input = ctx.attr.api_target
    srcs = ctx.files.srcs
    script_content = """
    # remove unneeded non-java files, like MANIFEST
    mkdir codegen_tmp
    unzip -j {input} -d codegen_tmp
    unzip -j {input_resource_name} -d  codegen_tmp
    unzip -j {input_test} -d codegen_tmp
    cd codegen_tmp
    zip -r ../{codegen_srcjar} .
    echo ../{codegen_srcjar}
    """.format(
        codegen_srcjar = codegen_srcjar.path,
        input = input[0][JavaInfo].source_jars[0].path,
        input_resource_name = input[1][JavaInfo].source_jars[0].path,
        input_test = input[2][JavaInfo].source_jars[0].path,
    )
    ctx.actions.run_shell(
        inputs = [input[0][JavaInfo].source_jars[0], input[1][JavaInfo].source_jars[0], input[2][JavaInfo].source_jars[0]],
        outputs = [codegen_srcjar],
        command = script_content,
    )

    compare_with_golden_script_content = """
    #!/bin/bash
    cd ${{BUILD_WORKSPACE_DIRECTORY}}
    mkdir test/integration/goldens/.source_code_tmp
    echo 'I am here, dir created!'
    unzip {codegen_srcjar} -d test/integration/goldens/.source_code_tmp
    cd test/integration/goldens/
    diff redis .source_code_tmp
    """.format(
        codegen_srcjar = codegen_srcjar.path,
    )
    ctx.actions.write(
        output = codegen_comparison_script,
        content = compare_with_golden_script_content,
        is_executable = True,
    )
    return [DefaultInfo(executable = codegen_comparison_script)]


compare_with_goldens = rule(
    attrs = {
        "api_target": attr.label_list(),
        "srcs": attr.label_list(
            allow_files = True,
            mandatory = True,
        ), 
    },
    outputs = {
        "codegen_srcjar": "%{name}_codegen.srcjar",
        "codegen_comparison_script": "codegen_comparison_script.sh",
    },
    executable = True,
    implementation = _compare_with_goldens_impl,
    test = True,
)


def integration_test(name, path, deps):
    compare_with_goldens(
        name = name,
        api_target = [path, "%s_resource_name" % path, "%s_test" % path],
        srcs = deps,
    )
