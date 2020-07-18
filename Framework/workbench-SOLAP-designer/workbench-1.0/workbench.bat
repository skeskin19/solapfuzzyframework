@echo off

rem Schema Workbench launch script

set MONDRIAN_HOME=.\

if not exist .\lib set MONDRIAN_HOME=..\

rem base Mondrian JARs need to be included

set CP=%MONDRIAN_HOME%lib/commons-dbcp.jar
set CP=%CP%;%MONDRIAN_HOME%lib/commons-io.jar
set CP=%CP%;%MONDRIAN_HOME%lib/commons-lang.jar
set CP=%CP%;%MONDRIAN_HOME%lib/commons-collections.jar
set CP=%CP%;%MONDRIAN_HOME%lib/commons-pool.jar
set CP=%CP%;%MONDRIAN_HOME%lib/eigenbase-properties.jar
set CP=%CP%;%MONDRIAN_HOME%lib/eigenbase-resgen.jar
set CP=%CP%;%MONDRIAN_HOME%lib/eigenbase-xom.jar
set CP=%CP%;%MONDRIAN_HOME%lib/javacup.jar
set CP=%CP%;%MONDRIAN_HOME%lib/log4j.jar
set CP=%CP%;%MONDRIAN_HOME%lib/geomondrian.jar
set CP=%CP%;%MONDRIAN_HOME%lib/jlfgr.jar
set CP=%CP%;%MONDRIAN_HOME%lib/commons-math.jar
set CP=%CP%;%MONDRIAN_HOME%lib/commons-vfs.jar
set CP=%CP%;%MONDRIAN_HOME%lib/commons-logging.jar
set CP=%CP%;%MONDRIAN_HOME%lib/geo/jts-1.10.jar

rem Workbench GUI code and resources

set CP=%CP%;%MONDRIAN_HOME%lib/workbench.jar

rem Have a .schemaWorkbench directory for local 

for /F "delims=/" %%i in ('echo %USERPROFILE%') do set ROOT=%%~si

if not exist %ROOT%\.schemaWorkbench mkdir %ROOT%\.schemaWorkbench
if not exist %ROOT%\.schemaWorkbench\log4j.xml copy log4j.xml %ROOT%\.schemaWorkbench
if not exist %ROOT%\.schemaWorkbench\mondrian.properties copy mondrian.properties %ROOT%\.schemaWorkbench

rem put mondrian.properties on the classpath for it to be picked up

set CP=%ROOT%/.schemaWorkbench;%CP%

rem or
rem set the log4j.properties system property 
rem "-Dlog4j.properties=path to <.properties or .xml file>"
rem in the java command below to adjust workbench logging

rem add all needed JDBC drivers to the classpath

for %%i in ("drivers\*.jar") do call cpappend %%i

rem add all needed plugin jars to the classpath

for %%i in ("plugins\*.jar") do call cpappend %%i

java -Xms100m -Xmx500m -cp "%CP%" -Dlog4j.configuration=file:///%ROOT%\.schemaWorkbench\log4j.xml mondrian.gui.Workbench

rem End workbench.bat
