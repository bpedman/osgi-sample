package com.bpedman.osgisample.entity.impl;

import com.bpedman.osgisample.entity.Entity;
import com.bpedman.osgisample.entity.EntityService;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity: bpedersen
 * Date: 8/5/13
 */
public class EntityServiceImpl implements EntityService {

    private Map<String, Entity> users;

    public EntityServiceImpl() {
        users = new HashMap<>();
    }

    @Override
    public Entity getUser(String id) {
        return users.get(id);
    }

    @Override
    public Entity addUser(Entity user) {
        // TODO: generate URN ID
        user.setLastModified(new Date());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Entity updateUser(String id, Entity user) {
        Entity current = users.get(id);
        if (current == null) {
            throw new IllegalArgumentException("Node with ID " + id + " does not exist. Cannot update.");
        }
        try {
            BeanUtils.copyProperties(current, user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        current.setLastModified(new Date());
        return current;
    }

    @Override
    public void deleteUser(String id) {
        users.remove(id);
    }

    @Override
    public Collection<Entity> getUsers() {
        return users.values();
    }
}
