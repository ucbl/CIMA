COMPILER         = -g++
OPTIMIZATION_OPT = -O3
OPTIONS          = -pedantic -ansi -Wall -Werror $(OPTIMIZATION_OPT)
EXECUTABLETCP 		 = PortForwardingTCP
EXECUTABLEUDP 		 = PortForwardingUDP
BUILD_LIST+=main
all: $(BUILD_LIST)

main: mainTcp mainUdp
	$(COMPILER) $(OPTIONS) -o $(EXECUTABLEUDP) mainUDP.cpp -g logger.o util.o -L/usr/lib -lstdc++ -lpthread -lboost_thread -lboost_system -std=c++11

mainUdp: logger util

mainTcp: logger util
	$(COMPILER) $(OPTIONS) -o $(EXECUTABLETCP) mainTCP.cpp -g logger.o util.o -L/usr/lib -lstdc++ -lpthread -lboost_thread -lboost_system -std=c++11

logger:
	$(COMPILER) $(OPTIONS) -c  ./src/logger.cpp -g -o logger.o -std=c++11

util:
	$(COMPILER) $(OPTIONS) -c  ./src/util.cpp -g -o util.o -std=c++11

clean:
	rm -f  core *.o *.bak *~ *stackdump *#
	rm -f $(EXECUTABLETCP) $(EXECUTABLEUDP)
