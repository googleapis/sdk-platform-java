load("@rules_proto//proto:defs.bzl", "ProtoInfo")

def _process_arguments(
        proto_path,
        destination_path,
        gapic_generator_java_version,
        protobuf_version = None,
        grpc_version = None,
        proto_only = "false",
        gapic_additional_protos = None,
        transport = "grpc",
        rest_numeric_enums = "true",
        gapic_yaml = None,
        service_config = None,
        service_yam = None,
        include_samples = "true",
        enable_postprocessing = "false"):
    args = []
    args.append("--proto_path")
    args.append(proto_path)
    args.append("--destination_path")
    args.append(destination_path)
    args.append("--gapic_generator_version")
    args.append(gapic_generator_java_version)
    if protobuf_version != None:
        args.append("--protobuf_version")
        args.append(protobuf_version)
    if grpc_version != None:
        args.append("--grpc_version")
        args.append(grpc_version)
    args.append("--proto_only")
    args.append(proto_only)
    if gapic_additional_protos != None:
        args.append("--gapic_additional_protos")
        args.append(gapic_additional_protos)
    args.append("--transport")
    args.append(transport)
    args.append("--rest_numeric_enums")
    args.append(rest_numeric_enums)
    if gapic_yaml != None:
        args.append("--gapic_yaml")
        args.append(gapic_yaml)
    if service_config != None:
        args.append("--service_config")
        args.append(service_config)
    if service_yam != None:
        args.append("--service_yam")
        args.append(service_yam)
    args.append("--include_samples")
    args.append(include_samples)
    args.append("--enable_postprocessing")
    args.append(enable_postprocessing)

    return args

def _java_gapic_assembly_pkg_impl(ctx):
    args = _process_arguments(
        proto_path = ctx.attr.proto_path,
        destination_path = ctx.attr.destination_path,
        gapic_generator_java_version = ctx.attr.gapic_generator_version,
    )
    ctx.actions.run(
        inputs = ctx.files.proto,
        outputs = [ctx.outputs.pkg],
        arguments = args,
        progress_message = "Generating from %s" % ctx.attr.proto_path,
        executable = ctx.executable._generation_tool,
    )

java_gapic_assembly_pkg = rule(
    attrs = {
        "proto": attr.label(
            default = Label("@com_google_googleapis//:protos"),
            allow_files = True,
        ),
        "proto_path": attr.string(mandatory = True),
        "destination_path": attr.string(mandatory = True),
        "gapic_generator_version": attr.string(mandatory = True),
        "protobuf_version": attr.string(mandatory = False, default = ""),
        "_generation_tool": attr.label(
            default = Label("@gapic_generator_java//library_generation:generate_library"),
            executable = True,
            cfg = "exec",
        ),
    },
    outputs = {"pkg": "%{name}.tar.gz"},
    implementation = _java_gapic_assembly_pkg_impl,
)
