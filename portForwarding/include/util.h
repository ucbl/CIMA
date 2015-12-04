#ifndef UTIL_INCLUDED
#define UTIL_INCLUDED
#include <string>
#include "../include/connectedObject.h"
using namespace std;

namespace util
{
    //static ConnectedObject * connectedObject = nullptr;
    void outputMessage(string message,string informationType);
    unsigned short generatePort();
    void mainParametre(char ** , string & ,string & , unsigned short &, string &);
}


#endif // UTIL_INCLUDED
