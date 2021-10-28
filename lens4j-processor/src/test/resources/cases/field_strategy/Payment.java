package cases.field_strategy;

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

class Account {
    private String privateNumber;
    public String publicNumber;
    String packageNumber;
    protected String protectedNumber;

    public String getPrivateNumber() {
        return privateNumber;
    }

    public void setPrivateNumber(String privateNumber) {
        this.privateNumber = privateNumber;
    }
}