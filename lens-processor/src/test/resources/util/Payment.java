package util;

import com.github.lens.core.annotations.GenReadLens;
import com.github.lens.core.annotations.GenReadWriteLens;

@GenReadLens(lensName = "PAYMENT_RECEIVER_BANK_BIC_READ_LENS", path = "receiver.bank.bic")
@GenReadLens(lensName = "PAYMENT_PAYER_BANK_BIC_READ_LENS", path = "payer.bank.bic")
@GenReadWriteLens(lensName = "PAYMENT_RECEIVER_BANK_BIC_READ_WRITE_LENS", path = "receiver.bank.bic")
@GenReadWriteLens(lensName = "PAYMENT_PAYER_BANK_BIC_READ_WRITE_LENS", path = "payer.bank.bic")
public class Payment {
    private String id;
    private Payer payer;
    private Receiver receiver;
    private Currency currency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}