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
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.service.log.*;
import org.osgi.framework.FrameworkUtil;

import fr.liris.cima.nscl.profils.profilsExport.*;
import fr.liris.cima.nscl.users.UsersExport.*;

public class AdministrationServer implements IpuService{
	private static Log LOGGER = LogFactory.getLog(AdministrationServer.class);

		/** Logger OSGI*/
		private static ServiceTracker logServiceTracker;
		private static LogService logservice;


	/** rest client service*/
	public static RestClientService restClientService;

	public static ProtocolResolver protocolResolver;

	public static final String GSCL_DEVICES_CONTACT = "om2m/gscl/applications/CIMA/devices";

	ProfileDeviceAssociatingManagerInterface ProfileDeviceAssociatingManagerInterface;
	ProfilManagerInterface profilManagerInterface;

	public AdministrationServer(){

		//initialize ProfileDeviceAssociatingManagerInterface service
		ServiceTracker st = new ServiceTracker(FrameworkUtil.getBundle(AdministrationServer.class).getBundleContext(), ProfileDeviceAssociatingManagerInterface.class.getName(), null);
		st.open();
		ProfileDeviceAssociatingManagerInterface = (ProfileDeviceAssociatingManagerInterface) st.getService();

		//Initialize ProfileManagerInterafce Service
		st = new ServiceTracker(FrameworkUtil.getBundle(AdministrationServer.class).getBundleContext(), ProfilManagerInterface.class.getName(), null);
		st.open();
		 profilManagerInterface = (ProfilManagerInterface) st.getService();

	}

	@Override
	// POST without body
	public ResponseConfirm doExecute(RequestIndication requestIndication) {
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,"POST must have a body"));
	}
	@Override
	// GET
	public ResponseConfirm doRetrieve(RequestIndication requestIndication) {
		logServiceTracker = new ServiceTracker(FrameworkUtil.getBundle(AdministrationServer.class).getBundleContext(), org.osgi.service.log.LogService.class.getName(), null);
				logServiceTracker.open();
				logservice = (LogService) logServiceTracker.getService();

				LOGGER.info("Base : " + requestIndication.getBase());
				logservice.log(LogService.LOG_ERROR, "Base : " + requestIndication.getBase());

				LOGGER.info("Method : " + requestIndication.getMethod());
				logservice.log(LogService.LOG_ERROR, "Method : " + requestIndication.getMethod());

				LOGGER.info("Protocol : " + requestIndication.getProtocol());
				logservice.log(LogService.LOG_ERROR, "Protocol : " + requestIndication.getProtocol());

				LOGGER.info("Representation : " + requestIndication.getRepresentation());
				logservice.log(LogService.LOG_ERROR, "Representation : " + requestIndication.getRepresentation());

				LOGGER.info("RequestingEntity : " + requestIndication.getRequestingEntity());
				logservice.log(LogService.LOG_ERROR, "RequestingEntity : " + requestIndication.getRequestingEntity());

				LOGGER.info("TargetID : " + requestIndication.getTargetID());
				logservice.log(LogService.LOG_ERROR, "TargetID : " + requestIndication.getTargetID());

				LOGGER.info("Url : " + requestIndication.getUrl());
				logservice.log(LogService.LOG_ERROR, "Url : " + requestIndication.getUrl());

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
			case "profile" ://nscl/applications/CIMA/administration/profile
				return new ResponseConfirm(StatusCode.STATUS_OK, profilManagerInterface.getAllProfilsToJson() );
			case "ProfileDeviceAssociating" :
				return new ResponseConfirm(StatusCode.STATUS_OK, ProfileDeviceAssociatingManagerInterface.getAllProfileDeviceAssociatingToJson() );
			}

		} else if(tID.length == 6){
			if("device".equals(tID[4])) {
				// nscl/applications/CIMA/administration/device/<device id>/
				requestIndication.setTargetID(GSCL_DEVICES_CONTACT + "/all/" + tID[5]);
				resp = restClientService.sendRequest(requestIndication);
				resp.setRepresentation(Parser.parseObixToJSONStringDevice(resp.getRepresentation()));
				return resp;
//			return new ResponseConfirm(StatusCode.STATUS_OK, "[{\"id\" : \"0123456789\",\"name\" : \"monObjet\",\"uri\" : \"http://192.168.0.2\",\"dateConnection\" : \"10/10/14\",\"modeConnection\" : \"http\"}]");
			}
			else if("ProfileDeviceAssociating".equals(tID[4])){//nscl/applications/CIMA/administration/ProfileDeviceAssociating/<id device>
				return new ResponseConfirm(StatusCode.STATUS_OK, ProfileDeviceAssociatingManagerInterface.getProfileDeviceAssociatingToJson(tID[5]));
			}
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

		String payload = requestIndication.getRepresentation();
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
		else if(tID.length == 5)
		{
			if("profile".equals(tID[4])) {
				// nscl/applications/CIMA/administration/profile
				boolean b = profilManagerInterface.deleteProfilFromSimpleJson(payload);
				if (b)
					return new ResponseConfirm(StatusCode.STATUS_OK, "{\"message\" : \"Profile deleted succesfully.\"}");
				else
					return new ResponseConfirm(StatusCode.STATUS_OK, "{\"error\" : \"Error during profile deleting.\"}");
			}
			else if ("ProfileDeviceAssociating".equals(tID[4])){
				boolean b = ProfileDeviceAssociatingManagerInterface.deleteProfileDeviceAssociatingFromSimpleJson(payload);
				if (b)
					return new ResponseConfirm(StatusCode.STATUS_OK, "{\"error\" : 0}");
				else
					return new ResponseConfirm(StatusCode.STATUS_OK, "{\"error\" : 1}");
			}

		}
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getMethod()+" ressource not found"));
	}

	@Override
	// POST with body
	public ResponseConfirm doCreate(RequestIndication requestIndication) {


		String [] tID = requestIndication.getTargetID().split("/");

		ResponseConfirm resp = null;
		requestIndication.setBase("127.0.0.1:8181/");
		requestIndication.setBase("127.0.0.1:8181/");
		LOGGER.info("++++++++++++++++++++");
		//logservice.log(LogService.LOG_ERROR, "++++++++++++++++++++");

		LOGGER.info("tID.lengh = " + tID.length);
		//logservice.log(LogService.LOG_ERROR, "tID.lengh = " + tID.length);





		if(tID.length == 5){
			if("login".equals(tID[4])) { // nscl/applications/CIMA/administration/login

				ServiceTracker st = new ServiceTracker(FrameworkUtil.getBundle(AdministrationServer.class).getBundleContext(), UserManagerInterface.class.getName(), null);
				st.open();
				UserManagerInterface um = (UserManagerInterface) st.getService();

				boolean res = um.checkLoginFromJson(requestIndication.getRepresentation());

				if(res)
					return new ResponseConfirm(StatusCode.STATUS_OK, "{ \"error\" : 0}");
				else
					return new ResponseConfirm(StatusCode.STATUS_OK, "{ \"error\" : 1}");
			}
			else if("profile".equals(tID[4]))
			{//nscl/applications/CIMA/administration/profile

				String res = profilManagerInterface.saveNewProfilFromJson(requestIndication.getRepresentation());

				//return new ResponseConfirm(StatusCode.STATUS_OK, res );
				return new ResponseConfirm(StatusCode.STATUS_OK, "{\"message\" : \"Profile is created.\"}" );
			}
			else if("ProfileDeviceAssociating".equals(tID[4]))
			{//nscl/applications/CIMA/administration/ProfileDeviceAssociating

				boolean res = ProfileDeviceAssociatingManagerInterface.addProfileDeviceAssociatingFromJson(requestIndication.getRepresentation());

				if(res)
					return new ResponseConfirm(StatusCode.STATUS_OK, "{\"error\" : 0}" );
				else
					return new ResponseConfirm(StatusCode.STATUS_OK, "{\"error\" : 1}" );
			}
		}


		if(tID.length == 6){ //nscl/applications/CIMA/administration/profile/update

			profilManagerInterface.updateProfilFromJson(requestIndication.getRepresentation());

			return new ResponseConfirm(StatusCode.STATUS_OK, "{\"message\" : \"It's probably done.\"}" );


		}

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

}
