package fr.liris.cima.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;



/**
 *  Manages the starting and stopping of the bundle.
 *  @author hady
 */
public class Activator implements BundleActivator {
	/** Logger */
	private static Log logger = LogFactory.getLog(Activator.class);

	/** SCL service tracker */
	private ServiceTracker<Object, Object> sclServiceTracker;

	/** Logger OSGI*/
	private static ServiceTracker logServiceTracker;
	private static LogService logservice;

	private ServiceRegistration serviceRegistration;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
			logger.info("DAO START");
			MongoClient mongoClient = new MongoClient();
			MongoDatabase db = mongoClient.getDatabase("test");

		db.getCollection("restaurants").insertOne(new Document());
			logger.info("apresss");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
			logger.info("DAO STOP");
	}
}
