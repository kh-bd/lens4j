package com.github.lens.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import lombok.Data;
import org.testng.annotations.Test;

import java.util.function.Function;

/**
 * @author Sergei_Khadanovich
 */
public class ReadWriteLensImplTest {

    private static final ReadWriteLens<Entity, String> LENS =
            Lenses.readWriteLens(Entity::getProperty, Entity::setProperty);

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

    @Test
    public void modify_objectIsNull_doNothing() {
        Function<String, String> f = mock(Function.class);

        LENS.modify(null, f);

        verify(f, never()).apply(anyString());
    }

    @Test
    public void modify_propertyIsNull_doNothing() {
        Function<String, String> f = mock(Function.class);

        LENS.modify(new Entity(), f);

        verify(f, never()).apply(anyString());
    }

    @Test
    public void modify_propertyIsNotNull_setFunctionResult() {
        Entity entity = new Entity();
        entity.setProperty("value");

        LENS.modify(entity, String::toUpperCase);

        assertThat(entity.getProperty()).isEqualTo("VALUE");
    }

    @Data
    static class Entity {
        String property;
    }
}
