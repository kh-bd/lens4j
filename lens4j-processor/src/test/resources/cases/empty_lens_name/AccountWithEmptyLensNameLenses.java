package cases.empty_lens_name;

import common.Currency;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Integer;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class AccountWithEmptyLensNameLenses {
    public static final ReadLens<AccountWithEmptyLensName, String> CURRENCY_FIELD_FOR_TEST_CODE_READ_LENS = Lenses.readLens(AccountWithEmptyLensName::getCurrencyFieldForTest)
            .andThen(Lenses.readLens(Currency::getCode));

    public static final ReadWriteLens<AccountWithEmptyLensName, Integer> CURRENCY_FIELD_FOR_TEST_ID_READ_WRITE_LENS = Lenses.readLens(AccountWithEmptyLensName::getCurrencyFieldForTest)
            .andThen(Lenses.readWriteLens(Currency::getId, Currency::setId));

    private AccountWithEmptyLensNameLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
