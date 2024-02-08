package cases.factory_modifiers.different_access;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

public class Account {

    private String accountNumber;

    String getAccountNumber() {
        return accountNumber;
    }
}



@GenLenses(root = Account.class, factoryName = "AccountLenses", lenses = @Lens(path = "accountNumber", lensName = "LENS"))
class Lenses {
}
