package cases.specific_factory_name;

import common.Currency;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Integer;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class SpecificFactoryName {
    public static final ReadLens<AccountWithSpecificFactoryName, String> ACCOUNT_CURRENCY_CODE_READ_LENS = Lenses.readLens(AccountWithSpecificFactoryName::getCurrency)
            .andThen(Lenses.readLens(Currency::getCode));

    public static final ReadWriteLens<AccountWithSpecificFactoryName, Integer> ACCOUNT_CURRENCY_CODE_READ_WRITE_LENS = Lenses.readLens(AccountWithSpecificFactoryName::getCurrency)
            .andThen(Lenses.readWriteLens(Currency::getId, Currency::setId));

    private SpecificFactoryName() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
