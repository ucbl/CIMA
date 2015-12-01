package fr.liris.cima.gscl.core;


import java.util.List;
import java.util.Map;

import javax.security.auth.login.Configuration;

import obix.io.ObixEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.ipu.service.IpuService;

import fr.liris.cima.gscl.commons.Capability;
import fr.liris.cima.gscl.commons.Device;
import fr.liris.cima.gscl.commons.DeviceDescription;
import fr.liris.cima.gscl.commons.Encoder;
import fr.liris.cima.gscl.commons.constants.Constants;
import fr.liris.cima.gscl.commons.parser.Parser;
import fr.liris.cima.gscl.device.service.ManagedDeviceService;
import fr.liris.cima.gscl.device.service.capability.CapabilityManager;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.service.log.*;
import org.osgi.framework.FrameworkUtil;

/**
 * Specific device controller to perform requests on devices.
 * @author madiallo
 *
 */
public class DeviceController implements IpuService{

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(DeviceController.class);
	/** Logger OSGI*/
	private static ServiceTracker logServiceTracker;
	private static LogService logservice;

	/** managed device service*/
	private  ManagedDeviceService managerImpl;

	private CapabilityManager capabilityManager;
	/** rest client service*/
	public static RestClientService restClientService;


	public DeviceController(ManagedDeviceService deviceManagerImpl, CapabilityManager capabilityManager) {
		this.managerImpl = deviceManagerImpl;
		this.capabilityManager = capabilityManager;


	}

	/** Returns the implemented Application Point of Contact id */
	@Override
	public String getAPOCPath() {
		return Device.APOCPATH;
	}

	/**
	 * Executes a resource on devices.
	 * @param requestIndication - The generic request to handle.
	 * @return The generic returned response.
	 */
	@Override
	public ResponseConfirm doExecute(RequestIndication requestIndication) {

		LOGGER.info("*********Execute in DEVICE CONTROLLER ****");
			logservice.log(LogService.LOG_ERROR, "*********Execute in DEVICE CONTROLLER ****");

		/**
		 * http://localhost:8181/om2m/gscl/applications/CIMA/devices/DEVICE_1/capabilities/EV3Back
		 */
		String[] infos = requestIndication.getTargetID().split("/");
		int length = infos.length;

		try {
			if(infos[length - 2].equals(Constants.PATH_CAPABILITIES) ){
				String deviceId = infos[length - 3];
				String capabilityName = infos[length - 1];

				Device device = managerImpl.getDevice(deviceId);
				if(device != null) {
					//requestIndication.setBase(device.getUri());
					Capability capability = device.getCapability(capabilityName);
					managerImpl.invokeCapability(deviceId, capability, restClientService);

				//	requestIndication.setTargetID("");
					//	requestIndication.setTargetID("/"+Constants.PATH_CAPABILITIES+"/"+Constants.PATH_INVOKE+"/"+capabilityName);
			//		requestIndication.setProtocol(device.getModeConnection());
			LOGGER.info("send request for executing capabilities");
			logservice.log(LogService.LOG_ERROR, "send request for executing capabilities");
//ResponseConfirm responseConfirm = restClientService.sendRequest(requestIndication);
						ResponseConfirm responseConfirm = new ResponseConfirm(StatusCode.STATUS_ACCEPTED);
					return responseConfirm;
				}
			}
		} catch (Exception e) {
			LOGGER.error("IPU Device Error",e);
			logservice.log(LogService.LOG_ERROR, "IPU Device Error");

			return new ResponseConfirm(StatusCode.STATUS_NOT_IMPLEMENTED,"IPU Device Error");
		}
		return new ResponseConfirm(StatusCode.STATUS_BAD_REQUEST,"Bad request ...");
	}

	/**
	 * Retrieves a resource on devices.
	 * @param requestIndication - The generic request to handle.
	 * @return The generic returned response.
	 */
	@Override
	public ResponseConfirm doRetrieve(RequestIndication requestIndication) {


		LOGGER.info("*********Retrieve in DEVICE CONTROLLER **** " + requestIndication.getTargetID());
		logservice.log(LogService.LOG_ERROR, "*********Retrieve in DEVICE CONTROLLER **** " + requestIndication.getTargetID());
		String []infos = requestIndication.getTargetID().split("/");
		String lastInfo = infos[infos.length - 1];
		LOGGER.info("********* LAST INFOS **** " + lastInfo);
		logservice.log(LogService.LOG_ERROR, "********* LAST INFOS **** " + lastInfo);

		LOGGER.info("********* INFO - 2 **** " + infos[infos.length - 2]);
		logservice.log(LogService.LOG_ERROR, "********* INFO - 2 **** " + infos[infos.length - 2]);
if(lastInfo.equals(Constants.PATH_CAPABILITIES)){
			if(infos[infos.length - 2].equals(Constants.PATH_DEVICES_ALL)){
				// filter on capabilities
				Map<String, List <String>> params = requestIndication.getParameters();
				List<String> lFilter = params.get("filter");
				String filter = "";
				for(String f : lFilter){
					filter += f + "+";
				}
				filter = filter.substring(0, filter.length() - 1);
				List<Capability> capabilites = capabilityManager.getCapabilities(filter);

				return new ResponseConfirm(StatusCode.STATUS_OK, Encoder.encodeCapabilitiesToObix(capabilites));
			} else {
				String deviceId = infos[infos.length - 2];
				Device device = managerImpl.getDevice(deviceId);
				LOGGER.info("*********LAST INFO ****"+deviceId);
								logservice.log(LogService.LOG_ERROR, "*********LAST INFO ****"+deviceId);


				if(device != null) {
					LOGGER.info("*********Constant INFO = = = ****"+Constants.PATH_CAPABILITIES);
									logservice.log(LogService.LOG_ERROR, "*********Constant INFO = = = ****"+Constants.PATH_CAPABILITIES);
					String representation = Encoder.encodeCapabilitiesToObix(device.getCapabilities());
					LOGGER.info("send request for getting capabilities");
										logservice.log(LogService.LOG_ERROR, "send request for getting capabilities");
					ResponseConfirm responseConfirm = new ResponseConfirm(StatusCode.STATUS_OK, representation);
					return responseConfirm;
				}
			}
		}
		else if(infos[infos.length - 1].equals(Constants.PATH_DEVICES_ALL)){
			//device
			String format = managerImpl.devicesToObixFormat();
			LOGGER.info("******** /administration/device/ ***********");
						logservice.log(LogService.LOG_ERROR, "******** /administration/device/ ***********");

			ResponseConfirm confirm = new ResponseConfirm(StatusCode.STATUS_OK, format);
			return confirm;
		}

		else if(infos[infos.length - 2].equals(Constants.PATH_DEVICES_ALL)){
			//if(lastInfo.equals("capability")) {
			LOGGER.info("******** /administration/device/<id d'un device>***********");
						logservice.log(LogService.LOG_ERROR, "******** /administration/device/<id d'un device>***********");
			//}
		}

		else if(infos[infos.length - 3].equals(Constants.PATH_DEVICES_ALL)){
			if(lastInfo.equals("capability")) {
				LOGGER.info("******** /administration/device/<id d'un device>/capability***********");
				logservice.log(LogService.LOG_ERROR, "******** /administration/device/<id d'un device>/capability***********");
				String deviceId = infos[infos.length - 2];
			//	List<Capability> capabilities = managerImpl.getUnknownDeviceCapabilities(deviceId);
				List<Capability> capabilities = managerImpl.getDeviceCapabilities(deviceId);
				//String representation = managerImpl.capabilitiesToObixFormat(capabilities);
				String representation = Encoder.encodeCapabilitiesToObix(capabilities);

				ResponseConfirm confirm = new ResponseConfirm(StatusCode.STATUS_OK, representation);
			}
		}

		else if(infos[infos.length - 4].equals(Constants.PATH_DEVICES_ALL )){
			if(infos[infos.length - 2].equals("capability")) {
				LOGGER.info("********  /administration/device/<id d'un device>/capability/<id d'une capacité>  ***********");
								logservice.log(LogService.LOG_ERROR, "********  /administration/device/<id d'un device>/capability/<id d'une capacité>  ***********");
				String deviceId = infos[infos.length - 3];
				String capabilityId = infos[infos.length - 1];
				String representation;
			//	Capability capability = managerImpl.getCapabilityToUnknownDevice(deviceId, capabilityId);
				Capability capability = managerImpl.getCapabilityDevice(deviceId,  capabilityId);
				//representation = capability.toObixFormat();
				representation = Encoder.encodeCapabilityToObix(capability);

				return new ResponseConfirm(StatusCode.STATUS_OK, representation);

			}
		}

		else if(lastInfo.equals("protocol")) {
			LOGGER.info("********  /administration/protocol  ***********");
						logservice.log(LogService.LOG_ERROR, "********  /administration/protocol  ***********");

		}

		else if(lastInfo.contains("capabilities?filter=")) {
			LOGGER.info("********  /administration/capabilities?filter=<filter>  ***********");
			logservice.log(LogService.LOG_ERROR, "********  /administration/capabilities?filter=<filter>  ***********");
String filter = lastInfo.split("=")[1];
LOGGER.info("filter = " + filter);
			logservice.log(LogService.LOG_ERROR, "filter = " + filter);

			// TODO Le filtre sur les capabilities
			// TODO Où stocke-t-on les capabilities ?
			List<Capability> fCapabilities = this.capabilityManager.getCapabilities(filter);
			obix.List lObix = new obix.List("filter");
			for(Capability c : fCapabilities){
				//lObix.add(c.toObj());
				lObix.add(Encoder.encodeCapabilityToObixObj(c));
			}
			String representation = ObixEncoder.toString(lObix);
			return new ResponseConfirm(StatusCode.STATUS_OK, representation);
		}

		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method not Implemented"));
	}

	/**
	 * Updates a resource on devices.
	 * @param requestIndication - The generic request to handle.
	 * @return The generic returned response.
	 */
	@Override
	public ResponseConfirm doUpdate(RequestIndication requestIndication) {
		String[] infos = requestIndication.getTargetID().split("/");
		int length = infos.length;

		ResponseConfirm confirm = new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST, ""));

		if(infos[infos.length - 2].equals(Constants.PATH_DEVICES_ALL)){

			LOGGER.info("*********PATH_DEVICES_ALL ****");
						logservice.log(LogService.LOG_ERROR, "*********PATH_DEVICES_ALL ****");

			String representation = requestIndication.getRepresentation();
			LOGGER.info("PATH_DEVICES_ALL == "+representation);
						logservice.log(LogService.LOG_ERROR, "PATH_DEVICES_ALL == "+representation);

			//Device device = Parser.ParseJsonToDevice(representation);
			Device device = Parser.parseObixToDevice(representation);
			if(device == null) {
				return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND, "No device with this information"));
			}
			LOGGER.info("DEVICE CAPABILITIES == "+device.getCapabilities());
			logservice.log(LogService.LOG_ERROR, "DEVICE CAPABILITIES == "+device.getCapabilities());
		//	boolean validate = managerImpl.switchUnknownToKnownDevice(device);
			device.setKnown(true);
			Device existDev = managerImpl.getDevice(device.getId());
			if(existDev == null){
				managerImpl.addDevice(device);
			} else {
				managerImpl.updateDevice(device.getId(), device);
			}

			if(device.isKnown() == true) {
				confirm = new ResponseConfirm(StatusCode.STATUS_OK, "Device is now known ");
			}
			else {
				confirm = new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_INTERNAL_SERVER_ERROR, ""));
			}

		}
		else if(infos[infos.length - 4].equals(Constants.PATH_DEVICES_ALL )){
			if(infos[infos.length - 2].equals("capability")) {
				LOGGER.info("******** UPDATE  /administration/device/<id d'un device>/capability/<id d'une capacité>  ***********");
				logservice.log(LogService.LOG_ERROR, "******** UPDATE  /administration/device/<id d'un device>/capability/<id d'une capacité>  ***********");
				String deviceId = infos[infos.length - 3];
				String capabilityId = infos[infos.length - 1];
				String representation;
				//Capability capability = managerImpl.getCapabilityToUnknownDevice(deviceId, capabilityId);
				Capability capability = managerImpl.getCapabilityDevice(deviceId, capabilityId);
				Capability upCapability = Parser.parseObixToCapability(requestIndication.getRepresentation());

				//TODO edit la capability
				// si la capability n'existe pas on en créé une nouvelle
				if(capability == null) capability = new Capability(capabilityId);
				if (upCapability.getName() != null) capability.setName(upCapability.getName());
				if (upCapability.getKeywords() != null) capability.setKeywords(upCapability.getKeywords());
				if (upCapability.getProtocol() != null) capability.setProtocol(upCapability.getProtocol());
				capability.setConfiguration("manual");
				managerImpl.updateDeviceCapability(deviceId, capability);
				return new ResponseConfirm(StatusCode.STATUS_OK, "Capability updated or created");
			}
		}
		return confirm;
	}

	/**
	 * Deletes a resource on devices.
	 * @param requestIndication - The generic request to handle.
	 * @return The generic returned response.
	 */
	@Override
	public ResponseConfirm doDelete(RequestIndication requestIndication) {
		String []infos = requestIndication.getTargetID().split("/");
		String lastInfo = infos[infos.length - 1];

		if(infos[infos.length - 4].equals(Constants.PATH_DEVICES_ALL )){
			if(infos[infos.length - 2].equals("capability")) {
				LOGGER.info("******** DELETE  /administration/device/<id d'un device>/capability/<id d'une capacité>  ***********");
				logservice.log(LogService.LOG_ERROR, "******** DELETE  /administration/device/<id d'un device>/capability/<id d'une capacité>  ***********");

				String deviceId = infos[infos.length - 3];
				String capabilityId = infos[infos.length - 1];
				String representation;
				boolean valid = managerImpl.removeCapabilityDevice(deviceId, capabilityId);
				if(valid)
					return new ResponseConfirm(StatusCode.STATUS_OK, "Capability deleted");
				else
					return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST," No capability to delete"));
			}
		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST," "));
	}

	/**
	 * Create a resource on devices.
	 * @param requestIndication - The generic request to handle.
	 * @return The generic returned response.
	 */
	@Override
	public ResponseConfirm doCreate(RequestIndication requestIndication) {

		String []infos = requestIndication.getTargetID().split("/");
		String lastInfo = infos[infos.length - 1];

		String representation = requestIndication.getRepresentation();

		if(lastInfo.equals(Device.APOCPATH)) {
			DeviceDescription deviceDescription = Parser.parseXmlToDeviceDescription(representation);
			if(deviceDescription != null) {
				Device device = new Device(deviceDescription);
				LOGGER.info("***DEVICE TO OBIX FORMAT***");
								logservice.log(LogService.LOG_ERROR, "***DEVICE TO OBIX FORMAT***");
				//LOGGER.info(device.toObixFormat());
				managerImpl.addDevice(device);
			}
		}
		else if(infos[infos.length - 3].equals(Constants.PATH_DEVICES_ALL)){
			if(lastInfo.equals("test")) {
				String deviceId = infos[infos.length - 2];
			//	Device device = managerImpl.getUnknownDevice(deviceId);
				Device device = managerImpl.getDevice(deviceId);
				if(device != null) {
					Capability capability = Parser.parseObixToCapability(representation);
					managerImpl.invokeCapability(deviceId, capability, restClientService);
				}

				LOGGER.info("********  /administration/device/<id d'un device>/test  ***********");
				logservice.log(LogService.LOG_ERROR, "********  /administration/device/<id d'un device>/test  ***********");

			}
		} else if(infos[infos.length - 3].equals(Constants.PATH_CAPABILITIES) && infos[infos.length - 2].equals(Constants.PATH_INVOKE)){
			String deviceId = infos[infos.length - 4];
			String capabilityName = infos[infos.length - 1];

			Device device = managerImpl.getDevice(deviceId);
			if(device != null) {
				requestIndication.setBase(device.getDeviceDescription().getUri());
				requestIndication.setTargetID("");
				//	requestIndication.setTargetID("/"+Constants.PATH_CAPABILITIES+"/"+Constants.PATH_INVOKE+"/"+capabilityName);
				requestIndication.setProtocol(device.getDeviceDescription().getModeConnection());
				LOGGER.info("send request for executing capabilities");
				logservice.log(LogService.LOG_ERROR, "send request for executing capabilities");
				ResponseConfirm responseConfirm = restClientService.sendRequest(requestIndication);
				//	ResponseConfirm responseConfirm = restClientService.sendRequest(requestIndication);
				return responseConfirm;
			}
		}

		ResponseConfirm confirm = new ResponseConfirm(StatusCode.STATUS_OK,"Device created successfully");
		LOGGER.info("confirm *** "+ confirm);
		logservice.log(LogService.LOG_ERROR, "confirm *** "+ confirm);

		LOGGER.info("url ***" + requestIndication.getUrl());
		logservice.log(LogService.LOG_ERROR, "url ***" + requestIndication.getUrl());

		return confirm;
	}
}
