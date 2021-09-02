package com.github.lens;

import com.github.lens.core.ReadLens;
import java.lang.String;
import javax.annotation.processing.Generated;
import util.Bank;
import util.Payer;
import util.Payment;
import util.Receiver;

@Generated("com.github.lens.processor.LensProcessor")
public final class Lenses {
    public static final ReadLens<Payment, String> PAYMENT_RECEIVER_BANK_BIC_READ_LENS = com.github.lens.core.Lenses.readLens(Payment::getReceiver)
            .andThen(com.github.lens.core.Lenses.readLens(Receiver::getBank))
            .andThen(com.github.lens.core.Lenses.readLens(Bank::getBic));

    public static final ReadLens<Payment, String> PAYMENT_PAYER_BANK_BIC_READ_LENS = com.github.lens.core.Lenses.readLens(Payment::getPayer)
            .andThen(com.github.lens.core.Lenses.readLens(Payer::getBank))
            .andThen(com.github.lens.core.Lenses.readLens(Bank::getBic));

    public static final ReadLens<Payment, String> PAYMENT_RECEIVER_BANK_BIC_READ_WRITE_LENS = com.github.lens.core.Lenses.readLens(Payment::getReceiver)
            .andThen(com.github.lens.core.Lenses.readLens(Receiver::getBank))
            .andThen(com.github.lens.core.Lenses.readWriteLens(Bank::getBic, Bank::setBic));

    public static final ReadLens<Payment, String> PAYMENT_PAYER_BANK_BIC_READ_WRITE_LENS = com.github.lens.core.Lenses.readLens(Payment::getPayer)
            .andThen(com.github.lens.core.Lenses.readLens(Payer::getBank))
            .andThen(com.github.lens.core.Lenses.readWriteLens(Bank::getBic, Bank::setBic));

    private Lenses() {
    }
}