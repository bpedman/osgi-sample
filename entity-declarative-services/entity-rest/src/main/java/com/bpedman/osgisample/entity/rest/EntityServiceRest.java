package com.bpedman.osgisample.entity.rest;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;
import aQute.bnd.annotation.component.Reference;
import com.bpedman.osgisample.entity.Entity;
import com.bpedman.osgisample.entity.EntityService;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * User: bpedersen
 * Date: 8/5/13
 */
@WebService
@Path("/")
@Component(
        name = "EntityServiceRest",
        immediate = true
)
public class EntityServiceRest {

    private EntityService entityService;
    private Server server = null;

    @Reference
    public void setEntityService(EntityService entityService) {
        this.entityService = entityService;
    }

    public void unsetEntityService(EntityService entityService) {
        this.entityService = null;
    }

    @Activate
    public void activate() {
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        sf.setResourceClasses(getClass());
        sf.setResourceProvider(getClass(), new SingletonResourceProvider(this));
        sf.setAddress("/entity");
        sf.setProvider(new JacksonJsonProvider());
        server = sf.create();
    }

    @Deactivate
    public void deactivate() {
        if (server != null) {
            server.stop();
            server.destroy();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {
        Entity user = entityService.getUser(id);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(Entity user) {
        return Response.ok(entityService.addUser(user)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateUser(@PathParam("id") String id, Entity user) {
        Entity entity = entityService.updateUser(id, user);
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") String id) {
        entityService.deleteUser(id);
        return Response.noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Entity> getUsers() {
        return entityService.getUsers();
    }

}
