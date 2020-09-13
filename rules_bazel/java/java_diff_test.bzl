def run_single_JUnit(name, srcs, tool):
    native.genrule(
        name = name,
        outs = ["%s.zip" % name],
        srcs = srcs,
        cmd = "echo ' $(location %s)' > $@" % tool,
        executable = True,
        tools = [tool],
        local = 1,
    )
