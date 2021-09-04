package util;

import com.github.lens.core.Lenses;
import com.github.lens.core.ReadLens;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("com.github.lens.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, String> PAYMENT_RECEIVER_BANK_BIC_READ_LENS = Lenses.readLens(Payment::getReceiver)
            .andThen(Lenses.readLens(Receiver::getBank))
            .andThen(Lenses.readLens(Bank::getBic));

    public static final ReadLens<Payment, String> PAYMENT_RECEIVER_BANK_BIC_READ_WRITE_LENS = Lenses.readLens(Payment::getReceiver)
            .andThen(Lenses.readLens(Receiver::getBank))
            .andThen(Lenses.readWriteLens(Bank::getBic, Bank::setBic));

    public static final ReadLens<Payment, String> PAYMENT_PAYER_BANK_BIC_READ_LENS = Lenses.readLens(Payment::getPayer)
            .andThen(Lenses.readLens(Payer::getBank))
            .andThen(Lenses.readLens(Bank::getBic));

    public static final ReadLens<Payment, String> PAYMENT_PAYER_BANK_BIC_READ_WRITE_LENS = Lenses.readLens(Payment::getPayer)
            .andThen(Lenses.readLens(Payer::getBank))
            .andThen(Lenses.readWriteLens(Bank::getBic, Bank::setBic));

    private PaymentLenses() {
    }
}