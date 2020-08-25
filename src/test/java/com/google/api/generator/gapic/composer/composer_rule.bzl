load("@rules_java//java:defs.bzl", "java_test", "java_binary")

def composer_test(
        name, 
        srcs, 
        test_class, 
        golden,
        deps):
    # This creates the first target, which runs codegen and compares it with goldens.
    java_test(
        name = name,
        srcs =  srcs,
        test_class = test_class,
        args = [golden],
        attrs = {"srcs": attr.label_list(allow_files = [".rst"])},
        deps = deps,
    )
    # This creates the secondary target, which update the golden file based on
    # the generated code above.
    java_binary(
        name = name + ".update",
        srcs = srcs,
        test_class = test_class,
        attrs = {
            "output": attr.label(),
        },        
        deps = deps,
    )
