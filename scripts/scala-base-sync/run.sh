#!/usr/bin/env bash

ORIGIN_TAG="v0.7.0"
REPO_URL="https://github.com/cerst/scala-base.g8"
SCRIPT_FOLDER_DIR="$( cd "$(dirname "$0")" ; pwd -P )"

INCLUDE_FILES="${SCRIPT_FOLDER_DIR}/include-files"
PROJECT_ROOT_DIR="${SCRIPT_FOLDER_DIR}/../.."
TMP_DIR="${SCRIPT_FOLDER_DIR}/tmp"

INPUT_DIR="${TMP_DIR}/src/main/g8"

echo "----- DEBUG-INFO -----"
echo "Include files: $INCLUDE_FILES"
echo "Input dir: $INPUT_DIR"
echo "Output dir: $PROJECT_ROOT_DIR"
echo "Repo Url: $REPO_URL"
echo "Tmp dir: $TMP_DIR"
echo "----- DEBUG-INFO -----"

rm -rf ${TMP_DIR}
git clone ${REPO_URL} ${TMP_DIR}

echo "Checking script tags"
LATEST_TAG=$( cd ${TMP_DIR} && git describe --abbrev=0 --tags)
if [[ "$LATEST_TAG" != "$ORIGIN_TAG" ]]; then
    echo "Origin tag for this script ($ORIGIN_TAG) differs from latest one ($LATEST_TAG)."
    echo "Please update the scala-base-sync folder manually and retry (don't forget to remove unwanted files again)"
    exit 1
fi

echo "Copying files"
rsync --files-from=${INCLUDE_FILES} ${INPUT_DIR} ${PROJECT_ROOT_DIR}

echo "Clean-up tmp dir"
rm -rf ${TMP_DIR}

echo "Checking for sbt plugin updates not covered by scala-base repo"
(cd ${PROJECT_ROOT_DIR} && sbt --error ";reload plugins;dependencyUpdatesReport")
ACTUAL_UPDATES="$PROJECT_ROOT_DIR/project/target/dependency-updates.txt"
NO_UPDATES="$SCRIPT_FOLDER_DIR/no-dependency-updates.txt"

cmp --silent ${NO_UPDATES} ${ACTUAL_UPDATES} \
&& echo "All sbt plugins up-to-date" \
|| (echo -e "Please consider updating scala-base as well!" && cat ${ACTUAL_UPDATES})
