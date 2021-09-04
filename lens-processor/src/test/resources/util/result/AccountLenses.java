package util;

import com.github.lens.core.Lenses;
import com.github.lens.core.ReadLens;
import java.lang.Integer;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("com.github.lens.processor.LensProcessor")
public final class AccountLenses {
    public static final ReadLens<Account, String> ACCOUNT_CURRENCY_CODE_READ_LENS = Lenses.readLens(Account::getCurrency)
            .andThen(Lenses.readLens(Currency::getCode));

    public static final ReadLens<Account, Integer> ACCOUNT_CURRENCY_CODE_READ_WRITE_LENS = Lenses.readLens(Account::getCurrency)
            .andThen(Lenses.readWriteLens(Currency::getId, Currency::setId));

    private AccountLenses() {
    }
}