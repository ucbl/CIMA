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
package fr.liris.cima.gscl.mgmtdevice;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import fr.liris.cima.gscl.device.service.ManagedDeviceService;
import fr.liris.cima.gscl.device.service.capability.CapabilityManager;
import fr.liris.cima.gscl.portforwarding.PortForwardManagerThread;
import fr.liris.cima.gscl.portforwarding.PortForwardingInterface;
import org.eclipse.om2m.core.service.SclService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import fr.liris.cima.gscl.device.service.ConfigManager;
import fr.liris.cima.gscl.device.service.ManagedDeviceService;
import fr.liris.cima.gscl.device.service.capability.CapabilityManager;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.service.log.*;
import org.osgi.framework.FrameworkUtil;

import javax.swing.plaf.synth.SynthSeparatorUI;
/**
 *  Manages the starting and stopping of the bundle.
 *  @author <ul>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>
 *         </ul>
 */
public class Activator implements BundleActivator {
	/** Logger */
private static Log logger = LogFactory.getLog(Activator.class);
/** Logger OSGI*/
private static ServiceTracker logServiceTracker;
private static LogService logservice;
/** SCL service tracker */
	private ServiceTracker<Object, Object> sclServiceTracker;

	private ServiceRegistration serviceRegistration;

	@Override
	public void start(BundleContext bundleContext) throws Exception {

		logServiceTracker = new ServiceTracker(bundleContext, org.osgi.service.log.LogService.class.getName(), null);
		logServiceTracker.open();
		logservice = (LogService) logServiceTracker.getService();

		sclServiceTracker = new ServiceTracker<Object, Object>(bundleContext, SclService.class.getName(), null) {
			public void removedService(ServiceReference<Object> reference, Object service) {
				logger.info("SclService removed");
								logservice.log(LogService.LOG_ERROR, "SclService removed");
			}

			public Object addingService(ServiceReference<Object> reference) {
				logger.info("SclService discovered in mgmtdevice");
								logservice.log(LogService.LOG_ERROR, "SclService discovered in mgmtdevice");
				SclService sclService = (SclService) this.context.getService(reference);


				ServiceReference sf = context.getServiceReference(PortForwardingInterface.class.getName());
				PortForwardingInterface pf = (PortForwardingInterface) context.getService(sf);


				serviceRegistration = this.context.registerService(ManagedDeviceService.class.getName(), new DeviceManagerImpl(sclService, pf) , null);
				logger.info("ManagedDeviceService registered successfully");
				logservice.log(LogService.LOG_ERROR, "ManagedDeviceService registered successfully");

				serviceRegistration = this.context.registerService(CapabilityManager.class.getName(), new CapabilityManagerImpl(sclService) , null);
				logger.info("CapabilityManager registered successfully");
								logservice.log(LogService.LOG_ERROR, "CapabilityManager registered successfully");







				return sclService;
			}
		};
		sclServiceTracker.open();

	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		logger.error("Unregistered ManagedDeviceService");
				logservice.log(LogService.LOG_ERROR, "Unregistered ManagedDeviceService");
		serviceRegistration.unregister();
	}
}
