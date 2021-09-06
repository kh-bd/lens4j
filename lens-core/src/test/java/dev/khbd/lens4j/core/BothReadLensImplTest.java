package dev.khbd.lens4j.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class BothReadLensImplTest {

    private static final ReadLens<Person, String> LENS =
            Lenses.both(Lenses.readLens(Person::getName),
                    Lenses.readLens(Person::getAge),
                    (name, age) -> String.format("%s is %d years old", name, age));

    @Test
    public void get_objectIsNull_returnNull() {
        String result = LENS.get(null);

        assertThat(result).isNull();
    }

    @Test
    public void get_firstLensReturnNull_returnNull() {
        String result = LENS.get(new Person(null, 10));

        assertThat(result).isNull();
    }

    @Test
    public void get_secondLensReturnNull_returnNull() {
        String result = LENS.get(new Person("Alex", null));

        assertThat(result).isNull();
    }

    @Test
    public void get_bothLensesReturnNotNullValue_returnCombinerResult() {
        String result = LENS.get(new Person("Alex", 10));

        assertThat(result).isEqualTo("Alex is 10 years old");
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
    }
}
