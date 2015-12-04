#ifndef AbstractServer_H
#define AbstractServer_H
#include "../include/socketInformation.h"

#include <vector>
#include <string>

using namespace std;
using namespace si;

class AbstractServer
{
    public:
        /** Default constructor */
        AbstractServer();
        /** Initializer constructor */
        AbstractServer(string);
        /** Default destructor */
        virtual ~AbstractServer() = 0;
        /** Access serverInformation
         * \return The current value of serverInformation
         */
        SocketInformation & GetServerInformation() { return serverInformation; }
        /** Set serverInformation
         * \param val New value to set
         */
        void SetServerInformation(SocketInformation val) { serverInformation = val; }
        /** Access port
         * \return The current value of port
         */
        unsigned short Getport() { return port; }
        /** Set port
         * \param val New value to set
         */
        void Set(unsigned short val) { port = val; }
        /** Access address
         * \return The current value of address
         */
        string GetAddress() { return address; }
        /** Set address
         * \param val New value to address
         */
        void SetAddress(string val) { address = val; }
        /** Create a socket and store the socket Number in the serverInformation.socketNumber
         *
         * \return true if the socket is create false if not
         *
         */
        bool InitializeSocket();
        /** Assigned a set of information in the serverInformation.portInformation
         *
         */
        void InitializePortSocket();
         /** bind the port that we have in the serverInformation.portInformation with the network card (OS)
          *
          *
          */
        void BindingSocket();
        /** Receiving Information from one of the client that we have in the vector
         *
         */
        virtual bool Send(si::SocketInformation , string) = 0;
        /** Send Information to one of the client that we have in the vector
         *
         *
         */
        virtual string Receive(si::SocketInformation) = 0 ;
        /** Close the Server and all the connection
         *
         */

        virtual void Close() = 0 ;

    protected:
        SocketInformation serverInformation; //!< Member variable "serverInformation"
        unsigned short port; //!< Member variable "port"
        string address;

};

#endif // AbstractServer_H
