package fr.liris.cima.nscl.mongodao;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import com.google.gson.*;

public class Activator implements BundleActivator {

    /** SCL service tracker */
   // private ServiceTracker<Object, Object> sclServiceTracker;

   // private ServiceRegistration serviceRegistration;


    public void start(BundleContext bundlecontext) throws Exception{

        try {
            System.out.println("BON BON BON BON BON");
            Gson gson = new GsonBuilder().create();
            System.out.println(gson.toJson("Hello"));
            System.out.println(gson.toJson(123));
        }catch (Exception e){
            System.out.println("ERRRRRRRRRRRRRRR "+ e.toString());
        }

    }


    public void stop(BundleContext context) throws Exception{
        //serviceRegistration.unregister();
    }
}
