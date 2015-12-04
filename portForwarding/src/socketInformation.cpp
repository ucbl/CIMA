#include "../include/socketInformation.h"
#include <sys/socket.h>
#include <netinet/in.h>
#include <iostream>

using namespace std;


void si::showSocketInformation(SocketInformation socketInformation)
{
    cout<<"Socket Information : socketNumber = "<<socketInformation.socketNumber<<endl;
}
