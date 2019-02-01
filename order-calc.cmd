@echo off

Set JAVACMD="C:\Program Files\Java\jre1.8.0_191\bin\java"
set CONF_DIR="C:\tmp\order-calc\conf"

rem Invoke the App
%JAVACMD% -DconfigDir=%CONF_DIR% -jar order-calculator-0.0.1-SNAPSHOT.jar

exit 0