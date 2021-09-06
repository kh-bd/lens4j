package dev.khbd.lens4j.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class BothReadWriteLensImplTest {

    private static final ReadWriteLens<Person, String> NAME_LENS =
            Lenses.readWriteLens(Person::getName, Person::setName);
    private static final ReadWriteLens<Person, Integer> AGE_LENS =
            Lenses.readWriteLens(Person::getAge, Person::setAge);

    private static final ReadWriteLens<Person, NameAndAge> LENS =
            Lenses.both(NAME_LENS, AGE_LENS, NameAndAge::new, NameAndAge::getName, NameAndAge::getAge);

    @Test
    public void get_objectIsNull_returnNull() {
        NameAndAge result = LENS.get(null);

        assertThat(result).isNull();
    }

    @Test
    public void get_firstLensReturnNull_returnNull() {
        NameAndAge result = LENS.get(new Person(null, 10));

        assertThat(result).isNull();
    }

    @Test
    public void get_secondLensReturnNull_returnNull() {
        NameAndAge result = LENS.get(new Person("Alex", null));

        assertThat(result).isNull();
    }

    @Test
    public void get_bothLensesReturnNotNullValue_returnCombinerResult() {
        NameAndAge result = LENS.get(new Person("Alex", 10));

        assertThat(result.getName()).isEqualTo("Alex");
        assertThat(result.getAge()).isEqualTo(10);
    }

    @Test
    public void set_propertyIsNull_doNothing() {
        Person person = new Person("Alex", 10);

        LENS.set(person, null);

        assertThat(person.getName()).isEqualTo("Alex");
        assertThat(person.getAge()).isEqualTo(10);
    }

    @Test
    public void set_propertyIsNotNull_deconstructItAndSetToOriginalObject() {
        Person person = new Person("Sergei", 20);

        LENS.set(person, new NameAndAge("Alex", 10));

        assertThat(person.getName()).isEqualTo("Alex");
        assertThat(person.getAge()).isEqualTo(10);
    }

    static class Person {
        String name;
        Integer age;

        Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        String getName() {
            return name;
        }

        Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class NameAndAge {
        String name;
        Integer age;

        NameAndAge(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        String getName() {
            return name;
        }

        Integer getAge() {
            return age;
        }
    }
}
