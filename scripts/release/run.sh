#!/usr/bin/env bash

# greadlink because readlink has no -f on Mac
SCRIPT_DIR_ABS_PATH="$(dirname $(greadlink -f "$0"))"
PROJECT_ROOT_ABS_PATH="${SCRIPT_DIR_ABS_PATH}/../.."

PRE_RELEASE_TASKS="\
$(cat ${SCRIPT_DIR_ABS_PATH}/pre_release_tasks)
;versionToFile"
RELEASE_TASKS=$(cat ${SCRIPT_DIR_ABS_PATH}/release_tasks)


run_pre_release_tasks() {
    echo "" \
    && echo "=========================" \
    && echo "Run sbt PRE-release tasks" \
    && echo "${PRE_RELEASE_TASKS}" \
    && echo "=========================" \
    && ( cd ${PROJECT_ROOT_ABS_PATH} && sbt --warn ${PRE_RELEASE_TASKS} )
}

read_and_confirm_version() {
    # pipe sbt output to /dev/null as it otherwise interferes with the variable assignment using cat
    PROJECT_VERSION=$(cat ${PROJECT_ROOT_ABS_PATH}/target/version-to-file/version) \
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

ensure_master_branch() {
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

run_release_tasks() {
    echo "" \
    && echo "=====================" \
    && echo "Run sbt release tasks" \
    && echo "${RELEASE_TASKS}" \
    && echo "=====================" \
    && ( cd ${PROJECT_ROOT_ABS_PATH} && sbt --warn ${RELEASE_TASKS} )
}


echo_success() {
    echo "" \
    && echo "=================" \
    && echo "Release succeeded" \
    && echo "================="
}

run_pre_release_tasks \
    && read_and_confirm_version \
    && ensure_master_branch \
    && run_release_tasks \
    && echo_success
