#!/bin/bash
localLocation=$(pwd)

#

#	install the port forwarding
	cd portForwarding
	sudo ./install.sh
	cd ..
	cd $localLocation/org.eclipse.om2m
#	sudo mvn clean; mvn install
	sudo  mvn  install

