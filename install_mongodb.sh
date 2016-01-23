#!/bin/bash


echo "********************"
echo "* INSTALL  MONGODB *"
echo "********************"


echo "Getting the Key ..."
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10


echo "Creating the list file ..."
echo "deb http://repo.mongodb.org/apt/ubuntu "$(lsb_release -sc)"/mongodb-org/3.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.0.list


echo "Updating packages ..."
sudo apt-get update


echo "Installing mongo-db lastest version ..."
sudo apt-get install -y mongodb-org

echo "Stopping auto-started mongodb server ..."
sudo /etc/init.d/mongodb stop
