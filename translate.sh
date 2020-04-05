#!/usr/bin/env bash

while [[ $# -gt 0 ]]
do
key="$1"

case $key in
    -from-lang)
    SOURCE_LANG="$2"
    shift # past argument
    shift # past value
    ;;
   -to-lang)
    TARGET_LANG="$2"
    shift # past argument
    shift # past value
    ;;
    -text)
    TEXT="$2"
    shift # past argument
    shift # past value
    ;;    
    --default)
    DEFAULT=YES
    shift # past argument
    ;;
    *) # unknown option
	shift # past argument
    ;;
esac
done

curl -i -X POST --data "sourcelang=${SOURCE_LANG}" --data "targetlang=${TARGET_LANG}" --data "text=${TEXT}" "http://localhost:8080/translate-api/api/v1/translate"
echo "\n"
