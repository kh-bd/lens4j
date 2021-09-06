package util;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(
        lenses = {
                @Lens(path = "receiver.bank.bic", lensName = "PAYMENT_RECEIVER_BANK_BIC_READ_LENS"),
                @Lens(path = "receiver.bank.bic", lensName = "PAYMENT_RECEIVER_BANK_BIC_READ_WRITE_LENS", type = LensType.READ_WRITE),
                @Lens(path = "payer.bank.bic", lensName = "PAYMENT_PAYER_BANK_BIC_READ_LENS"),
                @Lens(path = "payer.bank.bic", lensName = "PAYMENT_PAYER_BANK_BIC_READ_WRITE_LENS", type = LensType.READ_WRITE)
        }
)
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