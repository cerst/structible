#!/usr/bin/env bash

# greadlink because readlink has no -f on Mac
SCRIPT_DIR_ABS_PATH="$(dirname $(greadlink -f "$0"))"
PROJECT_ROOT_ABS_PATH="${SCRIPT_DIR_ABS_PATH}/../.."
PROJECT_VERSION=""

# compile IS required before test
# https://github.com/plokhotnyuk/jsoniter-scala#known-issues
SBT_PRE_PUBLISH_TASKS="\
;clean
;headerCreate
;test:headerCreate
;compile
;test
;doc
;paradox"

echo_success() {
    echo "" \
    && echo "=================" \
    && echo "Publish succeeded" \
    && echo "================="
}

git_clone_commit_push_doc() {
    echo "==========================================="
    echo "Cloning, updating and pushing documentation"
    echo "==========================================="
    if [[ ${PROJECT_VERSION} == "" ]]; then
        echo "Internal script error: Project version should have been made available by now"
        exit 1
    else
        (
            REPO_NAME="cerst.github.io"
            PROJECT_NAME="structible"
            FOLDER_PATH="$PROJECT_NAME/$PROJECT_VERSION"

            cd ${SCRIPT_DIR_ABS_PATH} \
            && rm -rf ${REPO_NAME} \
            && git clone https://github.com/cerst/${REPO_NAME} \
            && cd ${REPO_NAME} \
            && mkdir -p ${FOLDER_PATH} \
            && cp -r ${PROJECT_ROOT_ABS_PATH}/doc/target/paradox/site/main/* ${FOLDER_PATH} \
            && git add . \
            && git commit -m "release $PROJECT_NAME v$PROJECT_VERSION" \
            && git push \
            && rm -rf ${REPO_NAME}
        )
    fi

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

sbt_run_publish_task() {
    echo "" \
    && echo "=======================" \
    && echo "Run 'sbt publishSigned'" \
    && echo "=======================" \
    && ( cd ${PROJECT_ROOT_ABS_PATH} && sbt --warn publishSigned )
}

sbt_run_pre_publish_tasks() {
    echo "" \
    && echo "=========================" \
    && echo "Run sbt pre-publish tasks" \
    && echo "${SBT_PRE_PUBLISH_TASKS}" \
    && echo "=========================" \
    && ( cd ${PROJECT_ROOT_ABS_PATH} && sbt --warn ${SBT_PRE_PUBLISH_TASKS} )
}

sbt_run_release_task() {
    echo "" \
    && echo "=========================" \
    && echo "Run 'sbt sonatypeRelease'" \
    && echo "=========================" \
    && ( cd ${PROJECT_ROOT_ABS_PATH} && sbt --warn sonatypeRelease )
}

sbt_run_pre_publish_tasks \
    && readAndConfirmVersion \
    && git_ensure_master_branch \
    && sbt_run_publish_task \
    && git_clone_commit_push_doc \
    && sbt_run_release_task \
    && echo_success
