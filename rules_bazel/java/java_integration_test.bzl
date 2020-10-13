def _compare_with_goldens_test_impl(ctx):
    diff_output = ctx.outputs.diff_output
    check_diff_script = ctx.outputs.check_diff_script
    input = ctx.attr.api_target
    srcs = ctx.files.srcs
    api_name = ctx.attr.name
    
    script = """
    mkdir codegen_tmp
    unzip -j {input} -d codegen_tmp
    unzip -j {input_resource_name} -d  codegen_tmp
    unzip -j {input_test} -d codegen_tmp
    cd codegen_tmp
    # Remove unneeded non-Java files, like MANIFEST
    rm -rf $(find . -type f ! -name "*.java")
    cd ..
    diff codegen_tmp test/integration/goldens/{api_name}/ > {diff_output}
    """.format(
        diff_output = diff_output.path,
        input = input[0][JavaInfo].source_jars[0].path,
        input_resource_name = input[1][JavaInfo].source_jars[0].path,
        input_test = input[2][JavaInfo].source_jars[0].path,
        api_name = api_name
    )
    ctx.actions.run_shell(
        inputs = srcs + [
            input[0][JavaInfo].source_jars[0], 
            input[1][JavaInfo].source_jars[0], 
            input[2][JavaInfo].source_jars[0],
        ],
        outputs = [diff_output],
        command = script,
    )


    check_diff_script_content = """
    cd ${{BUILD_WORKSPACE_DIRECTORY}}
    if [ -s {diff_output} ]
    then
        cat {diff_output}
        exit 1 
    else
        exit 0 
    fi
    """.format(
        diff_output = diff_output.path,
    )
    ctx.actions.write(
        output = check_diff_script,
        content = check_diff_script_content,
        is_executable = True,
    )
    return [DefaultInfo(executable = check_diff_script)]


compare_with_goldens_test = rule(
    attrs = {
        "api_target": attr.label_list(),
        "srcs": attr.label_list(
            allow_files = True,
            mandatory = True,
        ), 
    },
    outputs = {
        "diff_output": "%{name}_diff.txt",
        "check_diff_script": "check_diff_script.sh",
    },
    implementation = _compare_with_goldens_test_impl,
    test = True,
)


def integration_test(name, target, data):
    compare_with_goldens_test(
        name = name,
        api_target = [
            target, 
            "%s_resource_name" % target,
            "%s_test" % target
        ],
        srcs = data,
    )
