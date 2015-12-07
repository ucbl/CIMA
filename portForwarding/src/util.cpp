#include "../include/util.hpp"
#include "../include/logger.hpp"
#include <ctype.h>
#include <cstdlib>
#include <time.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>
using namespace std;

void util::outputMessage(string message,string informationType)
{
    logger::instance().log(message, informationType);

    if(informationType == logger::LOG_LEVEL_ERROR)
    {
        throw runtime_error(message);
    }
    if(informationType == logger::LOG_LEVEL_FATAL)
    {
        throw runtime_error(message);
        exit(0);
    }
    cout<<informationType<<" "<<message<<endl;
}

unsigned short util::generatePort()
{
    int socketElement = socket(AF_INET , SOCK_STREAM , 0);
    if(socketElement == -1)
    {
        outputMessage("Unable To create Sockets",logger::LOG_LEVEL_FATAL);
    }
    struct sockaddr_in server;
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	for(unsigned short i = 10000 ; i<0xFFFF; i++ )
    {
        server.sin_port = htons(i);
        if(bind(socketElement,(struct sockaddr *)&server , sizeof(server)) < 0)
        {

        }
        else
        {
            outputMessage("Port Generated",logger::LOG_LEVEL_INFO);
            close(socketElement);
            return i;
        }
    }

    outputMessage("All the port are used",logger::LOG_LEVEL_FATAL);
    return -1;

}


void util::sendJson(int port)
{
    cout<<"{\"port\" : \""<< port<<"\" "<<", \"pid\" : "<<"\""<< getpid() <<"\""<<"}"<<endl;
}
