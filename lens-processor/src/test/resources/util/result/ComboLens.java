package com.github.lens;

import com.github.lens.core.ReadLens;
import java.lang.String;
import javax.annotation.processing.Generated;
import util.Account;
import util.Bank;
import util.Currency;
import util.Payer;
import util.Payment;
import util.Receiver;

@Generated("com.github.lens.processor.LensProcessor")
public final class Lenses {
    public static final ReadLens<Account, String> ACCOUNT_CUR_CODE_READ_LENS = com.github.lens.core.Lenses.readLens(Account::getCurrency)
            .andThen(com.github.lens.core.Lenses.readLens(Currency::getCode));

    public static final ReadLens<Account, String> ACCOUNT_CUR_CODE_READ_WRITE_LENS = com.github.lens.core.Lenses.readLens(Account::getCurrency)
            .andThen(com.github.lens.core.Lenses.readWriteLens(Currency::getCode, Currency::setCode));

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
