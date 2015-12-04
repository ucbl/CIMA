#include "../include/logger.h"
#include "../include/util.h"
#include <ctype.h>
#include <cstdlib>
#include <time.h>
using namespace std;

void util::outputMessage(string message,string informationType)
{
    logger::instance().log(message, informationType);

    if(informationType == logger::LOG_LEVEL_ERROR)
    {
        throw runtime_error(message);
    }
    cout<<informationType<<" "<<message<<endl;
}

unsigned short util::generatePort()
{
    srand (time(NULL));

    unsigned short port = 0;

    while( port < 9999 )
    {
        srand (time(NULL));
        port = rand() % 60000;
    }
    return port;
}

void util::mainParametre(char ** argv, string &ipAdressGSCL, string & ipAddressObject, unsigned short & port, string & idObject)
{
    ipAdressGSCL = string(argv[1]);
    ipAddressObject = string(argv[2]);
    port = atoi(argv[3]);
    idObject = string(argv[4]);
}
