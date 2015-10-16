/*******************************************************************************
 * Copyright (c) 2013-2014 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Thierry Monteil (Project co-founder) - Management and initial specification,
 *      conception and documentation.
 *     Mahdi Ben Alaya (Project co-founder) - Management and initial specification,
 *      conception, implementation, test and documentation.
 *     Christophe Chassot - Management and initial specification.
 *     Khalil Drira - Management and initial specification.
 *     Yassine Banouar - Initial specification, conception, implementation, test
 *      and documentation.
 ******************************************************************************/
package fr.liris.cima.nscl.avatarbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.core.service.SclService;
import org.eclipse.om2m.ipu.service.IpuService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import fr.liris.cima.nscl.avatarbuilder.service.AvatarService;

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.*;

/**
 *  Manages the starting and stopping of the bundle.
 *  @author <ul>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>
 *         </ul>
 */
public class Activator implements BundleActivator {
	/** Logger */
	private static Logger logger = Logger.getLogger(Activator.class.getName());
	private  static  Handler fh ;
	/** SCL service tracker */
	private ServiceTracker<Object, Object> sclServiceTracker;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		try{
		fh = new FileHandler("log/nsclAvatarBuilder.log", false);
		logger.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());}
		catch(IOException ex){}


		logger.info("Register AvatarService ...");
		bundleContext.registerService(AvatarService.class.getName(), new AvatarFactory(), null);
		bundleContext.registerService(IpuService.class.getName(), new AvatarController(), null);

		logger.info("AvatarService is registered.");


		sclServiceTracker = new ServiceTracker<Object, Object>(bundleContext, SclService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("SclService removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("SclService discovered");
				SclService sclService = (SclService) this.context.getService(reference);
				final AvatarManager avatarManager = new AvatarManager(sclService);
				return sclService;
			}
		};
		sclServiceTracker.open();

	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		/** logger.error("Stop IPU Phidgets monitor");
        try {
            SampleMonitor.stop();

        } catch (Exception e) {
            logger.error("Stop Phidgets error", e);
        }**/
	}

}
