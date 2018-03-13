@ECHO OFF
rem program start

set PATH=../jre/bin;%PATH%

java -version

call ../config/config.bat

set slot_file_name="../input/vslot_param_stage01.xlsx"
set slot_stage="stage01"
set slot_type="freespin"

java -jar ../jar/vslot-simulator.jar %slot_file_name% %slot_stage% %slot_type% %slot_username%

set /p DUMMY=Hit ENTER to shutdown...

@ECHO ON