@echo off
chcp 65001 1>nul
java -Dsun.stdout.encoding=UTF-8 -jar "%~dp0Person-Car-app-0.0.1.jar" %*
