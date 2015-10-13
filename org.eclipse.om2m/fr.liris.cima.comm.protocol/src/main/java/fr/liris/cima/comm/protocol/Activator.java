package fr.liris.cima.comm.protocol;

import org.eclipse.om2m.comm.service.RestClientService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private ServiceRegistration serviceRegistration;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		// TODO Auto-generated method stub
		this.serviceRegistration = bundleContext.registerService(ProtocolResolver.class.getName(), new ProtocolResolver(), null);
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		this.serviceRegistration.unregister();
	}

}
