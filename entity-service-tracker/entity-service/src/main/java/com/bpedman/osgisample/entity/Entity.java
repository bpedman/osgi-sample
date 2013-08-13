package com.bpedman.osgisample.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Entity: bpedersen
 * Date: 8/5/13
 */
@Data
public class Entity {

    private String id;
    private String name;
    private Date lastModified;

    // Relations
    private String presence;
    private List<String> groups;
    private List<String> nodes;
    private String parent;
}
