package com.bpedman.osgisample.entity.rest;

import com.bpedman.osgisample.entity.Entity;
import com.bpedman.osgisample.entity.EntityService;

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
public class EntityServiceRest {

    private static EntityService entityService;

    public void setEntityService(EntityService entityService) {
        EntityServiceRest.entityService = entityService;
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
