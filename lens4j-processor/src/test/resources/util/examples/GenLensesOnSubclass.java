package util.examples;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

class GenLensesOnSubclass {

    @GenLenses(lenses = @Lens(path = "account.bic"))
    class SubClass {
        Account account;

        public void setAccount(Account account) {
            this.account = account;
        }

        Account getAccount() {
            return account;
        }
    }
}
