#!/bin/bash
# This script allows generation of libraries that are composed of more than one
# service version. It is achieved by calling `generate_library.sh` without
# postprocessing for all service versions and then calling
# postprocess_library.sh at the end, once all libraries are ready
