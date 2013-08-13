package com.bpedman.osgisample.entity.impl;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;
import com.bpedman.osgisample.entity.Node;
import com.bpedman.osgisample.entity.NodeService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: bpedersen
 * Date: 8/5/13
 */
@Component(
        name = "NodeServiceImpl",
        properties = {"service.exported.interfaces=*"}
)
public class NodeServiceImpl implements NodeService {

    Map<String, Node> nodes;

    private static final Logger LOG = LoggerFactory.getLogger(NodeServiceImpl.class);

    public NodeServiceImpl() {
        nodes = new HashMap<>();
    }

    @Activate
    public void activate() {
        LOG.info("Activating NodeServiceImpl");
    }

    @Deactivate
    public void deactivate() {
        LOG.info("Deactivating NodeServiceImpl");
    }

    @Override
    public Node getNode(String id) {
        return nodes.get(id);
    }

    @Override
    public Node addNode(Node node) {
        //TODO: Implement
        return null;
    }

    @Override
    public Node updateNode(String id, Node node) {
        Node current = nodes.get(id);
        if (current == null) {
            throw new IllegalArgumentException("Node with ID " + id + " does not exist. Cannot update.");
        }
        try {
            BeanUtils.copyProperties(current, node);
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return current;
    }

    @Override
    public void deleteNode(String id) {
        nodes.remove(id);
    }

    @Override
    public Collection<Node> getNodes() {
        return nodes.values();
    }
}
