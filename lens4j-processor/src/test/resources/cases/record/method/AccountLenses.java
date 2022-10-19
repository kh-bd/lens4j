package cases.record.method;

import common.Currency;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class AccountLenses {
    public static final ReadLens<Account, Currency> CUR_LENS = Lenses.readLens(Account::currency);

    public static final ReadWriteLens<Account, String> CUR_CODE_LENS = Lenses.readLens(Account::currency)
            .andThen(Lenses.readWriteLens(Currency::getCode, Currency::setCode));

    private AccountLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
