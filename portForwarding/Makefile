all: PortForwarding

PortForwarding   : socketInformation.o util.o logger.o connectedObject.o  abstractServer.o serverTCP.o

	g++ -o PortForwarding serverTCP.o socketInformation.o abstractServer.o connectedObject.o  util.o  logger.o main.cpp -g  -std=c++11 -pthread

serverTCP.o         : ./include/serverTCP.h ./src/serverTCP.cpp
	  g++ -c ./src/serverTCP.cpp -g -std=c++11 -fpermissive

abstractServer.o : ./include/abstractServer.h ./src/abstractServer.cpp
	  g++ -c ./src/abstractServer.cpp -g -std=c++11 -fpermissive

connectedObject.o :  ./include/connectedObject.h ./src/connectedObject.cpp
	  g++ -c  ./src/connectedObject.cpp -g -std=c++11 -fpermissive

socketInformation.o : ./include/socketInformation.h ./src/socketInformation.cpp
	  g++ -c ./src/socketInformation.cpp -g -std=c++11 -fpermissive

util.o           : ./include/util.h ./src/util.cpp
	  g++ -c ./src/util.cpp -g -std=c++11 -fpermissive

logger.o         : ./include/logger.h ./src/logger.cpp
	  g++ -c ./src/logger.cpp -g -std=c++11 -fpermissive

clean :
	rm -f PortForwardingTCP *.o




