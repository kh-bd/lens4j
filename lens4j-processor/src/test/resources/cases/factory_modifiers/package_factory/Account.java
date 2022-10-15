package cases.factory_modifiers.package_factory;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(lenses = @Lens(path = "currency.code", lensName = "LENS"), accessLevel = GenLenses.AccessLevel.PACKAGE)
public class Account {
    private Currency currency;

    public Currency getCurrency() {
        return currency;
    }
}
