 #C.I.M.A. :

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

NOTE : For now CIMA is only Linux compatible

##Vocabulary
We need to define some words :
- NSCL : NSCL is the top part of OM2M framework, this is the part which is contacted by clients web-apps to retrieve connected devices infos.
- GSCL : GSCL is the middle part of OM2M framework, this is the part which aggregate connected devices. This part manage connected devices and is in contact with nscl and web-app which want to use a device's capability.
- Capability : a capability is a device basic action, which can not be splitted.

##Install
You need to install first mongodb and restheart api :
``bash
$ ./INSTALL/install_mongodb.sh
$ ./INSTALL/install_restheart.sh
```
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
First you have to launch mongodb and restheart api :
 - Mongodb : just execute the script :
```bash
$ ./START/start_mongodb.sh
```
NOTE : You just have to launch mongodb one time. MongoDB has not just one way to start, if it not work, you can try
```bash
$ sudo service mongodb start
or
$ sudo mongod --fork --syslog
```
It depends of you exploitation system.
 - RestHeart API : just execute the script :
```bash
$ ./START/start_resthart.sh
```
NOTE : Resheart need java 8.

Then you can launch CIMA :


 - First to launch nscl component :
```bash
$ sudo ./launchCima.sh -n
```
 - Then to launch gscl component :
```bash
$ sudo ./launchCima.sh -g
```

Now you can connect on administration interface with url http://localhost:8080/.

##Make an object compatible with the CIMA platform
Please read the CIMA robot documentation.

##License
See LICENSE.md

##Contact

##Authors

##Thanks

##Credits
