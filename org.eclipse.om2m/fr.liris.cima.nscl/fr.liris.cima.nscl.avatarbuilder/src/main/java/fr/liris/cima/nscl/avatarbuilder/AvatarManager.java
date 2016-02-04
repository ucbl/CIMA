package fr.liris.cima.nscl.avatarbuilder;

import obix.Contract;
import obix.Obj;
import obix.Op;
import obix.Str;
import obix.Uri;
import obix.io.ObixEncoder;

import org.eclipse.om2m.commons.resource.Application;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.core.service.SclService;

import fr.liris.cima.nscl.avatarbuilder.constants.Constants;

/**
 * Manage all the avatar
 */
public class AvatarManager {

	private static final String DESC = "DESCRIPTION";
	private static final String CAPABS = "CAPABILITIES";

	static SclService SCL;

	public AvatarManager(SclService scl) {
		SCL = scl;
	}

	/**
	 * Create avatar resources
	 * @param appId the application ID
	 * @param aPoCPath the application point of contact
	 */
	public static void createAvatarResources(String appId, String aPoCPath) {
		// Create the Application resource
		ResponseConfirm response = SCL.doRequest(new RequestIndication(Constants.METHOD_CREATE,Constants.SCLID+"/applications",Constants.REQENTITY,new Application(appId,aPoCPath)));
		// Create Application sub-resources only if application not yet created
		if(response.getStatusCode().equals(StatusCode.STATUS_CREATED)) {
			// Create DESCRIPTOR container sub-resource
			SCL.doRequest(new RequestIndication(Constants.METHOD_CREATE,Constants.SCLID+"/applications/"+appId+"/containers",Constants.REQENTITY,new Container(DESC)));

			// Create CAPABILITIES container sub-resource
			SCL.doRequest(new RequestIndication(Constants.METHOD_CREATE,Constants.SCLID+"/applications/"+appId+"/containers",Constants.REQENTITY,new Container(CAPABS)));

			String content, targetID;
			// Create DESCRIPTION contentInstance on the DESCRIPTOR container resource
			content = toObix();
			//Device.getDescriptorRep(SCLID, appId);
			targetID= Constants.SCLID+"/applications/"+appId+"/containers/"+DESC+"/contentInstances";
			SCL.doRequest(new RequestIndication(Constants.METHOD_CREATE,targetID,Constants.REQENTITY,new ContentInstance(content.getBytes())));
		}
	}


	/**
	 * Encode avatar to obix
	 * @return a String corresponding to Obix syntax
	 */
	public static String toObix() {

		Obj obj = new Obj();

		// OP name
		Op op = new Op();
		op.setName("getCapabilities");
		//op.setHref(new Uri("gscl/applications/CIMA/devices/DEVICE_0/capability"));
		op.setIs(new Contract("retrieve"));
		op.setIn(new Contract("obix:Nil"));
		op.setOut(new Contract("obix:Nil"));
		obj.add(op);

		return ObixEncoder.toString(obj);
	}
}
