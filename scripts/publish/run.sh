#!/usr/bin/env bash

# greadlink because readlink has no -f on Mac
SCRIPT_DIR_ABS_PATH="$(dirname $(greadlink -f "$0"))"
PROJECT_ROOT_ABS_PATH="${SCRIPT_DIR_ABS_PATH}/../.."

# run core tasks first as all other projects depend on it
# run doc tasks last as it (likely) depends on test code snippets of other projects
SBT_STAGE_TASKS="\
;core/clean
;core/headerCreate
;core/test;headerCreate
;core/test
;akka-http/clean
;akka-http/headerCreate
;akka-http/test:headerCreate
;akka-http/test
;configs/clean
;configs/headerCreate
;configs/test:headerCreate
;configs/test
;jsoniter-scala/clean
;jsoniter-scala/headerCreate
;jsoniter-scala/test:headerCreate
;jsoniter-scala/test
;quill/clean
;quill/headerCreate
;quill/test:headerCreate
;quill/test
;doc/clean
;doc/paradox"

# TODO: sbt publish tasks
SBT_PUBLISH_TASKS="\
"



stage() {
    echo "" \
    && echo "===================" \
    && echo "Run Sbt stage tasks" \
    && echo "${SBT_STAGE_TASKS}" \
    && echo "===================" \
    && ( cd ${PROJECT_ROOT_ABS_PATH} && sbt --warn ${SBT_STAGE_TASKS} )
}

readAndConfirmVersion() {
    # pipe sbt output to /dev/null as it otherwise interferes with the variable assignment using cat
    PROJECT_VERSION=$( cd ${PROJECT_ROOT_ABS_PATH} && sbt versionToFile > /dev/null && cat target/version-to-file/version ) \
    && echo "" \
    && echo "Do you want to publish v$PROJECT_VERSION (y/n)?" \
    && while true; do
        read yn
            case ${yn} in
                [Yy]* ) echo "Publish not yet implemented" && exit 1; break;;
                [Nn]* ) echo "Aborted" && exit 0;;
                * ) echo "Please answer yes or no.";;
            esac
    done

}

# TODO: clone the gh-pages repo, put the doc in it and push
publish() {
    echo "" \
    && echo "=====================" \
    && echo "Run Sbt publish tasks" \
    && echo ${SBT_PUBLISH_TASKS} \
    && echo "=====================" \
    && $( cd ${PROJECT_ROOT_ABS_PATH} && sbt --warn ${SBT_PUBLISH_TASKS} )
}

echo_success() {
    echo "" \
    && echo "=================" \
    && echo "Publish succeeded" \
    && echo "================="
}

stage && readAndConfirmVersion
