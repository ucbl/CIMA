#ifndef SocketInformation_H
#define SocketInformation_H
#include <sys/socket.h>
#include <netinet/in.h>

namespace si
{
    struct SocketInformation
    {
            int socketNumber; //!< Member variable "socketNumber"
            sockaddr_in portInformation; //!< Member variable "portInformation"
    };

    void showSocketInformation(SocketInformation);

}


#endif // SocketInformations_H
