#TODO
pwd=`pwd`
cd org.eclipse.om2m
#sudo mvn clean install
sudo rm -f log/gscl.log
sudo rm -f log/nscl.log
cd ..
wait

osascript -e "tell application \"Terminal\" to do script \"cd $pwd; clear; sudo java -Dosgi.logfile=../../../../../../../../log/gscl.log -jar -ea -Declipse.ignoreApp=true -Dosgi.clean=true -Ddebug=true org.eclipse.om2m/org.eclipse.om2m.site.gscl/target/products/gscl/macosx/cocoa/x86_64/plugins/org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar -console -noExit\""> /dev/null

osascript -e "tell application \"Terminal\" to do script \"cd $pwd; clear; sudo java -Dosgi.logfile=../../../../../../../../log/nscl.log  -jar -ea -Declipse.ignoreApp=true -Dosgi.clean=true -Ddebug=true org.eclipse.om2m/org.eclipse.om2m.site.nscl/target/products/nscl/macosx/cocoa/x86_64/plugins/org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar -console -noExit\""> /dev/null
