xcopy "G:\project-repo\ionic-ops-ui\service-app\src" "H:\git-projects\ops-service-app\src" /E /D
@echo off
echo type "commit" or "update"
cd "curl"

set GIT_PATH="C:\Program Files\Git\bin\git.exe"
set BRANCH = "dev"

:P
set ACTION=
set /P ACTION=Action: %=%
if "%ACTION%"=="c" (
  %GIT_PATH% add -A
	%GIT_PATH% commit -am "Auto-committed on %date%"
	%GIT_PATH% pull %BRANCH%
	%GIT_PATH% push %BRANCH%
)
if "%ACTION%"=="u" (
	%GIT_PATH% pull %BRANCH%
)
if "%ACTION%"=="exit" exit /b
goto P