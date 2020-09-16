def _test_srcjar_impl(ctx):
    print("=========== label name:")
    print(ctx.attr.test_class_name)
    test_class_name = ctx.attr.test_class_name
    arguments = []
    inputs = ctx.files.srcs
    attr = ctx.attr 
    test_runner = ctx.executable.test_runner
    ctx.actions.run(
        inputs = inputs,
        outputs = [ctx.outputs.output],
        arguments = arguments + ["%s" % test_class_name] + ["true"],
        progress_message = "%s: `%s %s`" % (ctx.label, test_runner.path, " ".join(arguments)),
        executable = test_runner,
    )

test_srcjar = rule(
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
        "output_suffix": attr.string(mandatory = False, default = ".testoutput.zip"),
    },
    outputs = {
        "output": "%{name}%{output_suffix}",
    },
    implementation = _test_srcjar_impl,  
)

def run_single_junit(name, test_class_name, srcs):
    test_srcjar(
        name = name,
        test_class_name = test_class_name,
        srcs = srcs,
    )
