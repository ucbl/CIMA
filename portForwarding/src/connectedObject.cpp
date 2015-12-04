#include "../include/socketInformation.h"
#include "../include/connectedObject.h"
#include "../include/logger.h"
#include "../include/util.h"
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string>
#include <vector>

using namespace std;

ConnectedObject::ConnectedObject()
{
}

ConnectedObject::ConnectedObject(string _id, unsigned short  _port, string _ipAddress) : id(_id),port(_port), ipAddress(_ipAddress)
{
    this->InitializeTCPSocket();
    this->InitializePortSocket();

}

ConnectedObject::~ConnectedObject()
{

}


bool ConnectedObject::InitializeTCPSocket()
{
    this->socketInformation.socketNumber =  socket(AF_INET , SOCK_STREAM , 0);
    if(this->socketInformation.socketNumber == -1)
        util::outputMessage("Unable to create socket {ConnectedObject}", logger::LOG_LEVEL_ERROR);
    util::outputMessage("Socket created successfully {ConnectedObject}", logger::LOG_LEVEL_INFO);
    return true ;
}

bool ConnectedObject::InitializePortSocket()
{
    this->socketInformation.portInformation.sin_port = htons(this->port);
    this->socketInformation.portInformation.sin_addr.s_addr = inet_addr(this->ipAddress.c_str());
    this->socketInformation.portInformation.sin_family = AF_INET;
    util::outputMessage("Connection mode TCP established for object {ConnectedObject}", logger::LOG_LEVEL_INFO);
    util::outputMessage("Starting connection with the object {ConnectedObject}", logger::LOG_LEVEL_INFO);

    if (connect(this->socketInformation.socketNumber , (struct sockaddr *)&(this->socketInformation.portInformation) , sizeof(this->socketInformation.portInformation)) < 0)
    {
        util::outputMessage("Connection Fail with the object {ConnectedObject}",logger::LOG_LEVEL_WARNING);
        return false;
    }
    util::outputMessage("Connection Established with the object {ConnectedObject}",logger::LOG_LEVEL_INFO);
    return true;
}


///thread dataReceivingThread = thread(&server::receivingData, &Server, receivedMessage);
void ConnectedObject::Communication(ServerTCP & serverTCP)
{
    communicationThreads=thread(&ConnectedObject::CommunicationThread,this,std::ref(serverTCP));
}

void ConnectedObject::CommunicationThread(ServerTCP & serverTCP)
{

    string message;
    while(true)
    {
        message = this->ReceiveData();
        if(message != "")
        for (int i = 0; i < serverTCP.GetClientInformation().size(); i++)
                if(serverTCP.Send(serverTCP.GetClientInformation()[i],message) != true)
                {
                    //TODO Close this client Connection to the Object restart all
//                    serverTCP.GetClientInformation().erase(serverTCP.GetClientInformation().begin() + i );
                }

    }
}

bool ConnectedObject::SendingData(string data)
{
    util::outputMessage("Start sending data {ConnectedObject}",logger::LOG_LEVEL_DEBUG);
    if( send(this->socketInformation.socketNumber , data.c_str(), data.size() , 0)  < 0)
    {
        util::outputMessage("Fail To send to the object {ConnectedObject}",logger::LOG_LEVEL_WARNING);
        return false;
    }
    util::outputMessage("Data Sended {ConnectedObject}", logger::LOG_LEVEL_DEBUG);
    return true;
}

string ConnectedObject::ReceiveData()
{
    int sizeDataReceived = 0;
    string message = "";
    const int lengthReceivedMessage = 65536;
    char * tmp  = new char [lengthReceivedMessage];
    while( sizeDataReceived = recv(this->socketInformation.socketNumber, tmp , lengthReceivedMessage , 0) > 0 )
    {
        util::outputMessage("receivingData... {ConnectedObject}", logger::LOG_LEVEL_DEBUG);
        message = string(tmp);
        sizeDataReceived = 0;
        delete [] tmp;
        return message;
    }
}
