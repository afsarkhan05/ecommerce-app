#!/usr/bin/env bash

set -f; IFS='.'
set -- $JAVA_ALPINE_VERSION

JV=${1:-9};

set +f; unset IFS

java $JAVA_OPTS -jar $JAR_PATH $MAIN_CLASS