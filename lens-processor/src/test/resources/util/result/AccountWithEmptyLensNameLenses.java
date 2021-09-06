package util;

import com.github.lens.core.Lenses;
import com.github.lens.core.ReadLens;
import com.github.lens.core.ReadWriteLens;
import java.lang.Integer;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("com.github.lens.processor.LensProcessor")
public final class AccountWithEmptyLensNameLenses {
    public static final ReadLens<AccountWithEmptyLensName, String> CURRENCY_FIELD_FOR_TEST_CODE = Lenses.readLens(AccountWithEmptyLensName::getCurrencyFieldForTest)
            .andThen(Lenses.readLens(Currency::getCode));

    public static final ReadWriteLens<AccountWithEmptyLensName, Integer> CURRENCY_FIELD_FOR_TEST_ID = Lenses.readLens(AccountWithEmptyLensName::getCurrencyFieldForTest)
            .andThen(Lenses.readWriteLens(Currency::getId, Currency::setId));

    private AccountWithEmptyLensNameLenses() {
    }
}
