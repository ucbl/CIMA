#C.I.M.A.

##About
CIMA is a platform project developed by the [LIRIS laboratory](http://liris.cnrs.fr/) and students from [Claude Bernard university](www.univ-lyon1.fr/).
The project aims to create an interoperability platform to connect all objects and use those with web-apps. It use [OM2M eclipse framework](http://eclipse.org/om2m/).

As OM2M, CIMA is composed of three parts :
- NSCL, the cloud part :
This is the part which is in contact with the web-apps which want to use connected object. This part can be deployed out of the CIMA platform.
- GSCL, the object aggregating part :
This part collects all the objects which are accessible by the platform.
- Device detection code :
This part is added on device if you want your device can be automatically detected.

NOTE : For now CIMA is only Mac and Linux compatible

##Vocabulary
We need to define some words :
- NSCL : NSCL is the top part of OM2M framework, this is the part which is contacted by clients web-apps to retrieve connected devices infos.
- GSCL : GSCL is the middle part of OM2M framework, this is the part which aggregate connected devices. This part manage connected devices and is in contact with nscl and web-app which want to use a device's capability.
- Capability : a capability is a device basic action, which can not be splitted.

##Install
To install CIMA simply use the script `launchCima.sh` with install option:
```bash
$ ./launchCima.sh -i
```
The script need **maven 3** and **sudo** installed to correctly run.
Don't forget to add execute permission with `chmod` command
```bash
$ chmod +x launchCima.sh
```

##Use
You may launch launchCima.sh tow times to execute CIMA :

 - First to launch nscl component :
```bash
$ ./launchCima.sh -n
```
 - Then to launch gscl component :
```bash
$ ./launchCima.sh -g
```
NOTE : the script launch GSCL with sudo because he have to access to the `arp-scan` command.

Now you can connect on administration interface with url http://localhost:8080/cima.

##Make an object compatible with the CIMA platform
To make an object compatible with the CIMA platform in HTTP, you must create a /infos REST resource that responds to GET requests.
Response format pattern :
```xml
<device>
  <!-- object's name -->
  <name></name>
  <!-- object's connection's mode (ex : IP) -->
  <modeConnection></modeConnection>
  <!-- object's base uri (http://192.168.0.1:8080/) -->
  <uri></uri>
  <!-- a list of capabilities -->
  <capabilities>
    <!-- a capability -->
    <capability>
      <!-- a capability id /!\ Must be unique -->
      <id></id>
      <!-- the protocol to contact the capability -->
      <protocol>
        <!-- the protocol name (ex : HTTP)-->
        <protocolName></protocolName>
        <!-- some protocol's attributes -->
        <!-- /!\ Attributes can change according to the protocol -->
        <!-- used -->
        <!-- here we have an example with HTTP protocol -->
        <!-- HTTP method (ex : GET, POST, PUT, DELETE...) -->
        <method></method>
        <!-- HTTP contact port (ex : 80, 8080...) -->
        <port></port>
        <!-- The capability uri (ex : /mycapability) -->
        <uri></uri>
      </protocol>
      <!-- some keywords which can class the capability -->
      <keywords>
      <keyword></keyword>
      </keywords>
    </capability>
  </capabilities>
</device>
```

##License
See LICENSE.md

##Contact

##Authors

##Thanks

##Credits
