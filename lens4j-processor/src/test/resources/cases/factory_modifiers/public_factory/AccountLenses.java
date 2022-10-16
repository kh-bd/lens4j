package cases.factory_modifiers.public_factory;

import common.Currency;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class AccountLenses {

    public static final ReadLens<Account, String> LENS = Lenses.readLens(Account::getCurrency)
            .andThen(Lenses.readLens(Currency::getCode));

    private AccountLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}