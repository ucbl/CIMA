@echo off
cd "org.eclipse.om2m\org.eclipse.om2m.site.gscl\target\products\gscl\win32\win32\x86_64" & Start gscl.exe
echo %time%
timeout 2 > NUL
echo %time%
cd "..\..\..\..\..\..\..\org.eclipse.om2m.site.nscl\target\products\nscl\win32\win32\x86_64" & Start nscl.exe
echo %time%
timeout 2 > NUL
echo %time%
Start http://127.0.0.1:8080