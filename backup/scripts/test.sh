#!/bin/bash

./gradlew build || exit 1
./gradlew cloverGenerateReport || exit 1
scripts/coverage_summary.sh
cp -r shared/build/reports/clover/html/* /coverage-out/ || exit 1