package util;

import com.github.lens.core.annotations.GenLenses;
import com.github.lens.core.annotations.Lens;
import com.github.lens.core.annotations.LensType;

@GenLenses(
        lenses = {
                @Lens(path = "currency.code", lensName = "ACCOUNT_CURRENCY_CODE_LENS"),
                @Lens(path = "currency.code", lensName = "ACCOUNT_CURRENCY_CODE_LENS", type = LensType.READ_WRITE)
        }
)
public class AccountWithTheSameLensNames {
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