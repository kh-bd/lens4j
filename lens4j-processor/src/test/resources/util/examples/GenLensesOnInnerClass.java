package util.examples;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

class GenLensesOnInnerClass {

    @GenLenses(lenses = @Lens(path = "account.bic"))
    class InnerClass {
        Account account;

        public void setAccount(Account account) {
            this.account = account;
        }

        Account getAccount() {
            return account;
        }
    }
}
