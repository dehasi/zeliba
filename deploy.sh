#!/usr/bin/env bash

gradle -PreleaseVersion=$(date +%Y.%m.%d) --info publish

# login https://oss.sonatype.org/
# migrated to https://identity.sonatype.com/
# https://central.sonatype.com/publishing/namespaces