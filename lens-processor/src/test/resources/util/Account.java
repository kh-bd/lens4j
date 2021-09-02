package util;

import com.github.lens.processor.annotations.GenReadLens;
import com.github.lens.processor.annotations.GenReadWriteLens;

@GenReadLens(lensName = "ACCOUNT_CUR_CODE_READ_LENS", path = "currency.code")
@GenReadWriteLens(lensName = "ACCOUNT_CUR_CODE_READ_WRITE_LENS", path = "currency.code")
public class Account {
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