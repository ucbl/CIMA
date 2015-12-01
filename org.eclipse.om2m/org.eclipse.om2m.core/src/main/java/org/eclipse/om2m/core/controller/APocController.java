package org.eclipse.om2m.core.controller;

import org.eclipse.om2m.commons.resource.Application;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.core.comm.RestClient;
import org.eclipse.om2m.core.dao.DAOFactory;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.service.log.*;
import org.osgi.framework.FrameworkUtil;

/** allows the choice of the controller based on the aPoC of the application**/
public class APocController extends Controller{



    public ResponseConfirm doCreate (RequestIndication requestIndication) {

        String sclId = requestIndication.getTargetID().split("/")[0];
        String applicationId = requestIndication.getTargetID().split("/")[2];
        String applicationUri = sclId+"/applications/"+applicationId;
        Application application= DAOFactory.getApplicationDAO().find(applicationUri);
        String aPoCPath = application.getAPoCPaths().getAPoCPath().get(0).getPath();
        if (aPoCPath.matches(".*://.*")){
            String targetID = requestIndication.getTargetID().split(applicationId)[1];
            requestIndication.setBase(aPoCPath);
            requestIndication.setTargetID(targetID);
            return new RestClient().sendRequest(requestIndication);
        }else{
            InterworkingProxyController IPUController= new InterworkingProxyController();
            return IPUController.doCreate(requestIndication);
        }
    }


    public ResponseConfirm doRetrieve (RequestIndication requestIndication) {
      setLogServiceTracker(new ServiceTracker(FrameworkUtil.getBundle(APocController.class).getBundleContext(), org.osgi.service.log.LogService.class.getName(), null));
      getLogServiceTracker().open();
      setLogservice((LogService) getLogServiceTracker().getService());


        String sclId = requestIndication.getTargetID().split("/")[0];
        String applicationId = requestIndication.getTargetID().split("/")[2];
        String applicationUri = sclId+"/applications/"+applicationId;

        Application application= DAOFactory.getApplicationDAO().find(applicationUri);
        String aPoCPath = application.getAPoCPaths().getAPoCPath().get(0).getPath();
        if (aPoCPath.matches(".*://.*")){
            String targetID = requestIndication.getTargetID().split(applicationId)[1];
            requestIndication.setBase(aPoCPath);
            requestIndication.setTargetID(targetID);
            LOGGER.info(targetID);
                        getLogservice().log(LogService.LOG_ERROR, targetID);
            return new RestClient().sendRequest(requestIndication);
        }else{
            Controller IPUController= new InterworkingProxyController();
            return IPUController.doRetrieve(requestIndication);

        }



    }
    public ResponseConfirm doUpdate (RequestIndication requestIndication) {

        String sclId = requestIndication.getTargetID().split("/")[0];
        String applicationId = requestIndication.getTargetID().split("/")[2];
        String applicationUri = sclId+"/applications/"+applicationId;
        Application application= DAOFactory.getApplicationDAO().find(applicationUri);

        /****** DEBUG *******/

        LOGGER.info("Application URI : " + applicationUri);
        getLogservice().log(LogService.LOG_ERROR, "Application URI : " + applicationUri);

        /****** /DEBUG ******/


        String aPoCPath = application.getAPoCPaths().getAPoCPath().get(0).getPath();
        if (aPoCPath.matches(".*://.*")){
            String targetID = requestIndication.getTargetID().split(applicationId)[1];
            requestIndication.setBase(aPoCPath);
            requestIndication.setTargetID(targetID);
            return new RestClient().sendRequest(requestIndication);
        }else{
            Controller IPUController= new InterworkingProxyController();
            return IPUController.doUpdate(requestIndication);
        }
    }



    public ResponseConfirm doDelete (RequestIndication requestIndication) {
        String sclId = requestIndication.getTargetID().split("/")[0];
        String applicationId = requestIndication.getTargetID().split("/")[2];
        String applicationUri = sclId+"/applications/"+applicationId;
        Application application= DAOFactory.getApplicationDAO().find(applicationUri);
        String aPoCPath = application.getAPoCPaths().getAPoCPath().get(0).getPath();
        if (aPoCPath.matches(".*://.*")){
            String targetID = requestIndication.getTargetID().split(applicationId)[1];
            requestIndication.setBase(aPoCPath);
            requestIndication.setTargetID(targetID);
            return new RestClient().sendRequest(requestIndication);
        }else{
            Controller IPUController= new InterworkingProxyController();
            return IPUController.doDelete(requestIndication);
        }
    }

    public ResponseConfirm doExecute (RequestIndication requestIndication) {

        String sclId = requestIndication.getTargetID().split("/")[0];
        String applicationId = requestIndication.getTargetID().split("/")[2];
        String applicationUri = sclId+"/applications/"+applicationId;
        Application application= DAOFactory.getApplicationDAO().find(applicationUri);
        String aPoCPath = application.getAPoCPaths().getAPoCPath().get(0).getPath();
        if (aPoCPath.matches(".*://.*")){
            String targetID = requestIndication.getTargetID().split(applicationId)[1];
            requestIndication.setBase(aPoCPath);
            requestIndication.setTargetID(targetID);
            return new RestClient().sendRequest(requestIndication);
        }else{
            Controller IPUController= new InterworkingProxyController();
            return IPUController.doExecute(requestIndication);
        }

    }

}
