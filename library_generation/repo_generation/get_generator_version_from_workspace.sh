#!/usr/bin/env bash
curl --silent 'https://raw.githubusercontent.com/googleapis/googleapis/master/WORKSPACE' | perl -nle 'print $1 if m/_gapic_generator_java_version\s+=\s+\"(.+)\"/'