package com.bpedman.osgisample.entity.rest;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;
import aQute.bnd.annotation.component.Reference;
import com.bpedman.osgisample.entity.Node;
import com.bpedman.osgisample.entity.NodeService;
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

/**
 * User: bpedersen
 * Date: 8/5/13
 */
@WebService
@Path("/")
@Component(
        name = "NodeServiceRest",
        immediate = true
)
public class NodeServiceRest {

    private NodeService nodeService;
    private Server server = null;

    @Reference
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    public void unsetNodeService(NodeService nodeService) {
        this.nodeService = null;
    }

    @Activate
    public void activate() {
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        sf.setResourceClasses(getClass());
        sf.setResourceProvider(getClass(), new SingletonResourceProvider(this));
        sf.setAddress("/node");
        sf.setProvider(new JacksonJsonProvider());
        server = sf.create();
    }

    @Deactivate
    public void deactivate() {
        if (server != null) {
            server.stop();
            server.destroy();
            server = null;
        }
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
