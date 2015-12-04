sudo rm -rf /CIMA/PortForwarding
sudo mkdir -p /CIMA/PortForwarding
sudo make clean
wait
sudo make
wait
sudo cp PortForwarding /CIMA/PortForwarding
sudo chmod 777 /CIMA/PortForwarding
