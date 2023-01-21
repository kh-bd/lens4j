package cases.lens_modifiers;

import common.Currency;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class AccountLenses {

    public static final ReadLens<Account, String> READ_DEFAULT = Lenses.readLens(Account::getCurrency)
            .andThen(Lenses.readLens(Currency::getCode));

    public static final ReadLens<Account, String> READ_PUBLIC = Lenses.readLens(Account::getCurrency)
            .andThen(Lenses.readLens(Currency::getCode));

    static final ReadLens<Account, String> READ_PACKAGE = Lenses.readLens(Account::getCurrency)
            .andThen(Lenses.readLens(Currency::getCode));

    public static final ReadWriteLens<Account, String> WRITE_DEFAULT = Lenses.readLens(Account::getCurrency)
            .andThen(Lenses.readWriteLens(Currency::getCode, Currency::setCode));

    public static final ReadWriteLens<Account, String> WRITE_PUBLIC = Lenses.readLens(Account::getCurrency)
            .andThen(Lenses.readWriteLens(Currency::getCode, Currency::setCode));

    static final ReadWriteLens<Account, String> WRITE_PACKAGE = Lenses.readLens(Account::getCurrency)
            .andThen(Lenses.readWriteLens(Currency::getCode, Currency::setCode));


    private AccountLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}