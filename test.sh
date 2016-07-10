#!/bin/bash
set -x
DEVICE=$1
OUTPUT=$2
TESTPATH=`pwd`

if [ -z "$OUTPUT" ]; then
   OUTPUT=results
fi

if [ -z "$DEVICE" ]; then
    ADB=adb
else
    ADB="adb -s $DEVICE"
fi

# Build test jar
  ant build

#Test
sleep 1
$ADB root
sleep 1
$ADB remount

$ADB push bin/SmokeTest.jar /data/local/tmp/SmokeTest.jar

mkdir -p ${OUTPUT}

$ADB shell uiautomator runtest SmokeTest.jar -c com.cloudminds.terminal.smoke.SmokeTest | tee ${OUTPUT}/test_result.txt

mkdir -p ${OUTPUT}/failedScreenshot
