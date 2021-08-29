package com.github.lens.core;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.Data;
import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class AccessorsLensTest {

    private static final Lens<Entity, String> LENS =
            Lens.fromAccessors(Entity::getProperty, Entity::setProperty);

    @Test
    public void get_objectIsNull_returnNull() {
        assertThat(LENS.get(null)).isNull();
    }

    @Test
    public void get_objectPropertyIsNull_returnNull() {
        Entity entity = new Entity();

        assertThat(LENS.get(entity)).isNull();
    }

    @Test
    public void get_objectPropertyIsNotNull_returnIt() {
        Entity entity = new Entity();
        entity.setProperty("value");

        assertThat(LENS.get(entity)).isEqualTo("value");
    }

    @Test
    public void set_objectIsNull_doNotSet() {
        // this test check, that there is no NullPointerException
        LENS.set(null, "value");
    }

    @Test
    public void set_propertyIsNull_setNull() {
        Entity entity = new Entity();
        entity.setProperty("value");

        LENS.set(entity, null);

        assertThat(entity.getProperty()).isNull();
    }

    @Test
    public void set_propertyIsNotNull_setNewPropertyValue() {
        Entity entity = new Entity();
        entity.setProperty("value");

        LENS.set(entity, "new value");

        assertThat(entity.getProperty()).isEqualTo("new value");
    }

    @Data
    static class Entity {
        String property;
    }
}
