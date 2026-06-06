@echo off
REM Run the MediTrack demo from the assembled jar
SET JAR=target\meditrack-1.0.0-jar-with-dependencies.jar
IF NOT EXIST "%JAR%" (
  echo Assembled JAR not found. Building...
  mvn -q clean package assembly:single
  IF ERRORLEVEL 1 (
    echo Build failed. Resolve errors and try again.
    exit /b 1
  )
)

echo Running MediTrack demo (DemoRunner)...
java -jar "%JAR%" --demo
exit /b 0
