#!/bin/bash


echo "**********************"
echo "* INSTALL  RESTHEART *"
echo "**********************"

echo "Deleting previous restheart installation ..."
sudo rm -rf restheart-1.1.4.zip


echo "Downloading restheart ..."
wget https://github.com/SoftInstigate/restheart/releases/download/1.1.4/restheart-1.1.4.zip

echo "Unzipping restheart ..."
unzip restheart-1.1.4.zip

wait


