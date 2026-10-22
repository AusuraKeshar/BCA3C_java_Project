@echo off
setlocal
cd /d "%~dp0"
where mvn >nul 2>nul
if errorlevel 1 (
  echo Maven is required. Install Maven 3.9+ and Java 21 LTS, then retry.
  pause
  exit /b 1
)
mvn javafx:run
endlocal
