#include "../include/abstractServer.h"
#include "../include/socketInformation.h"
#include "../include/logger.h"
#include "../include/util.h"

#include <string>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

using namespace si;
using namespace std;


AbstractServer::AbstractServer()
{
    this->address = "127.0.0.1";
    this->port = 5000;
}

AbstractServer::AbstractServer(string _address) : address(_address)
{
    //ctor
}


AbstractServer::~AbstractServer()
{
    //dtor
}

bool AbstractServer::InitializeSocket()
{
    this->serverInformation.socketNumber = socket(AF_INET , SOCK_STREAM , 0);
    if(this->serverInformation.socketNumber == -1 )
        util::outputMessage("Unable to create socket", logger::LOG_LEVEL_ERROR);
    util::outputMessage("Socket created successfully", logger::LOG_LEVEL_INFO);
    return true;
}


void AbstractServer::InitializePortSocket()
{
    this->serverInformation.portInformation.sin_port = htons(this->port);
    this->serverInformation.portInformation.sin_addr.s_addr = INADDR_ANY;//inet_addr(this->address.c_str());
    this->serverInformation.portInformation.sin_family = AF_INET;
    util::outputMessage("Connection mode TCP established", logger::LOG_LEVEL_INFO);
}

void AbstractServer::BindingSocket()
{
    int i = 0;
    do
    {
        this->port =util::generatePort();
        this->serverInformation.portInformation.sin_port = htons(this->port);
    } while (bind(this->serverInformation.socketNumber,(sockaddr *)&(this->serverInformation.portInformation) , sizeof(this->serverInformation.portInformation)) < 0 && i++ <3);
        if(i == 3)
            //Todo CRITICAL ERROR AND EXIT THE PROGRAM;
        util::outputMessage("Binding failure with the port :"+to_string(this->port),logger::LOG_LEVEL_ERROR);

    util::outputMessage("Binding established with :"+to_string(this->port), logger::LOG_LEVEL_INFO);

}
