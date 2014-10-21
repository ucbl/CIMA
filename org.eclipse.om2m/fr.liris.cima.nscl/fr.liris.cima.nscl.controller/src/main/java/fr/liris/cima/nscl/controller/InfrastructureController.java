package fr.liris.cima.nscl.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.ipu.service.IpuService;

import fr.liris.cima.nscl.controller.constants.Constants;
import fr.liris.cima.nscl.controller.encoder.JsonEncoder;
import fr.liris.cima.nscl.controller.parser.Parser;
import fr.liris.cima.nscl.controller.subscriber.ClientSubscriber;

public class InfrastructureController implements IpuService{

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(InfrastructureController.class);

	/** Returns the implemented Application Point of Contact id */
	@Override
	public String getAPOCPath() {
		return Constants.APOCPATH;
	}

	@Override
	public ResponseConfirm doExecute(RequestIndication requestIndication) {
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method not Implemented"));
	}

	@Override
	public ResponseConfirm doRetrieve(RequestIndication requestIndication) {

		/**
		 * http://localhost:8181/om2m/gscl/applications/CIMA/hadydelabe/DEVICE_0/capabilities
		 * Ici, il faut parser le device et recuperer la liste de toutes ses capacites.
		 */
		
		String[] infos = requestIndication.getTargetID().split("/");
		int length = infos.length;
		ResponseConfirm responseConfirm = null;
		String representation = JsonEncoder.devicesToJSONStr(DeviceManager.devices);
		if(infos[length - 1].equals(Constants.APOCPATH_DEVICES)){
			
			LOGGER.info("********************REPRESENTATION*******"+requestIndication.getUrl());
			requestIndication.setRepresentation(representation);
			//responseConfirm = new CIMARestHttpClient().sendRequest(requestIndication);
			responseConfirm = new ResponseConfirm(StatusCode.STATUS_OK, representation);
			return responseConfirm;
		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST, ""));
	}

	@Override
	public ResponseConfirm doUpdate(RequestIndication requestIndication) {
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method not Implemented"));
	}

	@Override
	public ResponseConfirm doDelete(RequestIndication requestIndication) {
		String[] infos = requestIndication.getTargetID().split("/");
		int length = infos.length;
		String deviceId = infos[length - 1];
		ResponseConfirm responseConfirm = null;

		if(infos[length - 2].equals(Constants.APOCPATH_DEVICES)){
			LOGGER.info("************DELETE DEVICE IN INFRASTRUCTURE CONTROLLER*****************");
			Device device = DeviceManager.removeDevice(deviceId);
			// Send notification to all subscribers
			notifyAllSubscribers(requestIndication, device);
			
			responseConfirm = new ResponseConfirm(StatusCode.STATUS_DELETED, "suppression avec succes");
		}
		
		return responseConfirm;
	}

	@Override
	public ResponseConfirm doCreate(RequestIndication requestIndication) {

		String[] infos = requestIndication.getTargetID().split("/");
		int length = infos.length;

		String representation = requestIndication.getRepresentation();

		if(representation != null) {
			try {
				
				if(infos[length - 1].equals(Constants.APOCPATH_SUBSCRIBERS)){
					ClientSubscriber subscriber = Parser.parseXmlToClientSubscriber(representation);
					boolean success = DeviceManager.addSubscriber(subscriber);
					if(success) {
						LOGGER.info("A client Subscriber added succesfully ");
						// send all information to this subscriber
						List<Device> devices = DeviceManager.devices;
						requestIndication.setBase(subscriber.getUrl());
						requestIndication.setTargetID("");
						requestIndication.setRepresentation(JsonEncoder.encodeContactInfo(devices));
						LOGGER.info("Sending all devices contact info to new subscriber");
						new CIMARestHttpClient().sendRequest(requestIndication);

						return new ResponseConfirm(StatusCode.STATUS_ACCEPTED, "");
					}
				}
				else if(infos[length - 1].equals(Constants.APOCPATH_DEVICES)){
					LOGGER.error("Creating a device in infrastrucure controller and sending contact information to all subscribers");
                  
					Device device = Parser.parseObixToDevice(representation);
					DeviceManager.addDevice(device);
				//	DeviceNsclManager.createAvatar(device.getId(), device.getProtocol(), device.getUrl());
					
					for(ClientSubscriber subscriber : DeviceManager.subscribers){
						requestIndication.setBase(subscriber.getUrl());
						requestIndication.setRepresentation(JsonEncoder.encodeContactInfo(device));
						new CIMARestHttpClient().sendRequest(requestIndication);
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
		for(ClientSubscriber subscriber : DeviceManager.subscribers){
			requestIndication.setBase(subscriber.getUrl());
		//	requestIndication.setRepresentation(JsonEncoder.encodeContactInfo(device));
			requestIndication.setRepresentation(JsonEncoder.contactInfoToJSONStr(device.getContactInfo()));
			requestIndication.setTargetID("");
			
			new CIMARestHttpClient().sendRequest(requestIndication);
		}
	}
}