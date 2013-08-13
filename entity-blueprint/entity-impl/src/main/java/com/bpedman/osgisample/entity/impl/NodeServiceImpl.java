package com.bpedman.osgisample.entity.impl;

import com.bpedman.osgisample.entity.Node;
import com.bpedman.osgisample.entity.NodeService;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: bpedersen
 * Date: 8/5/13
 */
public class NodeServiceImpl implements NodeService {

    Map<String, Node> nodes;

    public NodeServiceImpl() {
        nodes = new HashMap<>();
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
