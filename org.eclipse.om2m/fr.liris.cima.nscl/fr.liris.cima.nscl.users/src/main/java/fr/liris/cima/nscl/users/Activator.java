package fr.liris.cima.nscl.users;

import fr.liris.cima.nscl.users.UsersExport.UserManagerInterface;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.framework.ServiceReference;


import fr.liris.cima.nscl.mongodao.persistance.MongoDaoInterface;


public class Activator implements BundleActivator {

    /** SCL service tracker */
    private ServiceTracker<Object, Object> sclServiceTracker;

    private ServiceRegistration serviceRegistration;



    public void start(BundleContext bundlecontext) throws Exception{


        ServiceReference sf = bundlecontext.getServiceReference(MongoDaoInterface.class.getName());
        MongoDaoInterface mongoDaoInterface = (MongoDaoInterface) bundlecontext.getService(sf);


       serviceRegistration = bundlecontext.registerService(UserManagerInterface.class.getName(), new UserManager(mongoDaoInterface), null);


        System.out.println("UserManager service registerd succesfully.");

    }


    public void stop(BundleContext context) throws Exception{
        //serviceRegistration.unregister();
    }
}
