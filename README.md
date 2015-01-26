    C.I.M.A.

#About
CIMA is an interoperability platform which connect objects to Web Clients. It use [OM2M eclipse framework](http://eclipse.org/om2m/).

As OM2M, CIMA is made up three parts :
- NSCL, the cloud part :
This is the part wich in contact with Webapp which want to use connected object. This part can be deploy out of the CIMA plateform
- GSCL, the object agregator part :
This part collect all the objects wich are accessible by the plateform.
- Device detection code :
This part is added on device if you want your device can be automatically detected.

NOTE : For now CIMA is only Mac and Linux compatible.

#Install
To install CIMA simply use the script `launchCima.sh` with install option:
```bash
$ ./launchCima.sh -i
```
The script need **maven 3** and **sudo** installed to correctly run.
Don't forget to add execute permission with `chmod` command
```bash
$ chmod +x launchCima.sh
```

#Use
You may launch launchCima.sh tow times to execute CIMA :

 - First to launch nscl component

```bash
$ ./launchCima.sh -n
```
 - Then to launch gscl component

```bash
$ ./launchCima.sh -g
```
NOTE : the script launch GSCL in root because he have to access to the `arp-scan` command.

Now you can connect on administration interface with url http://localhost:8080/cima.

#License
See LICENSE.md

#Contact

#Authors

#Thanks

#Credits
