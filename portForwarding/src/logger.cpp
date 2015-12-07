#include "../include/logger.hpp"
#include <stdexcept>
#include <ctime>
using namespace std;

const string logger::LOG_LEVEL_DEBUG = "[DEBUG]";
const string logger::LOG_LEVEL_INFO  = "[INFO]";
const string logger::LOG_LEVEL_ERROR = "[ERROR]";
const string logger::LOG_LEVEL_WARNING = "[WARNING]";
const string logger::LOG_LEVEL_FATAL = "[FATAL]";

const char* const logger::LOG_FILE_NAME = "log.out";

logger* logger::loggerInstance = nullptr;

mutex logger::mutexLog;

logger& logger::instance()
{
    static cleanup cleanup;

    lock_guard<mutex> guard(mutexLog);
    if (loggerInstance == nullptr)
        loggerInstance = new logger();
    return *loggerInstance;
}

logger::cleanup::~cleanup()
{
    lock_guard<mutex> guard(logger::mutexLog);
    delete logger::loggerInstance;
    logger::loggerInstance = nullptr;
}

logger::~logger()
{
    fileOutputStream.close();
}

logger::logger()
{
    fileOutputStream.open(LOG_FILE_NAME, ios_base::app);
    if (!fileOutputStream.good())
    {
        throw runtime_error("Unable to initialize the logger!");
    }
}

void logger::log(const string& inMessage, const string& inLogLevel)
{
    lock_guard<mutex> guard(mutexLog);
    logHelper(inMessage, inLogLevel);
}

void logger::log(const vector<string>& inMessages, const string& inLogLevel)
{
    lock_guard<mutex> guard(mutexLog);
    for (size_t i = 0; i < inMessages.size(); i++)
    {
        logHelper(inMessages[i], inLogLevel);
    }
}

void logger::logHelper(const std::string& inMessage, const std::string& inLogLevel)
{
    time_t now = time(0);
    fileOutputStream << string(ctime(&now)) <<inLogLevel << " " << inMessage << endl;
}
