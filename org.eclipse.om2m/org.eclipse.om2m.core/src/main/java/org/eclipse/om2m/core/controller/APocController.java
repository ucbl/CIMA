package org.eclipse.om2m.core.controller;

import org.eclipse.om2m.commons.resource.Application;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.core.comm.RestClient;
import org.eclipse.om2m.core.dao.DAOFactory;

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.*;

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
        try{
            fh = new FileHandler("log/core.log", true);
        LOGGER.addHandler(fh);
        fh.setFormatter(new SimpleFormatter());}
        catch(IOException ex){}


        LOGGER.info("Application URI : " + applicationUri);
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
