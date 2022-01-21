def _overwrite_golden_impl(ctx):
    # Extract the Java source files from the generated 3 srcjars from API bazel target,
    # and put them in the temporary folder `codegen_tmp`, zip as `goldens_output_zip`.
    # Overwrite the goldens folder e.g `test/integration/goldens/redis` with the
    # code generation in `goldens_output_zip`.

    gapic_library = ctx.attr.gapic_library
    resource_name_library = ctx.attr.resource_name_library
    test_library = ctx.attr.test_library
    srcs = ctx.files.srcs

    # Convert the name of bazel rules e.g. `redis_update` to `redis`
    # because we will need to overwrite the goldens files in `redis` folder.
    api_name = "_".join(ctx.attr.name.split("_")[:-1])
    goldens_output_zip = ctx.outputs.goldens_output_zip

    script = """
    mkdir codegen_tmp
    unzip {input} -d codegen_tmp
    unzip {input_resource_name} -d  codegen_tmp
    unzip {input_test} -d codegen_tmp
    cd codegen_tmp
    # Remove unneeded non-Java files, like MANIFEST
    rm -rf $(find ./ -type f ! -name '*.java' -a ! -name '*gapic_metadata.json')
    rm -rf $(find ./ -type f -name 'PlaceholderFile.java')
    rm -r $(find ./ -type d -empty)
    zip -r ../{goldens_output_zip} .
    """.format(
        goldens_output_zip = goldens_output_zip.path,
        input = gapic_library[JavaInfo].source_jars[0].path,
        input_resource_name = resource_name_library[JavaInfo].source_jars[0].path,
        input_test = test_library[JavaInfo].source_jars[0].path,
    )

    ctx.actions.run_shell(
        inputs = srcs + [
            gapic_library[JavaInfo].source_jars[0],
            resource_name_library[JavaInfo].source_jars[0],
            test_library[JavaInfo].source_jars[0],
        ],
        outputs = [goldens_output_zip],
        command = script,
    )

    # Overwrite the goldens.
    golden_update_script_content = """
    cd ${{BUILD_WORKSPACE_DIRECTORY}}
    # Clear out existing Java and JSON files.
    rm -r $(find test/integration/goldens/{api_name}/ -name '*.java')
    rm -r $(find test/integration/goldens/{api_name}/ -name 'gapic_metadata.json')
    rm -r $(find ./ -type d -empty)
    unzip -ao {goldens_output_zip} -d test/integration/goldens/{api_name}
    """.format(
        goldens_output_zip = goldens_output_zip.path,
        api_name = api_name,
    )
    ctx.actions.write(
        output = ctx.outputs.golden_update_script,
        content = golden_update_script_content,
        is_executable = True,
    )
    return [DefaultInfo(executable = ctx.outputs.golden_update_script)]

overwrite_golden = rule(
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
        "goldens_output_zip": "%{name}.zip",
        "golden_update_script": "%{name}.sh",
    },
    executable = True,
    implementation = _overwrite_golden_impl,
)

def golden_update(name, target, data):
    overwrite_golden(
        name = name,
        gapic_library = target,
        resource_name_library = "%s_resource_name" % target,
        test_library = "%s_test" % target,
        srcs = data,
    )
