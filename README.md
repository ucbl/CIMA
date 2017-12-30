 # C.I.M.A. : "Couche d'Interopérabilité Matériels-Applications"

## About CIMA
CIMA is a platform project developed by the [LIRIS laboratory](http://liris.cnrs.fr/) and students from [Claude Bernard university](www.univ-lyon1.fr/).
This project aims to create an interoperability layer for the [ASAWoO](http://liris.cnrs.fr/asawoo/) project. This means that CIMA is the platform on top of which the ASAWoO platform will be built. It aims to discover and access connected objects and provide its clients with Web-standard access to those.

## Architecture
CIMA is based on the [Eclipse OM2M](http://eclipse.org/om2m/) framework. It adds features that allow discovery, semantic descriptions and configuration of objects.

### Vocabulary
CIMA's terms definitions are based on OM2M and ASAWoO concepts :
- NSCL : NSCL is the top part of OM2M framework, this is the part which is contacted by clients web-apps to retrieve connected devices infos.
- GSCL : GSCL is the middle part of OM2M framework, this is the part which aggregate connected devices. This part manage connected devices and is in contact with nscl and web-app which want to use a device's capability.
- Capability : a capability is a device basic action, which can not be splitted.

### Structure of the project
As OM2M, CIMA is composed of three parts :
- NSCL, the cloud part :
This is the part which is in contact with the web-apps which want to use connected object. This part can be deployed out of the CIMA platform.
- GSCL, the object aggregating part :
This part collects all the objects which are accessible by the platform.
- Device detection code :
This part is added on device if you want your device to be automatically detected.

More information on the platform architecture on [the CIMA page of the ASAWoO wiki](http://liris.cnrs.fr/asawoo/doku.php?id=cima).

*NOTE* : For now CIMA is only Linux compatible (tested with Debian)

## Install
You need to install first mongodb and restheart api :
```bash
$ ./INSTALL/install_mongodb.sh
$ ./INSTALL/install_restheart.sh
```
To install CIMA simply use the script `launchCima.sh` with install option:
```bash
$ ./launchCima.sh -i
```
The script needs **maven 3** and **sudo** installed to correctly run.
Don't forget to add execute permission with `chmod` command
```bash
$ chmod +x launchCima.sh
```

## Use
First you have to launch mongodb and restheart api :
 - Mongodb : just execute the script :
```bash
$ ./START/start_mongodb.sh
```

*NOTE* : You just have to launch mongodb one time. MongoDB has not just one way to start, if it does not work, you can try
```bash
$ sudo service mongodb start
or
$ sudo mongod --fork --syslog
```
This depends of you OS.

 - RestHeart API : just execute the script :
```bash
$ ./START/start_resthart.sh
```
*NOTE* : Resheart needs java 8.

Then you can launch CIMA :

 - First launch the NSCL component :
```bash
$ sudo ./launchCima.sh -n
```
 - Then launch the GSCL component :
```bash
$ sudo ./launchCima.sh -g
```

Now you can connect on administration interface with url http://localhost:8080/.

## Make an object compatible with the CIMA platform
Please read the CIMA robot documentation.

## License
See LICENSE.md

## Contact
Lionel Médini

## Contributors

  * Alexandre Boukhlif
  * Hady Mamadou Diallo
  * Rémy Desmargez
  * Maxime Baudin
  * Farah Chetouane
  * Harold Ngaleu Toumeni
  * Maxime Guyaux
  * Hicham Bouchikhi
  * Malik Abdelkader
  * Quan-Khanh Lu
  * Ricardo Yamamotu
  * Frédéric Da Silva
  * Jordan Martin
  * Christophe Pinelli

## Thanks

  * Hervé Saladin, from the LIRIS platform team
  * Christian Wolf, head of the LIRIS-VISION platform
  * Eric Lombardi, Olivier Georgeon, Erwan Guillou, Aurélien Tabard (and others) for their time during the use case study
  * Mehdi Terdjimi, for his help during design and development stages
