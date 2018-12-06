#!/usr/bin/env bash

set -e

exec java -jar build/libs/app.zip app $@
