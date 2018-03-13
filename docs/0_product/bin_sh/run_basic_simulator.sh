#!/bin/sh

PATH=../jre/bin:$PATH

java -version

source ../config/config.sh

slot_file_name="../input/vslot_param_basic.xlsx"
slot_stage="basic"
slot_type="simulator"

java -jar ../jar/vslot-simulator.jar $slot_file_name $slot_stage $slot_type $slot_username

read -p "Hit ENTER to shutdown..."
