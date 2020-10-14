def _compare_with_goldens_test_impl(ctx):
    diff_output = ctx.outputs.diff_output
    check_diff_script = ctx.outputs.check_diff_script
    gapic_library = ctx.attr.gapic_library
    resource_name_library = ctx.attr.resource_name_library
    test_library = ctx.attr.test_library
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
    touch {check_diff_script}
    if [ -s {diff_output} ]
    then
        echo 'exit 1' >> {check_diff_script}
    fi
    """.format(
        check_diff_script = check_diff_script.path,
        diff_output = diff_output.path,
        input = gapic_library[JavaInfo].source_jars[0].path,
        input_resource_name = resource_name_library[JavaInfo].source_jars[0].path,
        input_test = test_library[JavaInfo].source_jars[0].path,
        api_name = api_name
    )
    ctx.actions.run_shell(
        inputs = srcs + [
            gapic_library[JavaInfo].source_jars[0], 
            resource_name_library[JavaInfo].source_jars[0], 
            test_library[JavaInfo].source_jars[0],
        ],
        outputs = [diff_output, check_diff_script],
        command = script,
    )
    return [DefaultInfo(executable = check_diff_script)]


compare_with_goldens_test = rule(
    attrs = {
        "gapic_library": attr.label(),
        "resource_name_library": attr.label(),
        "test_library": attr.label(),
        "srcs": attr.label_list(
            allow_files = True,
            mandatory = True,
        ), 
    },
    outputs = {
        "diff_output": "diff_output.txt",
        "check_diff_script": "check_diff_script.sh",
    },
    implementation = _compare_with_goldens_test_impl,
    test = True,
)


def integration_test(name, target, data):
    compare_with_goldens_test(
        name = name,
        gapic_library = target,
        resource_name_library = "%s_resource_name" % target,
        test_library = "%s_test" % target,
        srcs = data,
    )
