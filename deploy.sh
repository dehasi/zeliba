#!/usr/bin/env bash

gradle -PreleaseVersion=$(date +%Y.%m.%d) --info publish

# login https://oss.sonatype.org/
