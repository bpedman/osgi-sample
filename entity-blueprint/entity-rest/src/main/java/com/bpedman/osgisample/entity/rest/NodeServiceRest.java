package com.bpedman.osgisample.entity.rest;

import com.bpedman.osgisample.entity.Node;
import com.bpedman.osgisample.entity.NodeService;

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

/**
 * User: bpedersen
 * Date: 8/5/13
 */
@WebService
@Path("/")
public class NodeServiceRest {

    private static NodeService nodeService;

    public void setNodeService(NodeService nodeService) {
        NodeServiceRest.nodeService = nodeService;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNode(@PathParam("id") String id) {
        Node node = nodeService.getNode(id);
        if (node != null) {
            return Response.ok(node).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNode(Node node) {
        return Response.ok(nodeService.addNode(node)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateNode(@PathParam("id") String id, Node node) {
        return Response.ok(nodeService.updateNode(id, node)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteNode(@PathParam("id") String id) {
        nodeService.deleteNode(id);
        return Response.noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public java.util.Collection<Node> getNodes() {
        return nodeService.getNodes();
    }
}
