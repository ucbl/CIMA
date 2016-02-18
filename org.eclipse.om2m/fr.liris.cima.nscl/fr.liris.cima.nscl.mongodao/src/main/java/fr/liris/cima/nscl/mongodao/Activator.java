package fr.liris.cima.nscl.mongodao;

import fr.liris.cima.nscl.mongodao.persistance.MongoDaoInterface;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

/**
 *  Manages the starting and stopping nscl.mongodao bundle.
 *
 */
public class Activator implements BundleActivator {

    /** SCL service tracker */
    private ServiceTracker<Object, Object> sclServiceTracker;

    private ServiceRegistration serviceRegistration;



    public void start(BundleContext bundlecontext) throws Exception{

        String mongodbHost = System.getProperty("fr.liris.cima.nscl.mongodao.host");
        int mongodbPort = Integer.parseInt(System.getProperty("fr.liris.cima.nscl.mongodao.port"));
        String mongodbDbName = System.getProperty("fr.liris.cima.nscl.mongodao.dbname");


        serviceRegistration = bundlecontext.registerService(MongoDaoInterface.class.getName(), new MongoDao(mongodbHost, mongodbPort, mongodbDbName), null);
        System.out.println("MongoDao service registered sucessfully");

    }


    public void stop(BundleContext context) throws Exception{
        serviceRegistration.unregister();
    }
}
