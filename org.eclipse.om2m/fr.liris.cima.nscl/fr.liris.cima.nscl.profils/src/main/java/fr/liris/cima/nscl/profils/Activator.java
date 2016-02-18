package fr.liris.cima.nscl.profils;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.framework.ServiceReference;


import fr.liris.cima.nscl.mongodao.persistance.MongoDaoInterface;

import fr.liris.cima.nscl.profils.profilsExport.Profil;

import fr.liris.cima.nscl.profils.profilsExport.ProfilManagerInterface;
import fr.liris.cima.nscl.profils.profilsExport.ProfileMatchingManagerInterface;

/**
 * Manages the starting and stopping nscl.profils bundle.
 */
public class Activator implements BundleActivator {

    /** SCL service tracker */
    private ServiceTracker<Object, Object> sclServiceTracker;

    private ServiceRegistration serviceRegistration;
    private ServiceRegistration serviceRegistration2;



    public void start(BundleContext bundlecontext) throws Exception{

        //Getting mongodao
        ServiceReference sf = bundlecontext.getServiceReference(MongoDaoInterface.class.getName());
        MongoDaoInterface mongoDaoInterface = (MongoDaoInterface) bundlecontext.getService(sf);

        //Register Profil(e) Manager
        serviceRegistration = bundlecontext.registerService(ProfilManagerInterface.class.getName(), new ProfilManager(mongoDaoInterface), null);
        System.out.println("ProfilManager service rehgisterd succesfully.");


        //Register ProfileMatchingManager
        serviceRegistration2 = bundlecontext.registerService(ProfileMatchingManagerInterface.class.getName(), new ProfileMatchingManager(mongoDaoInterface), null);
        System.out.println("ProfilManager service rehgisterd succesfully.");

    }


    public void stop(BundleContext context) throws Exception{
        serviceRegistration.unregister();
        serviceRegistration2.unregister();
    }
}
