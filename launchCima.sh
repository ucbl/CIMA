#!/bin/bash
localLocation=$(pwd)

function launchCima {

	os=$(uname -s)
	ws="gtk"
	arch=$(uname -m)

	if [ $arch != "x86_64" ]; then
		arch="x86"
	fi

	if [ $os == "Darwin" ]; then
		os="macosx"
		ws="cocoa"
	elif [ $os == "Linux" ]; then
		os="linux"
		ws="gtk"
	fi
	cd $localLocation/org.eclipse.om2m/org.eclipse.om2m.site.$1/target/products/$1/$os/$ws/$arch
	echo $2' java -jar -ea -Declipse.ignoreApp=true -Dosgi.clean=true -Ddebug=true plugins/org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar -console -noExit'
	$2 java -jar -ea -Declipse.ignoreApp=true -Dosgi.clean=true -Ddebug=true plugins/org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar -console -noExit
}

function installCima {
#	install the port forwarding
	cd portForwarding
	sudo ./install.sh
	cd ..
	cd $localLocation/org.eclipse.om2m
#	sudo mvn clean; mvn install
	sudo  mvn  install
}

function usage {
	echo "usage : launchCima [OPTION]"
	echo "install and launch CIMA"
	echo -e "  -h  --help\tshow this help"
	echo -e "  -i  --install\tcompile and install CIMA with maven. Need 'maven' and 'sudo' installed"
	echo -e "  -n  --nscl\tlaunch nscl part of CIMA."
	echo -e "  -g  --gscl\tlaunch gscl part of CIMA. Need 'sudo' installed"

}

OPTS=$( getopt -o hing -l "help,install,nscl,gscl" -- "$@" )
if [ $? != 0 ]
then
    exit 1
fi

eval set -- "$OPTS"
	
while true ; do
    case "$1" in
        -h | --help) 
			usage
        	exit 0
        ;;
        -i | --install) 
			installCima
			shift
        ;;
       	-n | --nscl) 
			launchCima nscl
        	shift
        ;;
        -g | --gscl) 
			launchCima gscl sudo
			shift
		;;
        --) 
			shift
			break
		;;
    esac
done


cd $localLocation
