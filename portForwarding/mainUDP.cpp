#include <cstdlib>
#include <cstddef>
#include <iostream>
#include <string>

#include <boost/shared_ptr.hpp>
#include <boost/enable_shared_from_this.hpp>
#include <boost/bind.hpp>
#include <boost/asio.hpp>
#include <boost/thread/mutex.hpp>

#include "./include/PortForwardingUDP.hpp"
#include "./include/logger.hpp"
#include "./include/util.hpp"



int main(int argc, char* argv[])
{
   if (argc != 4)
   {
      util::outputMessage("usage: PortForwardingTCP <ip address GSCL> <ip address object> <object port> ",logger::LOG_LEVEL_FATAL);
      return 1;
   }


	const unsigned short local_port   = util::generatePort();
	const unsigned short forward_port = static_cast<unsigned short>(::atoi(argv[3]));
	const std::string local_host      = argv[1];
	const std::string forward_host    = argv[2];

    util::sendJson(local_port);

	boost::asio::io_service ios;

	try
	{
		PortForwardingUDP::m_udpProxyServer svrTest(ios,
			local_host,local_port,
			forward_host,forward_port);

		ios.run();
	}
	catch(std::exception& e)
	{
	    util::outputMessage(e.what(),logger::LOG_LEVEL_FATAL);
		return 1;
	}

	return 0;
}
