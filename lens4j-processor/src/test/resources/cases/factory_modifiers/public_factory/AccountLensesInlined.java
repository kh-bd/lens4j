package cases.factory_modifiers.public_factory;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class AccountLenses {
    public static final ReadLens<Account, String> LENS = new ReadLens<Account, String>() {
        @Override
        public final String get(Account object) {
            if (object == null) {
                return null;
            }
            Currency currency = object.getCurrency();
            if (currency == null) {
                return null;
            }
            return currency.getCode();
        }
    };

    private AccountLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
