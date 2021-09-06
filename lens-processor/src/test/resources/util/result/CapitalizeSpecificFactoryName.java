package util;

import com.github.lens.core.Lenses;
import com.github.lens.core.ReadLens;
import com.github.lens.core.ReadWriteLens;
import java.lang.Integer;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("com.github.lens.processor.LensProcessor")
public final class SpecificFactoryName {
    public static final ReadLens<AccountWithDeCapitalizeSpecificFactoryName, String> ACCOUNT_CURRENCY_CODE_READ_LENS = Lenses.readLens(AccountWithDeCapitalizeSpecificFactoryName::getCurrency)
            .andThen(Lenses.readLens(Currency::getCode));

    public static final ReadWriteLens<AccountWithDeCapitalizeSpecificFactoryName, Integer> ACCOUNT_CURRENCY_CODE_READ_WRITE_LENS = Lenses.readLens(AccountWithDeCapitalizeSpecificFactoryName::getCurrency)
            .andThen(Lenses.readWriteLens(Currency::getId, Currency::setId));

    private SpecificFactoryName() {
    }
}