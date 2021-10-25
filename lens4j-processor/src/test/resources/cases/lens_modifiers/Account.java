package cases.lens_modifiers;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.Lens.AccessLevel;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(
        lenses = {
                @Lens(path = "currency.code", lensName = "READ_DEFAULT"),
                @Lens(path = "currency.code", lensName = "READ_PUBLIC", accessLevel = AccessLevel.PUBLIC),
                @Lens(path = "currency.code", lensName = "READ_PACKAGE", accessLevel = AccessLevel.PACKAGE),
                @Lens(path = "currency.code", lensName = "WRITE_DEFAULT", type = LensType.READ_WRITE),
                @Lens(path = "currency.code", lensName = "WRITE_PUBLIC", type = LensType.READ_WRITE, accessLevel = AccessLevel.PUBLIC),
                @Lens(path = "currency.code", lensName = "WRITE_PACKAGE", type = LensType.READ_WRITE, accessLevel = AccessLevel.PACKAGE)
        }
)
public class Account {
    private Currency currency;

    public Currency getCurrency() {
        return currency;
    }
}
