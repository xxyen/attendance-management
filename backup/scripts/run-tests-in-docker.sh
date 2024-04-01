#!/bin/bash
mkdir coverage
docker run -e TZ="America/New_York" --rm -v `pwd`/coverage:/coverage-out  citest scripts/test.sh
