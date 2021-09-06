package util;

import com.github.lens.core.annotations.GenLenses;
import com.github.lens.core.annotations.Lens;
import com.github.lens.core.annotations.LensType;

@GenLenses(lenses = {
        @Lens(path = "currencyFieldForTest.code"),
        @Lens(path = "currencyFieldForTest.id", type = LensType.READ_WRITE)
})
public class AccountWithEmptyLensName {
    private String id;
    private String accountNumber;
    private String bic;
    private Currency currencyFieldForTest;

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

    public void setCurrencyFieldForTest(Currency currencyFieldForTest) {
        this.currencyFieldForTest = currencyFieldForTest;
    }

    public Currency getCurrencyFieldForTest() {
        return currencyFieldForTest;
    }
}