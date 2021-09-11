package cases.package_private_class;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(
        lenses = {
                @Lens(path = "accountNumber"),
                @Lens(path = "accountNumber", lensName = "ACCOUNT_NUMBER_READ_WRITE_LENS", type = LensType.READ_WRITE),
                @Lens(path = "currency.code", lensName = "ACCOUNT_CURRENCY_CODE_READ_LENS"),
                @Lens(path = "currency.id", lensName = "ACCOUNT_CURRENCY_CODE_READ_WRITE_LENS", type = LensType.READ_WRITE)
        }
)
class PackagePrivateAccount {
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
