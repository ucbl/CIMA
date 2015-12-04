#ifndef SERVERTCP_H
#define SERVERTCP_H
#include <vector>
#include <thread>
#include "../include/abstractServer.h"

using namespace std;
using namespace si;

class ConnectedObject;

class ServerTCP : public AbstractServer
{

public :
        /** \brief
        *
        *
        */
        ServerTCP();
        /** \brief
        *
        *
        */
        ServerTCP(string ,string ,unsigned short , string);
        /** \brief
        *
        *
        */
        virtual ~ServerTCP();
        /** \brief
        *
        *
        */
        void WaitConnection();
        /** \brief
        *
        *
        */
        void Listen();
        /** \brief
        *
        *
        */
        void Close();
        /** Access clientInformation
         * \return The current value of clientInformation
         */
        vector<SocketInformation>& GetClientInformation() { return clientInformation; }
        /** Set clientInformation
         * \param val New value to set
         */
        void SetClientInformation(vector<SocketInformation> val) { clientInformation = val; }
        /** \brief
        *
        *
        */
        bool Send(SocketInformation ,string);

    private :
        void CommunicationThread(SocketInformation );
        /** \brief
        *
        *
        */
        string Receive(SocketInformation);
        /** \brief
         *
         */
        void Communication(SocketInformation);
        /** \brief
         *
         *
         */
        void WaitingConnectionThread();
        vector<SocketInformation>   clientInformation; //!< Member variable "clientInformation"
        vector<thread> threadsCommunication;/**<  */
        thread threadWaitingConnection;/**<  */
        static const short inCommingConnection;/**<  */

        /* Information for the connected Object */
        string ipAddressObject;
        string idObject;
        unsigned short portConnectedObject;
        ConnectedObject * connectedObject = nullptr;
};


#endif // SERVERTCP_H
