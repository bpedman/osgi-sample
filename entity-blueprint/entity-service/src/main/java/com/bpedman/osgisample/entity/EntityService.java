package com.bpedman.osgisample.entity;

import java.util.Collection;

/**
 * Entity: bpedersen
 * Date: 8/5/13
 */
public interface EntityService {
    public Entity getUser(String id);
    public Entity addUser(Entity user);
    public Entity updateUser(String id, Entity user);
    public void deleteUser(String id);
    public Collection<Entity> getUsers();
}
