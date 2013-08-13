package com.bpedman.osgisample.entity.impl;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;
import com.bpedman.osgisample.entity.Entity;
import com.bpedman.osgisample.entity.EntityService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity: bpedersen
 * Date: 8/5/13
 */
@Component(
        name = "EntityServiceImpl",
        properties = {"service.exported.interfaces=*"}
)
public class EntityServiceImpl implements EntityService {

    private Map<String, Entity> users;

    public EntityServiceImpl() {
        users = new HashMap<>();
    }

    private static final Logger LOG = LoggerFactory.getLogger(EntityServiceImpl.class);

    public Entity getUser(String id) {
        return users.get(id);
    }

    @Activate
    public void activate() {
        LOG.info("Activating EntityServiceImpl");
    }

    @Deactivate
    public void deactivate() {
        LOG.info("Deactivating EntityServiceImpl");
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
