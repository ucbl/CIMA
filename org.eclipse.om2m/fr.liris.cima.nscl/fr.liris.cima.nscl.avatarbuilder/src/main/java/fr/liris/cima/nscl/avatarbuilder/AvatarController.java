package fr.liris.cima.nscl.avatarbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.ipu.service.IpuService;

import fr.liris.cima.nscl.avatarbuilder.AvatarFactory;
import fr.liris.cima.nscl.avatarbuilder.constants.Constants;

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.*;

/**
 * An avatar controller to handle request
 */
public class AvatarController implements IpuService{

	/** Logger */
	private static Logger LOGGER = Logger.getLogger(AvatarController.class.getName());

	/** Returns the implemented Application Point of Contact id */
	@Override
	public String getAPOCPath() {
		return Constants.APOCPATH;
	}

	@Override
	public ResponseConfirm doExecute(RequestIndication requestIndication) {
		/**
		 * http://localhost:8181/om2m/gscl/applications/CIMA/devices/DEVICE_0/capabilities/goOn
		 */
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method not Implemented"));

	}

	@Override
	public ResponseConfirm doRetrieve(RequestIndication requestIndication) {

		/**
		 * http://localhost:8181/om2m/gscl/applications/CIMA/devices/DEVICE_0/
		 * Ici, il faut parser le device et recuperer la liste de toutes ses capacites.
		 */
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method not Implemented"));
	}

	@Override
	public ResponseConfirm doUpdate(RequestIndication requestIndication) {
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method not Implemented"));
	}

	@Override
	public ResponseConfirm doDelete(RequestIndication requestIndication) {
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method not Implemented"));
	}

	@Override
	public ResponseConfirm doCreate(RequestIndication requestIndication) {
		/**
		 * http://localhost:8181/om2m/gscl/applications/CIMA/devices/
		 * Basic YWRtaW4vYWRtaW4=
		 */
		String representation = requestIndication.getRepresentation();
		if(representation != null) {
			try {
				/**
				Device device = ObixParser.parseObixToDevice(representation);
				DeviceManager.addDevice(device);
				LOGGER.error("*********CREATE in DEVICE CONTROLLER ****");
				DeviceManager.createAvatar(device.getId(), device.getProtocol(), device.getUri()); **/
				return new ResponseConfirm(StatusCode.STATUS_ACCEPTED, representation);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST, " Impossible to create device with this request"));
	}
}
