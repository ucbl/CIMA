package fr.liris.cima.nscl.core;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.comm.service.RestClientService;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.ipu.service.IpuService;

import fr.liris.cima.nscl.commons.Device;
import fr.liris.cima.nscl.commons.constants.Constants;
import fr.liris.cima.nscl.commons.parser.Parser;
import fr.liris.cima.nscl.commons.subscriber.ClientSubscriber;
import fr.liris.cima.nscl.commons.encoder.JsonEncoder;
import fr.liris.cima.nscl.device.service.ManagedDeviceService;

/**
 * Specific Infrasctructure controller to perform requests on devices, manage clients subscriptions
 * @author madiallo
 *
 */
public class InfrastructureController implements IpuService{

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(InfrastructureController.class);
	/** managed device service*/
	private  ManagedDeviceService managerImpl;
	/** rest client service*/
	public static RestClientService restClientService;
	
	public InfrastructureController(ManagedDeviceService deviceManagerImpl) {
		this.managerImpl = deviceManagerImpl;
	}

	@Override
	public String getAPOCPath() {
		return Constants.APOCPATH;
	}

	/**
     * Executes a resource on devices,  notify subscribers.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
	@Override
	public ResponseConfirm doExecute(RequestIndication requestIndication) {
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method not Implemented"));
	}

	/**
     * Retrieves a resource on devices.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
	@Override
	public ResponseConfirm doRetrieve(RequestIndication requestIndication) {
		
		String[] infos = requestIndication.getTargetID().split("/");
		int length = infos.length;
		ResponseConfirm responseConfirm = null;
		String representation = JsonEncoder.devicesToJSONStr(managerImpl.getDevices());
		if(infos[length - 1].equals(Constants.APOCPATH_DEVICES)){
			LOGGER.info("********************REPRESENTATION*******"+requestIndication.getUrl());
			requestIndication.setRepresentation(representation);
			responseConfirm = new ResponseConfirm(StatusCode.STATUS_OK, representation);
			return responseConfirm;
		}
		// TODO : Ajouter le retour de la liste des capacités en fonction d'un device
		// /nscl/application/CIMANSCL/devices/<device_id>/capabilities
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST, ""));
	}

	/**
     * Updates a resource on devices, subscribers.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
	@Override
	public ResponseConfirm doUpdate(RequestIndication requestIndication) {
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method not Implemented"));
	}

	/**
     * Deletes a resource on devices, notify subscribers.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
	@Override
	public ResponseConfirm doDelete(RequestIndication requestIndication) {
		String[] infos = requestIndication.getTargetID().split("/");
		int length = infos.length;
		String deviceId = infos[length - 1];
		ResponseConfirm responseConfirm = null;

		if(infos[length - 2].equals(Constants.APOCPATH_DEVICES)){
			LOGGER.info("************DELETE DEVICE IN INFRASTRUCTURE CONTROLLER*****************");
			Device device = managerImpl.removeDevice(deviceId);
			// Send notification to all subscribers
			notifyAllSubscribers(requestIndication, device);
			
			responseConfirm = new ResponseConfirm(StatusCode.STATUS_DELETED, "suppression avec succes");
		}
		
		return responseConfirm;
	}

	/**
     * Create a resource on devices, notify subscribers.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
	@Override
	public ResponseConfirm doCreate(RequestIndication requestIndication) {

		String[] infos = requestIndication.getTargetID().split("/");
		int length = infos.length;

		String representation = requestIndication.getRepresentation();

		if(representation != null) {
			try {
				
				if(infos[length - 1].equals(Constants.APOCPATH_SUBSCRIBERS)){
					ClientSubscriber subscriber = Parser.parseXmlToClientSubscriber(representation);
					int success = managerImpl.addSubscriber(subscriber);
					if(success == 0 || success == 1) {
						if(success == 0 ) LOGGER.info("A client Subscriber added succesfully ");
						else LOGGER.info("The client Subscriber already exist");
						// send all information to this subscriber
						List<Device> devices = managerImpl.getDevices();
						requestIndication.setBase(subscriber.getUrl()+":"+subscriber.getPort());
						requestIndication.setTargetID("");
						requestIndication.setRepresentation(JsonEncoder.devicesToJSONStr(devices));
						LOGGER.info("Sending all devices contact info to new subscriber");
						restClientService.sendRequest(requestIndication);

						return new ResponseConfirm(StatusCode.STATUS_ACCEPTED, "");
					}
				}
				else if(infos[length - 1].equals(Constants.APOCPATH_DEVICES)){
					LOGGER.error("Creating a device in infrastrucure controller and sending contact information to all subscribers");
                  
					Device device = Parser.parseObixToDevice(representation);
					managerImpl.addDevice(device);
					LOGGER.info("device = " + device);
					
					for(ClientSubscriber subscriber : managerImpl.getSubscribers()){
						requestIndication.setBase(subscriber.getUrl());
						requestIndication.setRepresentation(JsonEncoder.contactInfoToJSONStr(device.getContactInfo()));
						restClientService.sendRequest(requestIndication);
					}				
					return new ResponseConfirm(StatusCode.STATUS_ACCEPTED, representation);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST, " Impossible to create device with this request"));
	}
	
	private void notifyAllSubscribers(RequestIndication requestIndication, Device device) {
		for(ClientSubscriber subscriber : managerImpl.getSubscribers()){
			requestIndication.setBase(subscriber.getUrl());
			requestIndication.setRepresentation(JsonEncoder.contactInfoToJSONStr(device.getContactInfo()));
			requestIndication.setTargetID("");
			
			restClientService.sendRequest(requestIndication);
		}
	}
}