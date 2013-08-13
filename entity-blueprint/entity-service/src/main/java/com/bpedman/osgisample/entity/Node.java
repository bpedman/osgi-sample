package com.bpedman.osgisample.entity;

import lombok.Data;

import java.util.Map;

/**
 * Entity: bpedersen
 * Date: 8/5/13
 */
@Data
public class Node {
    private String id;
    private String name;
    private Map<String, String> configuration;
    private String internalAddress;
    private String externalAddress;

    // Relation
    private String entityId;
}
