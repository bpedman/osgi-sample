package com.bpedman.osgisample.entity.impl;

import com.bpedman.osgisample.entity.EntityService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * User: bpedersen
 * Date: 8/12/13
 */
public class Activator implements BundleActivator {

    ServiceRegistration registration = null;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        // Provide a service implementation to the OSGi container
        if (registration == null) {
            registration = bundleContext.registerService(
                    EntityService.class.getName(),
                    new EntityServiceImpl(),
                    null);
        }
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        if (registration != null) {
            registration.unregister();
            registration = null;
        }
    }
}
