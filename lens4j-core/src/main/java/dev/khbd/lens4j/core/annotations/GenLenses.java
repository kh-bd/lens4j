package dev.khbd.lens4j.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to generate factory with specified lenses.
 *
 * <p>Lenses can be generated at compile time. To do this, mark class with
 * {@link GenLenses} annotation and specify {@link Lens} annotation for
 * each desired lens.
 *
 * <p>For example, if we have a code like this
 * <pre>{@code @GenLenses(lenses = @Lens(path = "account.accountNumber", lensName = "ACCOUNT_NUMBER_LENS"))
 * class Payment {
 *      Account account;
 *
 *      ... accessor methods
 * }
 * class Account {
 *      String accountNumber;
 *
 *      ... accessor methods
 * }
 * }</pre>
 * {@code PaymentLenses} factory will be generated
 * <pre>{@code
 * final class PaymentLenses {
 *     public static final ReadLens<Payment, String> ACCOUNT_NUMBER_LENS =
 *         Lenses.readLens(Payment::getAccount)
 *            .andThen(Lenses.readLens(Account::getAccountNumber));
 * }
 * }</pre>
 *
 * @author Sergei_Khadanovich
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Repeatable(GenLenses.GenLensesMulti.class)
public @interface GenLenses {

    /**
     * Class which will be the root of all lenses.
     *
     * <p>Default value is {@link Void}. It means that an annotated element
     * is going to be the root of all lenses.
     *
     * @return root class
     */
    Class<?> root() default void.class;

    /**
     * Generated factory name.
     *
     * @return factory name
     */
    String factoryName() default "";

    /**
     * Lenses which should be added to generated factory.
     *
     * @return lenses
     */
    Lens[] lenses() default {};

    /**
     * Generated factory access level.
     *
     * @return access level
     */
    AccessLevel accessLevel() default AccessLevel.INHERIT;

    /**
     * Factory access level.
     */
    enum AccessLevel {

        /**
         * Generated factory will be public class.
         */
        PUBLIC,

        /**
         * Generated factory will be package-private.
         */
        PACKAGE,

        /**
         * Generated factory visibility modified will be the same as annotated class.
         */
        INHERIT
    }

    /**
     * Repeatable GenLenses.
     */
    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.CLASS)
    @interface GenLensesMulti {

       GenLenses[] value();
    }
}
