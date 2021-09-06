package util.examples;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(
        factoryName = "specificFactoryName",
        lenses = {
                @Lens(path = "currency.code", lensName = "ACCOUNT_CURRENCY_CODE_READ_LENS"),
                @Lens(path = "currency.id", lensName = "ACCOUNT_CURRENCY_CODE_READ_WRITE_LENS", type = LensType.READ_WRITE)
        }
)
public class AccountWithDeCapitalizeSpecificFactoryName {
    private String id;
    private String accountNumber;
    private String bic;
    private Currency currency;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getBic() {
        return bic;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }
}