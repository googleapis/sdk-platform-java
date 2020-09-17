def _run_unit_impl(ctx):
    test_class_name = ctx.attr.test_class_name
    arguments = [test_class_name]
    inputs = ctx.files.srcs
    output = ctx.outputs.output
    test_runner = ctx.executable.test_runner
    golden_files = ctx.files.golden_files
    print(golden_files)

    command = """
    mkdir local_tmp &&
    echo '\n \n \n hahahhaha \n \n \n' >> {output} &&
    less {output} &&
    ls &&
    ls local_tmp &&
    TEST_CLI_HOME="$(pwd)/local_tmp" \
    {test_runner_path} $@ &&
    ls local_tmp
        """.format(
            test_runner_path = test_runner.path,
            output=output.path,
            golden_files = golden_files,
        )
    print(command)
    ctx.actions.run_shell(
        inputs = inputs,
        outputs = [output],
        arguments = arguments,
        tools = [test_runner],
        command = command,
    )

run_junit = rule(
    attrs = {
        "test_class_name": attr.string(mandatory=True),
        "srcs": attr.label_list(
            allow_files = True,
            mandatory = True,
        ),
        "test_runner": attr.label(
            default = Label("//src/test/java/com/google/api/generator/gapic/dummy:run_single_junit_binary"),
            executable = True,
            cfg = "host",
        ),
        "output_suffix": attr.string(mandatory = False, default = ".testoutput.txt"),
        "golden_files": attr.label_list(mandatory =True, allow_files = True),
    },
    outputs = {
        "output": "%{name}%{output_suffix}",
    },
    implementation = _run_unit_impl,  
)

def update_golden(name, test_class_name, srcs, golden_files):
    run_junit(
        name = name,
        test_class_name = test_class_name,
        srcs = srcs,
        golden_files = golden_files,
    )