@echo off
cd "C:\Program Files\mosquitto\"
set arg1=%1
set arg2=%2
set arg3=%3
set arg4=%4
set msg="{\"node_id\":\"%arg3%\",\"db\":%arg4%}"
set arg5=%5
set arg6=%6
mosquitto_pub.exe -h %arg1% -p 1883 -t %arg2% -m %msg% --repeat %arg5% --repeat-delay %arg6%
pause