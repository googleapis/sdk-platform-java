def _process_inputs(
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

def java_gapic_assembly_shell_pkg(
        name,
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
        enable_postprocessing = "false",
        data = []):
    native.sh_library(
        name = "utilities",
        srcs = ["@gapic_generator_java//library_generation:utilities.sh"],
    )

    native.sh_binary(
        name = name,
        srcs = ["@gapic_generator_java//library_generation:generate_library.sh"],
        deps = [
            ":utilities",
        ],
        data = data,
        args = _process_inputs(
            proto_path = proto_path,
            destination_path = destination_path,
            gapic_generator_java_version = gapic_generator_java_version,
            protobuf_version = protobuf_version,
            grpc_version = grpc_version,
            proto_only = proto_only,
            gapic_additional_protos = gapic_additional_protos,
            transport = transport,
            rest_numeric_enums = rest_numeric_enums,
            gapic_yaml = gapic_yaml,
            service_config = service_config,
            service_yam = service_yam,
            include_samples = include_samples,
            enable_postprocessing = enable_postprocessing,
        ),
    )
