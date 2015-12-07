#pragma once

#include <boost/shared_ptr.hpp>
#include <boost/enable_shared_from_this.hpp>
#include <boost/bind.hpp>
#include <boost/asio.hpp>
#include <boost/thread/mutex.hpp>
#include "logger.hpp"
#include "util.hpp"


namespace PortForwardingUDP
{
	namespace ip = boost::asio::ip;
	using namespace boost::asio;
	using namespace std;
	const int max_data_length = 1024 * 10;

	class m_udpProxyServer
	{
	public:
		m_udpProxyServer(io_service& io,const string& localhost,
			const int& localport,const string& remotehost,const int& remoteport):
		downstream_socket_(io,ip::udp::endpoint(ip::udp::v4(),localport)),
			upstream_socket_(io),
			_remotehost(remotehost),
			_remoteport(remoteport),
			upstream_remoteUdpEndpoint(ip::address_v4::from_string(_remotehost),_remoteport)
		{
			start_downstream_receive();
		}

		void start_downstream_receive()
		{
			downstream_socket_.async_receive_from(
				boost::asio::buffer(downstream_data_,max_data_length),
				downstream_remoteUdpEndpoint,
				boost::bind(&m_udpProxyServer::upstream_connect,this,
				boost::asio::placeholders::bytes_transferred,
				boost::asio::placeholders::error));
		}

		void upstream_connect(const size_t& bytes_transferred,
			const boost::system::error_code& error)
		{
			if (!error )
			{
				upstream_socket_.async_connect(
					upstream_remoteUdpEndpoint,
					boost::bind(&m_udpProxyServer::handle_upstream_connect,
					this,bytes_transferred,boost::asio::placeholders::error));
			}
			else
			{
			    util::outputMessage(error.message(),logger::LOG_LEVEL_FATAL);
			}
		}

		void handle_upstream_connect(const size_t& bytes_transferred,
			const boost::system::error_code& error)
		{
			upstream_socket_.async_send_to(
					boost::asio::buffer(downstream_data_,bytes_transferred),
					upstream_remoteUdpEndpoint,
					boost::bind(&m_udpProxyServer::handle_upstream_send,
					this,boost::asio::placeholders::error));
		}

		void handle_upstream_send(const boost::system::error_code& error)
		{
			if (!error )
			{
				upstream_socket_.async_receive_from(
					boost::asio::buffer(upstream_data_,max_data_length),
					upstream_remoteUdpEndpoint,
					boost::bind(&m_udpProxyServer::handle_upstream_receive,
					this,
					boost::asio::placeholders::bytes_transferred,
					boost::asio::placeholders::error));

			}
			else
			{
                util::outputMessage(error.message(),logger::LOG_LEVEL_FATAL);
			}
		}

		void handle_upstream_receive(const size_t& bytes_transferred,
			const boost::system::error_code& error)
		{
			if (!error )
			{
				downstream_socket_.async_send_to(
					boost::asio::buffer(upstream_data_,bytes_transferred),
					downstream_remoteUdpEndpoint,
					boost::bind(&m_udpProxyServer::handle_downstream_send,
					this,
					boost::asio::placeholders::error));
			}
			else
			{
			    util::outputMessage(error.message(),logger::LOG_LEVEL_FATAL);
			}
		}

		void handle_downstream_send(const boost::system::error_code& error)
		{
			if (!error )
			{
				downstream_socket_.async_receive_from(
					boost::asio::buffer(downstream_data_,max_data_length),
					downstream_remoteUdpEndpoint,
					boost::bind(&m_udpProxyServer::handle_downstream_receive,this,
					boost::asio::placeholders::bytes_transferred,
					boost::asio::placeholders::error));
			}
			else
			{
			    util::outputMessage(error.message(),logger::LOG_LEVEL_FATAL);
			}
		}

		void handle_downstream_receive(const size_t& bytes_transferred,
			const boost::system::error_code& error)
		{
			upstream_socket_.async_send_to(
				boost::asio::buffer(downstream_data_,bytes_transferred),
				upstream_remoteUdpEndpoint,
				boost::bind(&m_udpProxyServer::handle_upstream_send,
				this,boost::asio::placeholders::error));
		}

	private:
		ip::udp::socket downstream_socket_;
		ip::udp::socket upstream_socket_;
		string _remotehost;
		int _remoteport;
		ip::udp::endpoint downstream_remoteUdpEndpoint;
		ip::udp::endpoint upstream_remoteUdpEndpoint;
		unsigned char downstream_data_[max_data_length];
		unsigned char upstream_data_[max_data_length];
	};
}
