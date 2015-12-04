#ifndef ConnectedObject_H
#define ConnectedObject_H
#include "../include/socketInformation.h"
#include <sys/socket.h>
#include <netinet/in.h>
#include <string>
#include <thread>
#include "../include/serverTCP.h"



using namespace std;
using namespace si;

class ServerTCP;

class ConnectedObject
{
public:

    ConnectedObject();

    ConnectedObject(string , unsigned short ,string  );

    virtual ~ConnectedObject();


    string GetId()
    {
        return id;
    }
    void SetId(string val)
    {
        id = val;
    }

    string GetIpAddress()
    {
        return ipAddress;
    }
    void SetIpAddress(string val)
    {
        this->ipAddress = val;
    }

    bool InitializeTCPSocket();

    bool InitializePortSocket();

    bool SendingData(string);

    void Communication(ServerTCP & );


private :

    void CommunicationThread(ServerTCP & );

    string ReceiveData();

    //fields
    //Parameters
    unsigned short port;
    unsigned short inComingConnection;
    unsigned short lengthReceivedMessage;

    string id;
    string ipAddress;
    SocketInformation socketInformation;

    thread communicationThreads;
};

#endif // ConnectedObject_H
