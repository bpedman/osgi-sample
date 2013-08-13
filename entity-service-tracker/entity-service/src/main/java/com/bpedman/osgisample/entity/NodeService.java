package com.bpedman.osgisample.entity;

import java.util.Collection;

/**
 * User: bpedersen
 * Date: 8/5/13
 */
public interface NodeService {
    public Node getNode(String id);
    public Node addNode(Node node);
    public Node updateNode(String id, Node node);
    public void deleteNode(String id);
    public Collection<Node> getNodes();
}
