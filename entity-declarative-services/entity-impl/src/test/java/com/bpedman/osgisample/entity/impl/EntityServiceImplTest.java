package com.bpedman.osgisample.entity.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import com.bpedman.osgisample.entity.Entity;
import com.bpedman.osgisample.entity.EntityService;
import com.google.inject.Inject;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * User: bpedersen
 * Date: 8/12/13
 */
@RunWith(JukitoRunner.class)
public class EntityServiceImplTest {

    @Inject private EntityService entityService;

    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {
            bind(EntityService.class).to(EntityServiceImpl.class);
        }
    }

    private Entity createEntity() {
        Entity entity = new Entity();
        entity.setId("test");
        entity.setName("test");
        return entityService.addUser(entity);
    }

    @Test
    public void testGetUser() throws Exception {
        Entity foo = entityService.getUser("foo");
        assertThat(foo).isNull();
        foo = createEntity();
        Entity ret = entityService.getUser(foo.getId());
        assertThat(foo.getId()).isEqualTo(ret.getId());
        assertThat(foo.getName()).isEqualTo("test");
        assertThat(foo.getId()).isNotEqualTo("test").startsWith("entities");
        assertThat(foo.getLastModified()).isNotNull().isBefore(new Date());
    }

    @Test
    public void testAddUser() throws Exception {

    }

    @Test
    public void testUpdateUser() throws Exception {

    }

    @Test
    public void testDeleteUser() throws Exception {

    }

    @Test
    public void testGetUsers() throws Exception {

    }
}
