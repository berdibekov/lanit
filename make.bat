@echo off
rem ---------------------------------------------------------------------------
chcp 65001 1>nul
mkdir dist 2>nul
rem ---------------------------------------------------------------------------
echo.
echo Building application...
echo version 0.0.1
echo.
call mvnw clean package --show-version -Dorg.slf4j.simpleLogger.showDateTime=true "-Dorg.slf4j.simpleLogger.dateTimeFormat=yyyy-MM-dd HH:mm:ss"
if %errorlevel% neq 0 (
  echo "Build failed. Check log above for more details."
  exit /b
)
rem ---------------------------------------------------------------------------
copy target\Person-Car-app-0.0.1.jar dist /Y 1>nul
copy conf\app.bat dist /Y 1>nul
rmdir /s /q target\ 1>nul
rem ---------------------------------------------------------------------------
echo.
echo ...build was successful
