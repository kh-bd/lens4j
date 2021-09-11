package util.examples;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "account.bid"))
class FieldNotFound {
    Account account;

    public void setAccount(Account account) {
        this.account = account;
    }

    Account getAccount() {
        return account;
    }
}
