#ifndef UTIL_INCLUDED
#define UTIL_INCLUDED
#include <string>

using namespace std;

namespace util
{
    void outputMessage(string message,string informationType);
    unsigned short generatePort();
    void sendJson(int port);
}

#endif // UTIL_INCLUDED

