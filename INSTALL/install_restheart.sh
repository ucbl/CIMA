#!/bin/bash


echo "**********************"
echo "* INSTALL  RESTHEART *"
echo "**********************"

echo "Deleting previous restheart installation ..."
sudo rm -rf restheart-1.1.4.zip restheart-1.1.4


echo "Downloading restheart ..."
wget https://github.com/SoftInstigate/restheart/releases/download/1.1.4/restheart-1.1.4.zip

echo "Unzipping restheart ..."
unzip restheart-1.1.4.zip

wait

echo "Copying restheart conf file"
cp ../conf/restheart.yml restheart-1.1.4/etc/
