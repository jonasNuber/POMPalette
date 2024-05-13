@echo off
setlocal

rem Set Java home to the JDK folder next to this script
set "JAVA_HOME=%~dp0jdk"

rem Set the path to the Java executable
set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"

rem Start the jar file
"%JAVA_EXE%" --module-path /libs;/libs/third-party -jar "%~dp0libs\javafx-application-0.9.0-SNAPSHOT.jar"

endlocal