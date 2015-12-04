#include "../include/serverTCP.h"
#include "../include/abstractServer.h"
#include "../include/socketInformation.h"
#include "../include/logger.h"
#include "../include/util.h"
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string>
#include <stdio.h>
#define L64Ko 65536

using namespace std;
using namespace si;

const short ServerTCP::inCommingConnection = 5;

ServerTCP::ServerTCP()
{
    //ctor
}
ServerTCP::ServerTCP(string ipAddressGSCL, string _ipAddressObject, unsigned short _portConnectedObject, string _idObject) : AbstractServer(ipAddressGSCL), ipAddressObject(_ipAddressObject), portConnectedObject(_portConnectedObject)
{

}

ServerTCP::~ServerTCP()
{
    this->Close();
}


void ServerTCP::Listen()
{
    listen(this->serverInformation.socketNumber ,inCommingConnection);
    util::outputMessage("Listen to : "+this->address+string(":")+ to_string(this->port), logger::LOG_LEVEL_INFO);
}

void ServerTCP::WaitConnection()
{
    this->threadWaitingConnection = thread(&ServerTCP::WaitingConnectionThread,this);
    util::outputMessage("Start Thread for Waiting connection {Server}", logger::LOG_LEVEL_INFO);
}

void ServerTCP::WaitingConnectionThread()
{
    SocketInformation socketInformation;
    util::outputMessage("Waiting for connection {Server}", logger::LOG_LEVEL_INFO);
    cout<<"{\"port\" = \""<< this->port<<"\"}"<<endl;
    while(true)
    {
        socketInformation.socketNumber = (accept(this->serverInformation.socketNumber, (struct sockaddr *)&(this->serverInformation.portInformation), (socklen_t*)&(this->serverInformation.portInformation)));
        //Add new client
        clientInformation.push_back(socketInformation);
        // Start the connection with the object
        if(this->connectedObject == nullptr)
        {
            this->connectedObject = new ConnectedObject(this->idObject,this->portConnectedObject,this->ipAddressObject);
        //Sending the server to the connected object and start communication
            this->connectedObject->Communication(*this);
        }
        //Start Commmunication
        this->Communication(socketInformation);

        util::outputMessage("Connection with a new client established {Server}", logger::LOG_LEVEL_INFO);
        util::outputMessage("Start Communication... {Server}", logger::LOG_LEVEL_INFO);
    }

}
///thread dataReceivingThread = thread(&server::receivingData, &Server, receivedMessage);

void ServerTCP::Communication(SocketInformation socketInformation)
{
    this->threadsCommunication.push_back(thread(&ServerTCP::CommunicationThread, this, socketInformation));
}
void ServerTCP::CommunicationThread(SocketInformation socketInformation)
{
    string message="";
    while(true)
    {
        message = this->Receive(socketInformation);
        if(message != "")
        {
            if(this->connectedObject->SendingData(message) != true)
            {
                //TODO Close All Connection to the Object restart all
                //break;
            }
        }
    }
}

bool ServerTCP::Send(SocketInformation socketInformation,string data)
{
    util::outputMessage("Start sending data {Server}",logger::LOG_LEVEL_DEBUG);
    if( send(socketInformation.socketNumber , data.c_str(), data.size() , 0)  < 0)
    {
        util::outputMessage("Fail To send to the object {Server}",logger::LOG_LEVEL_WARNING);
        return false;
    }
    util::outputMessage("Data Sended {Server}", logger::LOG_LEVEL_DEBUG);
    return true;
}

string ServerTCP::Receive(SocketInformation clientSocketInformation)
{
    int sizeDataReceived = 0;
    string message="";
    const int lengthReceivedMessage = L64Ko; // 64 KO
    char * tmp  = new char [lengthReceivedMessage];
    while( sizeDataReceived = recv(clientSocketInformation.socketNumber, tmp , lengthReceivedMessage , 0) > 0 )
    {
        util::outputMessage("receivingData... {Server}", logger::LOG_LEVEL_DEBUG);
        message = string(tmp);
        sizeDataReceived = 0;
        delete [] tmp;
        return message;
    }
    return message;
}

void ServerTCP::Close()
{
    delete this->connectedObject;
}
