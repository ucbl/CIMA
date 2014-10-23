package fr.liris.cima.nscl.device.manualconfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.ipu.service.IpuService;

import fr.liris.cima.nscl.commons.constants.*;

public class ManualConfigurationServer implements IpuService{
	private static Log LOGGER = LogFactory.getLog(ManualConfigurationServer.class);
	
	/** rest client service*/
	public static RestClientService restClientService;
	
	
	@Override
	// POST without body
	public ResponseConfirm doExecute(RequestIndication requestIndication) {
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method not Implemented"));
	}

	@Override
	// GET
	public ResponseConfirm doRetrieve(RequestIndication requestIndication) {
//		LOGGER.info("Base : " + requestIndication.getBase());
//		LOGGER.info("Method : " + requestIndication.getMethod());
//		LOGGER.info("Protocol : " + requestIndication.getProtocol());
//		LOGGER.info("Representation : " + requestIndication.getRepresentation());
//		LOGGER.info("RequestingEntity : " + requestIndication.getRequestingEntity());
//		LOGGER.info("TargetID : " + requestIndication.getTargetID());
//		LOGGER.info("Url : " + requestIndication.getUrl());
		ResponseConfirm resp = null;
		RequestIndication request = new RequestIndication();
		String [] tID = requestIndication.getTargetID().split("/");

		request.setBase("");
		request.setMethod("RETRIEVE");
		request.setProtocol("http");
		request.setRequestingEntity(Constants.REQENTITY);
		request.setRepresentation("");
		
		if(tID.length == 5){
			// nscl/applications/configuration/manualconfiguration/device return the list of unrecognized devices
			// nscl/applications/configuration/manualconfiguration/protocol return the spported protocol list
			switch(tID[4]){
			case "device" :
				request.setTargetID("gscl/applications/CIMA/devices/unknown");
				resp = restClientService.sendRequest(requestIndication);
				return new ResponseConfirm(StatusCode.STATUS_OK, "[{\"id\" : \"0123456789\",\"name\" : \"monObjet\",\"uri\" : \"http://192.168.0.2\",\"dateConnection\" : \"10/10/14\",\"modeConnection\" : \"http\"}]");
			case "protocol" :
				request.setTargetID("gscl/applications/CIMA/devices/protocol");
				resp = restClientService.sendRequest(requestIndication);
				return new ResponseConfirm(StatusCode.STATUS_OK, "[{\"id\" : \"0123456789\",\"name\" : \"monObjet\",\"uri\" : \"http://192.168.0.2\",\"dateConnection\" : \"10/10/14\",\"modeConnection\" : \"http\"}]");
				return new ResponseConfirm(StatusCode.STATUS_OK, "{\"protocoleName\" : \"http\",\"parameters\" : [{\"name\" : \"method\",\"value\" : \"\" },{\"name\" : \"port\",\"value\" : \"\"},{\"name\" : \"uri\",\"value\" : \"\" },{\"name\" : \"body\",\"value\" : \"\"}]}");
			}
			
		} else if(tID.length == 6){
			// nscl/applications/configuration/manualconfiguration/device/<device id>/
			request.setTargetID("gscl/applications/CIMA/devices/unknown/" + tID[5]);
			resp = restClientService.sendRequest(request);
			return new ResponseConfirm(StatusCode.STATUS_OK, "[{\"id\" : \"0123456789\",\"name\" : \"monObjet\",\"uri\" : \"http://192.168.0.2\",\"dateConnection\" : \"10/10/14\",\"modeConnection\" : \"http\"}]");
		} else if(tID.length == 7){
			// nscl/applications/configuration/manualconfiguration/device/<device id>/capability
			request.setTargetID("gscl/applications/CIMA/devices/unknown/" + tID[5] + "/capability");
			resp = restClientService.sendRequest(request);
			return new ResponseConfirm(StatusCode.STATUS_OK, "");
		} else if(tID.length == 8){
			// nscl/applications/configuration/manualconfiguration/device/<device id>/capability/<capability id>
			request.setTargetID("gscl/applications/CIMA/devices/unknown/" + tID[5] + "/capability/" + tID[7]);
			resp = restClientService.sendRequest(request);
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getMethod()+" capability not found"));
		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getMethod()+" ressource not found"));
	}

	@Override
	// PUT
	public ResponseConfirm doUpdate(RequestIndication requestIndication) {
		String [] tID = requestIndication.getTargetID().split("/");
		String body = requestIndication.getRepresentation();
		ResponseConfirm resp = null;
		RequestIndication request = new RequestIndication();
		
		request.setBase("");
		request.setMethod("UPDATE");
		request.setProtocol("http");
		request.setRequestingEntity(Constants.REQENTITY);
		if(tID.length == 6){
			// nscl/applications/configuration/manualconfiguration/device/<device id>/
			request.setTargetID("gscl/applications/CIMA/devices/unknown/" + tID[5]);
			request.setRepresentation("");
			resp = restClientService.sendRequest(request);
			return new ResponseConfirm(StatusCode.STATUS_OK, body);
		} else if(tID.length == 8){
			// nscl/applications/configuration/manualconfiguration/device/<device id>/capability/<capability id>
			request.setTargetID("gscl/applications/CIMA/devices/unknown/" + tID[5] + "capability/" + tID[7]);
			request.setRepresentation("");
			resp = restClientService.sendRequest(request);
			return new ResponseConfirm(StatusCode.STATUS_OK, body);
		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getMethod()+" ressource not found"));
	}

	@Override
	// DELETE
	public ResponseConfirm doDelete(RequestIndication requestIndication) {
		String [] tID = requestIndication.getTargetID().split("/");
		
		ResponseConfirm resp = null;
		RequestIndication request = new RequestIndication();
		
		request.setBase("");
		request.setMethod("DELETE");
		request.setProtocol("http");
		request.setRequestingEntity(Constants.REQENTITY);
		if(tID.length == 8){
			// nscl/applications/configuration/manualconfiguration/device/<device id>/capability/<capability id>
			request.setTargetID("gscl/applications/CIMA/devices/unknown" + tID[5] + "capability/" + tID[7]);
			resp = restClientService.sendRequest(request);
			return new ResponseConfirm(StatusCode.STATUS_OK, "ressource " + tID[7] + " deleted");
		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getMethod()+" ressource not found"));
	}

	@Override
	// POST with body
	public ResponseConfirm doCreate(RequestIndication requestIndication) {
		String [] tID = requestIndication.getTargetID().split("/");
		
		ResponseConfirm resp = null;
		RequestIndication request = new RequestIndication();
		
		request.setBase("");
		request.setMethod("CREATE");
		request.setProtocol("http");
		request.setRequestingEntity(Constants.REQENTITY);
		if(tID.length == 6){
			// nscl/applications/configuration/manualconfiguration/device/<device id>/test
			request.setTargetID("gscl/applications/CIMA/devices/unknown/" + tID[5] + "/test");
			request.setRepresentation("");
			resp = restClientService.sendRequest(request);
			return new ResponseConfirm(StatusCode.STATUS_OK, "blablabla");
		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getMethod()+" ressource not found"));
	}

	@Override
	public String getAPOCPath() {
		return "manualconfiguration";
	}

}
