package fr.liris.cima.gscl.comm.http;

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
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.service.log.*;
import org.osgi.framework.FrameworkUtil;

/**
 * Implements RestClientService class, to provide to CIMA, methods to send REST request using HTTP protocol.
 * This class helps converting a standard HTTP status code into a protocol-independent object and also converts a protocol independent parameters into a standard HTTP parameters.
 */
public class CIMARestHttpClient implements RestClientService{

	/** Logger */
	private static Log LOGGER = LogFactory.getLog(CIMARestHttpClient.class);
	/** Logger OSGI*/
	private static ServiceTracker logServiceTracker;
	private static LogService logservice;
	/** implemented specific protocol name */
	private static String protocol ="http";

	@Override
	public ResponseConfirm sendRequest(RequestIndication requestIndication) {

		logServiceTracker = new ServiceTracker(FrameworkUtil.getBundle(CIMARestHttpClient.class).getBundleContext(), org.osgi.service.log.LogService.class.getName(), null);
			logServiceTracker.open();
			logservice = (LogService) logServiceTracker.getService();

			LOGGER.info("Http Client > "+requestIndication);
			logservice.log(LogService.LOG_ERROR, "Http Client > " + requestIndication);

		HttpClient httpclient = new HttpClient();

		ResponseConfirm responseConfirm = new ResponseConfirm();
		HttpMethod httpMethod=null;
		String url = requestIndication.getUrl();
		if(!url.startsWith(protocol+"://")){
			url=protocol+"://"+url;
		}

		LOGGER.info("URL : " + url);
		logservice.log(LogService.LOG_ERROR, "URL : " + url);

		try {
			LOGGER.info("Method  : '"+requestIndication.getMethod() + "'");
			logservice.log(LogService.LOG_ERROR, "Method  : '"+requestIndication.getMethod() + "'");

			switch (requestIndication.getMethod()){

			case "GET" :
			case "RETRIEVE" :
				httpMethod =  new GetMethod(url);
				LOGGER.info("RETRIEVE--- + "+httpMethod);
				logservice.log(LogService.LOG_ERROR, "RETRIEVE--- + "+httpMethod);

				break;
			case "CREATE":
				httpMethod = new PostMethod(url);

				((PostMethod)httpMethod).setRequestEntity(new StringRequestEntity(requestIndication.getRepresentation(),"application/xml", "UTF8"));
				break;
			case "PUT":

				LOGGER.info("PUT");
				logservice.log(LogService.LOG_ERROR, "PUT");

			case "UPDATE":
				LOGGER.info("UPDATE");
				logservice.log(LogService.LOG_ERROR, "UPDATE");

				httpMethod = new PutMethod(url);
				((PutMethod)httpMethod).setRequestEntity(new StringRequestEntity(requestIndication.getRepresentation(),"application/xml", "UTF8"));
				break;
			case "DELETE":
				httpMethod =new DeleteMethod(url);
				break;
			case "EXECUTE":
				httpMethod = new PostMethod(url);
				break;
			default: return new ResponseConfirm();
			}


			HttpMethodRetryHandler myretryhandler = new HttpMethodRetryHandler() {
				public boolean retryMethod(
						final HttpMethod method,
						final IOException exception,
						int executionCount) {
					if (executionCount >= 0) {
						// Do not retry if over max retry count
						return false;
					}
					if (exception instanceof NoHttpResponseException) {
						// Retry if the server dropped connection on us
						return true;
					}
					if (!method.isRequestSent()) {
						// Retry if the request has not been sent fully or
						// if it's OK to retry methods that have been sent
						//return true;
						return false;
					}
					// otherwise do not retry
					return false;
				}
			};

			httpMethod.getParams().
			setParameter(HttpMethodParams.RETRY_HANDLER, myretryhandler);


			if(requestIndication.getRequestingEntity() != null)	httpMethod.addRequestHeader("Authorization", "Basic "+new String(Base64.encodeBase64(requestIndication.getRequestingEntity().getBytes())));
			httpMethod.setQueryString(getQueryFromParams(requestIndication.getParameters()));

			int statusCode = httpclient.executeMethod(httpMethod);
			responseConfirm.setStatusCode(getRestStatusCode(statusCode));

			if(statusCode!=204){
				if(httpMethod.getResponseBody()!=null){
					responseConfirm.setRepresentation(new String(httpMethod.getResponseBody()));
				}
			}
			if(statusCode==201){
				if(httpMethod.getResponseHeader("Location").getValue()!=null){
					responseConfirm.setResourceURI(httpMethod.getResponseHeader("Location").getValue());
				}
			}
			LOGGER.info("Http Client > "+responseConfirm);
			logservice.log(LogService.LOG_ERROR, "Http Client > "+responseConfirm);

		}catch(IOException e){
			//LOGGER.error(url+ " Not Found"+responseConfirm,e);
		} finally {
			LOGGER.error(" **********HTTP METHOD*******" + httpMethod);
			logservice.log(LogService.LOG_ERROR, " **********HTTP METHOD*******" + httpMethod);

			//httpMethod.releaseConnection();
		}

		return responseConfirm;
	}


	@Override
	public String getProtocol() {
		return protocol;
	}


	/**
	 * Converts a standard HTTP status code into a protocol-independent {@link StatusCode} object.
	 * @param statusCode standard HTTP status code.
	 * @return protocol independent status.
	 */
	public static StatusCode getRestStatusCode(int statusCode){
		switch(statusCode){
		case 200: return StatusCode.STATUS_OK;
		case 204: return StatusCode.STATUS_OK;
		case 202: return StatusCode.STATUS_ACCEPTED;
		case 201: return StatusCode.STATUS_CREATED;
		case 400: return StatusCode.STATUS_BAD_REQUEST;
		case 401: return StatusCode.STATUS_PERMISSION_DENIED;
		case 402: return StatusCode.STATUS_AUTHORIZATION_NOT_ADDED;
		case 403: return StatusCode.STATUS_FORBIDDEN;
		case 404: return StatusCode.STATUS_NOT_FOUND;
		case 405: return StatusCode.STATUS_METHOD_NOT_ALLOWED;
		case 406: return StatusCode.STATUS_NOT_ACCEPTABLE;
		case 408: return StatusCode.STATUS_REQUEST_TIMEOUT;
		case 409: return StatusCode.STATUS_CONFLICT;
		case 415: return StatusCode.STATUS_UNSUPPORTED_MEDIA_TYPE;
		case 500: return StatusCode.STATUS_INTERNAL_SERVER_ERROR;
		case 501: return StatusCode.STATUS_NOT_IMPLEMENTED;
		case 502: return StatusCode.STATUS_BAD_GATEWAY;
		case 503: return StatusCode.STATUS_SERVICE_UNAVAILABLE;
		case 504: return StatusCode.STATUS_GATEWAY_TIMEOUT;
		case 410: return StatusCode.STATUS_EXPIRED;
		default : return StatusCode.STATUS_INTERNAL_SERVER_ERROR;
		}
	}

	/**
	 * Converts a protocol independent parameters into a standard HTTP parameters.
	 * @param params protocol independent parameters map.
	 * @return standard HTTP query string.
	 */
	public static String getQueryFromParams(Map<String, List<String>> params){
		String query;
		List<String> values = new ArrayList<String>();
		String name;
		if (params != null) {
			query="?";
			Iterator<String> it = params.keySet().iterator();
			while(it.hasNext()){
				name = it.next().toString();
				values = params.get(name);
				for(int i=0;i<values.size();i++){
					query = query+name+"="+values.get(i)+"&";
				}
			}
			return query;
		}
		return null;
	}
}
