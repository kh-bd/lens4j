package cases.method.found;

import common.Bank;
import common.Payer;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, Payer> M_READ_LENS = Lenses.readLens(Payment::getPayer);

    public static final ReadLens<Payment, Bank> P_M_READ_LENS = Lenses.readLens(Payment::getPayer)
            .andThen(Lenses.readLens(Payer::getBank));

    public static final ReadLens<Payment, Bank> M_M_READ_LENS = Lenses.readLens(Payment::getPayer)
            .andThen(Lenses.readLens(Payer::getBank));

    public static final ReadLens<Payment, Bank> M_P_READ_LENS = Lenses.readLens(Payment::getPayer)
            .andThen(Lenses.readLens(Payer::getBank));

    public static final ReadWriteLens<Payment, Bank> M_P_READ_WRITE_LENS = Lenses.readLens(Payment::getPayer)
            .andThen(Lenses.readWriteLens(Payer::getBank, Payer::setBank));

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}