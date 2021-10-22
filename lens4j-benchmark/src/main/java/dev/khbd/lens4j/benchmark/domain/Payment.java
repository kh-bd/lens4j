package dev.khbd.lens4j.benchmark.domain;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

/**
 * @author Sergei_Khadanovich
 */
@GenLenses(lenses = @Lens(lensName = "CODE_LENS", path = "payerAccount.currency.code"))
public class Payment {

    private Account payerAccount;

    public Account getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(Account payerAccount) {
        this.payerAccount = payerAccount;
    }
}
