#include <iostream>
#include "include/util.h"
#include "include/connectedObject.h"
#include "include/serverTCP.h"

using namespace std;


/** \brief main methode
 *
 * \param Address Ip
 * \param Port
 * \param Id Object
 * \return 0 in fail or 1 in succes
 *
 */
int main(int argc, char ** argv)
{
    string ipAddressGSCL, ipAddressObject, idObject;
    unsigned short port;
    util::mainParametre(argv,ipAddressGSCL,ipAddressObject,port,idObject);

    ServerTCP serverTCP(ipAddressGSCL,ipAddressObject,port,idObject);
    serverTCP.InitializeSocket();
    serverTCP.InitializePortSocket();
    serverTCP.BindingSocket();
    serverTCP.Listen();
    serverTCP.WaitConnection();
    while(true)
    {

    }
    return 0;
}
