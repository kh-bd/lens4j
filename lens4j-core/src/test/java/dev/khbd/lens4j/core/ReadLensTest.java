package dev.khbd.lens4j.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class ReadLensTest {

    private static final ReadLens<Outer, String> AND_THEN_LEN =
            Lenses.readLens(Outer::getInner)
                    .andThen(Lenses.readLens(Inner::getValue));

    private static final ReadLens<Outer, String> AND_THEN_REF =
            Lenses.readLens(Outer::getInner)
                    .andThen(Inner::getValue);

    private static final ReadLens<Outer, String> AND_THEN_F_REF =
            Lenses.readLens(Outer::getInner)
                    .andThenF(Inner::getValue);

    private static final ReadLens<Outer, String> COMPOSE_LEN =
            Lenses.readLens(Inner::getValue)
                    .compose(Lenses.readLens(Outer::getInner));

    private static final ReadLens<Outer, String> COMPOSE_REF =
            Lenses.readLens(Inner::getValue)
                    .compose(Outer::getInner);

    private static final ReadLens<Outer, String> COMPOSE_F_REF =
            Lenses.readLens(Inner::getValue)
                    .composeF(Outer::getInner);

    // andThen

    @Test
    public void andThen_combinedWithLens_workAsIntended() {
        assertThat(AND_THEN_LEN.get(null)).isNull();
        assertThat(AND_THEN_LEN.get(new Outer(null))).isNull();
        assertThat(AND_THEN_LEN.get(new Outer(new Inner(null)))).isNull();
        assertThat(AND_THEN_LEN.get(new Outer(new Inner("value")))).isEqualTo("value");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void andThen_combinedWithMethodRef_throwNPE() {
        AND_THEN_REF.get(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void andThen_combinedWithMethodRefAndArgumentNotNull_throwNPE() {
        AND_THEN_REF.get(new Outer(null));
    }

    @Test
    public void andThen_combinedWithMethodRefAndValueIsNull_returnNull() {
        String value = AND_THEN_REF.get(new Outer(new Inner(null)));

        assertThat(value).isNull();
    }

    // andThenF

    @Test
    public void andThenF_combinedWithNotLens_workAsIntended() {
        assertThat(AND_THEN_F_REF.get(null)).isNull();
        assertThat(AND_THEN_F_REF.get(new Outer(null))).isNull();
        assertThat(AND_THEN_F_REF.get(new Outer(new Inner(null)))).isNull();
        assertThat(AND_THEN_F_REF.get(new Outer(new Inner("value")))).isEqualTo("value");
    }

    // compose

    @Test
    public void compose_combinedWithLens_workAsIntended() {
        assertThat(COMPOSE_LEN.get(null)).isNull();
        assertThat(COMPOSE_LEN.get(new Outer(null))).isNull();
        assertThat(COMPOSE_LEN.get(new Outer(new Inner(null)))).isNull();
        assertThat(COMPOSE_LEN.get(new Outer(new Inner("value")))).isEqualTo("value");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void compose_combinedWithMethodRef_throwNPE() {
        COMPOSE_REF.get(null);
    }

    @Test
    public void compose_combinedWithMethodRefAndArgumentNotNull_returnNull() {
        String value = COMPOSE_REF.get(new Outer(null));

        assertThat(value).isNull();
    }

    @Test
    public void compose_combinedWithMethodRefAndValueIsNull_returnNull() {
        String value = COMPOSE_REF.get(new Outer(new Inner(null)));

        assertThat(value).isNull();
    }

    // composeF

    @Test
    public void composeF_combinedWithNotLens_workAsIntended() {
        assertThat(COMPOSE_F_REF.get(null)).isNull();
        assertThat(COMPOSE_F_REF.get(new Outer(null))).isNull();
        assertThat(COMPOSE_F_REF.get(new Outer(new Inner(null)))).isNull();
        assertThat(COMPOSE_F_REF.get(new Outer(new Inner("value")))).isEqualTo("value");
    }

    static class Outer {
        final Inner inner;

        public Outer(Inner account) {
            this.inner = account;
        }

        Inner getInner() {
            return inner;
        }
    }

    static class Inner {
        final String value;

        public Inner(String value) {
            this.value = value;
        }

        String getValue() {
            return value;
        }
    }
}
