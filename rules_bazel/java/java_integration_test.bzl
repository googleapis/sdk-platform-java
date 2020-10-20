def _compare_with_goldens_test_impl(ctx):
    # Extract the Java source files from the generated 3 srcjars from API bazel target, 
    # and put them in the temporary folder `codegen_tmp`.
    # Compare the `codegen_tmp` with the goldens folder e.g `test/integration/goldens/redis`
    # and save the differences in output file `diff_output.txt`.

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
    # Bash `diff` command will return exit code 1 when there are differences between the two
    # folders. So we explicitly `exit 0` after the diff command to avoid build failure.
    exit 0
    """.format(
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
        outputs = [diff_output],
        command = script,
    )

    # Check the generated diff_output file, if it is empty, that means there is no difference  
    # between generated source code and goldens files, test should pass. If it is not empty, then 
    # test will fail by exiting 1.

    check_diff_script_content = """
    # This will not print diff_output to the console unless `--test_output=all` option
    # is enabled, it only emits the comparison results to the test.log.
    # We could not copy the diff_output.txt to the test.log ($XML_OUTPUT_FILE) because that
    # file is not existing at the moment. It is generated once test is finished.
    cat $PWD/test/integration/diff_output.txt
    if [ -s $PWD/test/integration/diff_output.txt ]
    then
        exit 1
    fi
    """

    ctx.actions.write(
        output = check_diff_script,
        content = check_diff_script_content,
    )
    runfiles = ctx.runfiles(files = [ctx.outputs.diff_output])
    return [DefaultInfo(executable = check_diff_script, runfiles = runfiles)]


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
    # Bazel target `java_gapic_library` will generate 3 source jars including the
    # the source Java code of the gapic_library, resource_name_library and test_library.
    compare_with_goldens_test(
        name = name,
        gapic_library = target,
        resource_name_library = "%s_resource_name" % target,
        test_library = "%s_test" % target,
        srcs = data,
    )
