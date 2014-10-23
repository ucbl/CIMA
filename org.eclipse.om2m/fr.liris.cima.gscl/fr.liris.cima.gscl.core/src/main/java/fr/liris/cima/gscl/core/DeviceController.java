package fr.liris.cima.gscl.core;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.ipu.service.IpuService;

import fr.liris.cima.gscl.commons.Device;
import fr.liris.cima.gscl.commons.constants.Constants;
import fr.liris.cima.gscl.commons.parser.Parser;
import fr.liris.cima.gscl.device.service.ManagedDeviceService;

/**
 * Specific device controller to perform requests on devices.
 * @author madiallo
 *
 */
public class DeviceController implements IpuService{

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(DeviceController.class);

	/** managed device service*/
	private  ManagedDeviceService managerImpl;
	/** rest client service*/
	public static RestClientService restClientService;


	public DeviceController(ManagedDeviceService deviceManagerImpl) {
		this.managerImpl = deviceManagerImpl;
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

		/**
		 * http://localhost:8181/om2m/gscl/applications/CIMA/devices/DEVICE_0/capabilities/goOn
		 */
		String[] infos = requestIndication.getTargetID().split("/");
		int length = infos.length;

		try {
			if(infos[length - 3].equals(Constants.PATH_CAPABILITIES) && infos[length - 2].equals(Constants.PATH_INVOKE)){
				String deviceId = infos[length - 4];
				String capabilityName = infos[length - 1];

				Device device = managerImpl.getDevice(deviceId);
				if(device != null) {
					requestIndication.setBase(device.getUri());
					requestIndication.setTargetID("");
					//	requestIndication.setTargetID("/"+Constants.PATH_CAPABILITIES+"/"+Constants.PATH_INVOKE+"/"+capabilityName);
					requestIndication.setProtocol(device.getModeConnection());
					LOGGER.info("send request for executing capabilities");
					ResponseConfirm responseConfirm = restClientService.sendRequest(requestIndication);
					//	ResponseConfirm responseConfirm = restClientService.sendRequest(requestIndication);
					return responseConfirm;
				}
			}
		} catch (Exception e) {
			LOGGER.error("IPU Device Error",e);
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

		LOGGER.info("*********Retrieve in DEVICE CONTROLLER ****");

		String []infos = requestIndication.getTargetID().split("/");
		String lastInfo = infos[infos.length - 1];



		if(lastInfo.equals(Constants.PATH_CAPABILITIES)){
			String deviceId = infos[infos.length - 2];
			Device device = managerImpl.getDevice(deviceId);
			LOGGER.info("*********LAST INFO ****"+deviceId);


			if(device != null) {
				LOGGER.info("*********Constant INFO = = = ****"+Constants.PATH_CAPABILITIES);
				String uri = device.getUri();
				requestIndication.setBase(uri);
				requestIndication.setTargetID("capabilities");
				//requestIndication.setTargetID("/"+Constants.PATH_CAPABILITIES);
				requestIndication.setMethod(Constants.METHOD_RETRIEVE);
				requestIndication.setProtocol(device.getModeConnection());
				LOGGER.info("send request for getting capabilities");
				ResponseConfirm responseConfirm = restClientService.sendRequest(requestIndication);
				responseConfirm.getRepresentation();
				return responseConfirm;
			}
		}
		else if(infos[infos.length - 1].equals(Constants.PATH_UNKNOWN_DEVICES)){
				LOGGER.info("******** /manualconfiguration/device/ ***********");
		}

		else if(infos[infos.length - 2].equals(Constants.PATH_UNKNOWN_DEVICES)){
			//if(lastInfo.equals("capability")) {
			LOGGER.info("******** /manualconfiguration/device/<id d'un device>***********");
			//}

		}
		
		else if(infos[infos.length - 3].equals(Constants.PATH_UNKNOWN_DEVICES)){
			if(lastInfo.equals("capability")) {
			LOGGER.info("******** /manualconfiguration/device/<id d'un device>/capability***********");
			}

		}

		else if(infos[infos.length - 4].equals(Constants.PATH_UNKNOWN_DEVICES )){
			if(infos[infos.length - 2].equals("capability")) {
				LOGGER.info("********  /manualconfiguration/device/<id d'un device>/capability/<id d'une capacité>  ***********");
			}
		}
		else if(lastInfo.equals("protocol")) {
			LOGGER.info("********  /manualconfiguration/protocol  ***********");
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

		if(infos[infos.length - 1].equals(Constants.PATH_UNKNOWN_DEVICES)){

			LOGGER.info("*********PATH_UNKNOWN_DEVICES ****");

			String representation = requestIndication.getRepresentation();
			//Device device = Parser.ParseJsonToDevice(representation);
			Device device = Parser.parseObixToDevice(representation);
			if(device == null) {
				return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND, "No device with this information"));
			}
			boolean validate = managerImpl.switchUnknownToKnownDevice(device);
			if(validate == true) {
				confirm = new ResponseConfirm(StatusCode.STATUS_OK, "Device is now known ");
			}
			else {
				confirm = new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_INTERNAL_SERVER_ERROR, ""));
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
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method not Implemented"));
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
			Device device = Parser.parseXmlDevice(representation);

			if(device != null) {
				LOGGER.info("***DEVICE TO OBIX FORMAT***");
				LOGGER.info(device.toObixFormat());
				managerImpl.addDevice(device);
			}
		}
		else if(infos[infos.length - 3].equals(Constants.PATH_UNKNOWN_DEVICES)){
			if(lastInfo.equals("test")) {
				LOGGER.info("********  /manualconfiguration/device/<id d'un device>/test  ***********");
			}
		}

		ResponseConfirm confirm = new ResponseConfirm(StatusCode.STATUS_OK,"Device created successfully");
		LOGGER.info("confirm *** "+ confirm);
		LOGGER.info("url ***"+requestIndication.getUrl());
		return confirm;
	}
}