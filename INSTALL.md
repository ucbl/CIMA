CIMA Middleware - INSTALL
===========================

1. Requirements
---------------
To compile the source code of the CIMA platform you must have:

 * OpenJDK 7 , or Oracle JDK 7
 * Maven 3([http://maven.apache.org])

The CIMA platform is an OSGi-based platform. So, in order to run
the CIMA , you must have an OSGi platform. It exists several
implementations of the OSGi specifications:

 * [Equinox](http://www.eclipse.org/equinox/)

2. Compilation
--------------
To compile the source code of the CIMA platform you must execute
the following command:

    $ mvn clean install

3. Configuration
----------------

Une fois compilé vous devez configuré CIMA pour le NSCL et le GSCL comme suit : 
 * NSCL 
		modifier à vous besoins le fichier org.eclipse.om2m.site.nscl/target/products/nscl/linux/gtk/x86_64/configuration/config.ini
 * GSCL
		modifier à vous besoins le fichier org.eclipse.om2m.site.gscl/target/products/nscl/linux/gtk/x86_64/configuration/config.ini

4. Running
----------

Run NSCl after GSCL, by : 
 * [NSCL]java -jar -ea -Declipse.ignoreApp=true -Dosgi.clean=true -Ddebug=true plugins/org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar -console -noExit 
 * [GSCL]java -jar -ea -Declipse.ignoreApp=true -Dosgi.clean=true -Ddebug=true plugins/org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar -console -noExit 





