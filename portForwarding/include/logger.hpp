#ifndef LOGGER_H
#define LOGGER_H

#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <mutex>

// Definition of a multithread safe singleton logger class
class logger
{
public:
    static const std::string LOG_LEVEL_DEBUG;
    static const std::string LOG_LEVEL_INFO;
    static const std::string LOG_LEVEL_ERROR;
    static const std::string LOG_LEVEL_WARNING;
    static const std::string LOG_LEVEL_FATAL;

    // Returns a reference to the singleton Logger object
    static logger& instance();

    // Logs a single message at the given log level
    void log(const std::string& inMessage,
             const std::string& inLogLevel);

    // Logs a vector of messages at the given log level
    void log(const std::vector<std::string>& inMessages,
             const std::string& inLogLevel);

protected:
    // Static variable for the one-and-only instance
    static logger* loggerInstance;

    // Constant for the filename
    static const char* const LOG_FILE_NAME;

    // Data member for the output stream
    std::ofstream fileOutputStream;

    // Embedded class to make sure the single Logger
    // instance gets deleted on program shutdown.
    friend class cleanup;
    class cleanup
    {
    public:
        ~cleanup();
    };

    // Logs message. The thread should own a lock on mutexLog
    // before calling this function.
    void logHelper(const std::string& inMessage,
                   const std::string& inLogLevel);

private:
    logger();
    virtual ~logger();
    logger(const logger&);
    logger& operator=(const logger&);
    static std::mutex mutexLog;
};

#endif // LOGGER_H
