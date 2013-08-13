package com.bpedman.osgisample.entity.rest;

import com.bpedman.osgisample.entity.EntityService;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * User: bpedersen
 * Date: 8/12/13
 */
public class Activator implements BundleActivator, ServiceTrackerCustomizer {

    private BundleContext context;
    private ServiceTracker myServiceTracker;
    private Server server = null;

    @Override
    public void start(BundleContext context) throws Exception {
        /*
            This is the naive way of getting a service
            Do not activate a bundle like this...there is no guarantee that the
            service is available and may cause null pointer exceptions

            ServiceReference reference = context.getServiceReference(EntityService.class.getName());
            EntityService service = (EntityService) context.getService(reference);
            EntityServiceRest restApi = new EntityServiceRest();
            restApi.setEntityService(service);
         */

        this.context = context;

        myServiceTracker = new ServiceTracker(context, EntityService.class.getName(), this);
        myServiceTracker.open();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (server != null) {
            server.stop();
            server.destroy();
            server = null;
        }
        myServiceTracker.close();
        this.context = null;
    }

    @Override
    public Object addingService(ServiceReference serviceReference) {
        final Object trackedService = context.getService(serviceReference);

        if (trackedService instanceof EntityService) {
            final EntityServiceRest restApi = new EntityServiceRest();
            restApi.setEntityService((EntityService) trackedService);

            JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
            sf.setResourceClasses(restApi.getClass());
            sf.setResourceProvider(restApi.getClass(), new SingletonResourceProvider(restApi));
            sf.setAddress("/entity");
            sf.setProvider(new JacksonJsonProvider());
            server = sf.create();
        }

        return trackedService;
    }

    @Override
    public void modifiedService(ServiceReference serviceReference, Object service) {
        // the service properties have been changed
        // Basically just do a stop and start on the REST service
        removedService(serviceReference, service);
        addingService(serviceReference);
    }

    @Override
    public void removedService(ServiceReference serviceReference, Object service) {
        final Object trackedService = context.getService(serviceReference);

        if (trackedService instanceof EntityService) {
            if (server != null) {
                server.stop();
                server.destroy();
                server = null;
            }
        }
    }
}
