#!/usr/bin/env bash

# greadlink because readlink has no -f on Mac
SCRIPT_DIR_ABS_PATH="$(dirname $(greadlink -f "$0"))"
PROJECT_ROOT_ABS_PATH="${SCRIPT_DIR_ABS_PATH}/../.."

# compile IS required before test
# https://github.com/plokhotnyuk/jsoniter-scala#known-issues
SBT_STAGE_TASKS="\
;clean
;headerCreate
;test:headerCreate
;compile
;test
;doc
;paradox"

# TODO: sbt publish tasks
SBT_PUBLISH_TASKS="\
"



echo_success() {
    echo "" \
    && echo "=================" \
    && echo "Publish succeeded" \
    && echo "================="
}

git_ensure_master_branch() {
    echo "==========================="
    echo "Ensure Git branch is master"
    echo "==========================="
    GIT_BRANCH=$(git symbolic-ref --short HEAD)
    if [[ ${GIT_BRANCH} -ne "master" ]]; then
        echo "You must be on Git branch 'master' to publish"
        exit 1
    else
        echo "Ok"
    fi
}

readAndConfirmVersion() {
    # pipe sbt output to /dev/null as it otherwise interferes with the variable assignment using cat
    PROJECT_VERSION=$( cd ${PROJECT_ROOT_ABS_PATH} && sbt versionToFile > /dev/null && cat target/version-to-file/version ) \
    && echo "" \
    && echo "Do you want to publish v$PROJECT_VERSION (y/n)?" \
    && while true; do
        read yn
            case ${yn} in
                [Yy]* ) break;;
                [Nn]* ) echo "Aborted" && exit 0;;
                * ) echo "Please answer yes or no.";;
            esac
    done
}

# TODO: clone the gh-pages repo, put the doc in it and push
sbt_run_publish_tasks() {
    echo "" \
    && echo "=====================" \
    && echo "Run Sbt publish tasks" \
    && echo ${SBT_PUBLISH_TASKS} \
    && echo "=====================" \
    && $( cd ${PROJECT_ROOT_ABS_PATH} && sbt --warn ${SBT_PUBLISH_TASKS} )
}

sbt_run_stage_stage_tasks() {
    echo "" \
    && echo "===================" \
    && echo "Run Sbt stage tasks" \
    && echo "${SBT_STAGE_TASKS}" \
    && echo "===================" \
    && ( cd ${PROJECT_ROOT_ABS_PATH} && sbt --warn ${SBT_STAGE_TASKS} )
}

sbt_run_stage_stage_tasks \
    && readAndConfirmVersion \
    && git_ensure_master_branch \
    && (echo "Publish not yet implemented" && exit 1)
