def run_single_JUnit(name, srcs, tool):
    print(name)
    output = "%s.zip" % name
    print(output)
    native.genrule(
        name = name,
        outs = [output],
        srcs = srcs,
        cmd = "echo ' $(location %s)' > $@" % output,
        executable = True,
        tools = [tool],
        local = 1,
    )
