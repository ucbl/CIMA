package fr.liris.cima.nscl.administration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.ipu.service.IpuService;

import fr.liris.cima.comm.protocol.ProtocolResolver;
import fr.liris.cima.nscl.commons.constants.Constants;
import fr.liris.cima.nscl.commons.parser.Parser;

public class AdministrationServer implements IpuService{
	private static Log LOGGER = LogFactory.getLog(AdministrationServer.class);
	
	/** rest client service*/
	public static RestClientService restClientService;

	public static ProtocolResolver protocolResolver;
	
	public static final String GSCL_DEVICES_CONTACT = "om2m/gscl/applications/CIMA/devices";
	
	@Override
	// POST without body
	public ResponseConfirm doExecute(RequestIndication requestIndication) {
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,"POST must have a body"));
	}
	@Override
	// GET
	public ResponseConfirm doRetrieve(RequestIndication requestIndication) {
		LOGGER.info("Base : " + requestIndication.getBase());
		LOGGER.info("Method : " + requestIndication.getMethod());
		LOGGER.info("Protocol : " + requestIndication.getProtocol());
		LOGGER.info("Representation : " + requestIndication.getRepresentation());
		LOGGER.info("RequestingEntity : " + requestIndication.getRequestingEntity());
		LOGGER.info("TargetID : " + requestIndication.getTargetID());
		LOGGER.info("Url : " + requestIndication.getUrl());
		ResponseConfirm resp = null;
		String [] tID = requestIndication.getTargetID().split("/");

		requestIndication.setBase("127.0.0.1:8181/");
//		request.setMethod("RETRIEVE");
//		request.setProtocol("http");
//		request.setRequestingEntity(Constants.REQENTITY);
		requestIndication.setRepresentation("");
		
		if(tID.length == 5){
			// nscl/applications/CIMA/administration/device return the list of unrecognized devices
			// nscl/applications/CIMA/administration/protocol return the spported protocol list
			switch(tID[4]){
			case "device" :
				requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all");
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(Parser.parseObixToJSONStringDevices(resp.getRepresentation()));
//				return resp;
				return new ResponseConfirm(StatusCode.STATUS_OK, "[{\"id\" : \"0123456789\",\"name\" : \"monObjet\",\"uri\" : \"http://192.168.0.2\",\"dateConnection\" : \"10/10/14\",\"modeConnection\" : \"http\"}]");
			case "protocol" :
				if(protocolResolver != null){
					resp = new ResponseConfirm(StatusCode.STATUS_OK, Parser.parseProtocolsToJSONString(protocolResolver));
				}
//				return new ResponseConfirm(StatusCode.STATUS_OK, "[{\"protocolName\" : \"http\",\"parameters\" : [{\"name\" : \"method\",\"value\" : \"\" },{\"name\" : \"port\",\"value\" : \"\"},{\"name\" : \"uri\",\"value\" : \"\" },{\"name\" : \"body\",\"value\" : \"\"}]}]");
			case "capabilities" :
				requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/capabilities");
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(Parser.parseObixToJSONStringCapabilities(resp.getRepresentation()));
				return resp;
			}
			
		} else if(tID.length == 6){
			// nscl/applications/CIMA/administration/device/<device id>/
			requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all/" + tID[5]);
			resp = restClientService.sendRequest(requestIndication);
			resp.setRepresentation(Parser.parseObixToJSONStringDevice(resp.getRepresentation()));
//			return resp;
			return new ResponseConfirm(StatusCode.STATUS_OK, "[{\"id\" : \"0123456789\",\"name\" : \"monObjet\",\"uri\" : \"http://192.168.0.2\",\"dateConnection\" : \"10/10/14\",\"modeConnection\" : \"http\"}]");
		} else if(tID.length == 7){
			// nscl/applications/CIMA/administration/device/<device id>/capability
			requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all/" + tID[5] + "/capability");
			resp = restClientService.sendRequest(requestIndication);
			resp.setRepresentation(Parser.parseObixToJSONStringDevice(resp.getRepresentation()));
//			return resp;
			return new ResponseConfirm(StatusCode.STATUS_OK, "");
		} else if(tID.length == 8){
			// nscl/applications/CIMA/administration/device/<device id>/capability/<capability id>
			requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all/" + tID[5] + "/capability/" + tID[7]);
			resp = restClientService.sendRequest(requestIndication);
			resp.setRepresentation(Parser.parseObixToJSONStringDevice(resp.getRepresentation()));
//			return resp;
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
		requestIndication.setBase("127.0.0.1:8181/");
		if(tID.length == 6){
			// nscl/applications/CIMA/administration/device/<device id>/
			requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all/" + tID[5]);
			requestIndication.setRepresentation(Parser.parseJSONToObixStringDevice(requestIndication.getRepresentation()));
			resp = restClientService.sendRequest(requestIndication);
			resp.setRepresentation(Parser.parseObixToJSONStringDevice(resp.getRepresentation()));
//			return resp;
			return new ResponseConfirm(StatusCode.STATUS_OK, body);
		} else if(tID.length == 8){
			// nscl/applications/CIMA/administration/device/<device id>/capability/<capability id>
			requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all/" + tID[5] + "/capability/" + tID[7]);
			requestIndication.setRepresentation(Parser.parseJSONToObixStringCapability(requestIndication.getRepresentation()));
			resp = restClientService.sendRequest(requestIndication);
			resp.setRepresentation(Parser.parseObixToJSONStringCapability(resp.getRepresentation()));
//			return resp;
			return new ResponseConfirm(StatusCode.STATUS_OK, body);
		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getMethod()+" ressource not found"));
	}

	@Override
	// DELETE
	public ResponseConfirm doDelete(RequestIndication requestIndication) {
		String [] tID = requestIndication.getTargetID().split("/");
		
		ResponseConfirm resp = null;
		requestIndication.setBase("127.0.0.1:8181/");
		requestIndication.setRepresentation("");
		if(tID.length == 8){
			// nscl/applications/CIMA/administration/device/<device id>/capability/<capability id>
			requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all/" + tID[5] + "/capability/" + tID[7]);
			resp = restClientService.sendRequest(requestIndication);
//			return resp;
			return new ResponseConfirm(StatusCode.STATUS_OK, "ressource " + tID[7] + " deleted");
		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getMethod()+" ressource not found"));
	}

	@Override
	// POST with body
	public ResponseConfirm doCreate(RequestIndication requestIndication) {
		String [] tID = requestIndication.getTargetID().split("/");
		
		ResponseConfirm resp = null;
		requestIndication.setBase("127.0.0.1:8181/");
		LOGGER.info("++++++++++++++++++++");
		LOGGER.info("tID.lengh = " + tID.length);
		if(tID.length == 7){
			// nscl/applications/CIMA/administration/device/<device id>/test
			requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all/" + tID[5] + "/test");
			requestIndication.setRepresentation(Parser.parseJSONToObixStringCapability(requestIndication.getRepresentation()));
			resp = restClientService.sendRequest(requestIndication);
//			return resp;
			return new ResponseConfirm(StatusCode.STATUS_OK, "blablabla");
		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getMethod()+" ressource not found"));
	}

	@Override
	public String getAPOCPath() {
		return "administration";
	}

}
