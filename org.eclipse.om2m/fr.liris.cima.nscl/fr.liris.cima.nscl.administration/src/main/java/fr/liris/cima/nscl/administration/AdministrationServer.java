package fr.liris.cima.nscl.administration;

import java.util.List;

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

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.*;
import java.io.IOException;
import java.util.Scanner;


public class AdministrationServer implements IpuService{
	private static Logger LOGGER = Logger.getLogger(AdministrationServer.class.getName());
	private  static  Handler fh ;

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
		try{
			fh = new FileHandler("log/nsclAdministration.log", true);
		LOGGER.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


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

                                System.out.println("++++++++++++++++++++++++++++++++++++++"+ resp.getRepresentation());
				resp.setRepresentation(Parser.parseObixToJSONStringDevices(resp.getRepresentation()));
                //                resp.setRepresentation(Parser.hydrateststring(resp.getRepresentation()));


                                
				return resp;
//				return new ResponseConfirm(StatusCode.STATUS_OK, "[{\"id\" : \"0123456789\",\"name\" : \"monObjet\",\"uri\" : \"http://192.168.0.2\",\"dateConnection\" : \"10/10/14\",\"modeConnection\" : \"http\"}]");
			case "protocol" :
//				if(protocolResolver != null){
//					resp = new ResponseConfirm(StatusCode.STATUS_OK, Parser.parseProtocolsToJSONString(protocolResolver));
//				}
				return new ResponseConfirm(StatusCode.STATUS_OK, "[{\"protocolName\" : \"http\",\"parameters\" : [{\"name\" : \"method\",\"value\" : \"\" },{\"name\" : \"port\",\"value\" : \"\"},{\"name\" : \"uri\",\"value\" : \"\" },{\"name\" : \"body\",\"value\" : \"\"}]}]");
			case "capabilities" :
				List<String> filters = requestIndication.getParameters().get("filter");
				String sFilters = "";
				boolean first = true;
				for(String sFilter : filters){
					if(first == true) first = false;
					else sFilter+="+";
					sFilters += sFilter;
				}
				requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all/capabilities?filter="+sFilters);
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(Parser.parseObixToJSONStringCapabilities(resp.getRepresentation()));
				return resp;
			case "vocabulaire" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.vocabEV3());

				return resp;
			case "contextDevice" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.contextDevice());

				return resp;
			case "contextCapabilities" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.contextCapabilities());

				return resp;
			case "sensor-S1-EV3UltrasonicSensor" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.sensorS1EV3UltrasonicSensor());

				return resp;
			case "sensor-S1-EV3UltrasonicSensor/Distance" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.sensorS1EV3UltrasonicSensorDistance());

				return resp;
			case "sensor-S1-EV3UltrasonicSensor/Listen" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.sensorS1EV3UltrasonicSensorListen());

				return resp;
			case "sensor-S4-EV3GyroSensor" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.sensorS4EV3GyroSensor());

				return resp;
			case "sensor-S4-EV3GyroSensor/Rate" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.sensorS4EV3GyroSensorRate());

				return resp;
			case "sensor-S4-EV3GyroSensor/Angle" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.sensorS4EV3GyroSensorAngle());

				return resp;
			case "sensor-S4-EV3GyroSensor/Angle and Rate" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.sensorS4EV3GyroSensorAngleRate());

				return resp;
			case "motor-A-rotate" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.motorARotate());

				return resp;
			case "motor-A-forward" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.motorAForward());

				return resp;
			case "motor-A-backward" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.motorABackward());

				return resp;
			case "motor-A-stop" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.motorAStop());

				return resp;
			case "motor-D-rotate" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.motorDRotate());

				return resp;
			case "motor-D-forward" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.motorDForward());

				return resp;
			case "motor-D-backward" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.motorDBackward());

				return resp;
			case "motor-D-stop" :
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(HydraGen.motorDStop());

				return resp;
		}

		} else if(tID.length == 6){
			// nscl/applications/CIMA/administration/device/<device id>/
			requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all/" + tID[5]);
			resp = restClientService.sendRequest(requestIndication);
			resp.setRepresentation(Parser.parseObixToJSONStringDevice(resp.getRepresentation()));
			return resp;
//			return new ResponseConfirm(StatusCode.STATUS_OK, "[{\"id\" : \"0123456789\",\"name\" : \"monObjet\",\"uri\" : \"http://192.168.0.2\",\"dateConnection\" : \"10/10/14\",\"modeConnection\" : \"http\"}]");
		} else if(tID.length == 7){
			// nscl/applications/CIMA/administration/device/<device id>/capability
			requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all/" + tID[5] + "/capability");
			resp = restClientService.sendRequest(requestIndication);
			resp.setRepresentation(Parser.parseObixToJSONStringDevice(resp.getRepresentation()));
			return resp;
//			return new ResponseConfirm(StatusCode.STATUS_OK, "");
		} else if(tID.length == 8){
			// nscl/applications/CIMA/administration/device/<device id>/capability/<capability id>
			requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all/" + tID[5] + "/capability/" + tID[7]);
			resp = restClientService.sendRequest(requestIndication);
			resp.setRepresentation(Parser.parseObixToJSONStringDevice(resp.getRepresentation()));
			return resp;
//			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getMethod()+" capability not found"));
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
			return resp;
//			return new ResponseConfirm(StatusCode.STATUS_OK, body);
		} else if(tID.length == 8){
			// nscl/applications/CIMA/administration/device/<device id>/capability/<capability id>
			requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all/" + tID[5] + "/capability/" + tID[7]);
			requestIndication.setRepresentation(Parser.parseJSONToObixStringCapability(requestIndication.getRepresentation()));
			resp = restClientService.sendRequest(requestIndication);
			resp.setRepresentation(Parser.parseObixToJSONStringCapability(resp.getRepresentation()));
			return resp;
//			return new ResponseConfirm(StatusCode.STATUS_OK, body);
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
			return resp;
//			return new ResponseConfirm(StatusCode.STATUS_OK, "ressource " + tID[7] + " deleted");
		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getMethod()+" ressource not found"));
	}

	@Override
	// POST with body
	public ResponseConfirm doCreate(RequestIndication requestIndication) {
		try{
			fh = new FileHandler("log/nsclAdministration.log", true);
		LOGGER.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


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
			return resp;
//			return new ResponseConfirm(StatusCode.STATUS_OK, "blablabla");
		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getMethod()+" ressource not found"));
	}

	@Override
	public String getAPOCPath() {
		return "administration";
	}

	private String getFile(String fileName) {

		StringBuilder result = new StringBuilder("");

		//Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();

	}

}
