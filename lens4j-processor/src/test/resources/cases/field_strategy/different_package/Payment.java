package cases.field_strategy.different_package;

import cases.field_strategy.different_package.other.Account;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(lenses = {
        @Lens(path = "account.privateNumber"),
        @Lens(path = "account.privateNumber", type = LensType.READ_WRITE),
        @Lens(path = "account.protectedNumber"),
        @Lens(path = "account.protectedNumber", type = LensType.READ_WRITE),
        @Lens(path = "account.packageNumber"),
        @Lens(path = "account.packageNumber", type = LensType.READ_WRITE),
        @Lens(path = "account.publicNumber"),
        @Lens(path = "account.publicNumber", type = LensType.READ_WRITE)
})
public class Payment {
    private Account account;

    public Account getAccount() {
        return account;
    }
}
