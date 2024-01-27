package cases.method.found;

import common.Bank;
import common.Payer;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, Payer> M_READ_LENS = new ReadLens<Payment, Payer>() {
        @Override
        public final Payer get(Payment object) {
            if (object == null) {
                return null;
            }
            return object.getPayer();
        }
    };

    public static final ReadLens<Payment, Bank> P_M_READ_LENS = new ReadLens<Payment, Bank>() {
        @Override
        public final Bank get(Payment object) {
            if (object == null) {
                return null;
            }
            Payer payer = object.getPayer();
            if (payer == null) {
                return null;
            }
            return payer.getBank();
        }
    };

    public static final ReadLens<Payment, Bank> M_M_READ_LENS = new ReadLens<Payment, Bank>() {
        @Override
        public final Bank get(Payment object) {
            if (object == null) {
                return null;
            }
            Payer getPayer = object.getPayer();
            if (getPayer == null) {
                return null;
            }
            return getPayer.getBank();
        }
    };

    public static final ReadLens<Payment, Bank> M_P_READ_LENS = new ReadLens<Payment, Bank>() {
        @Override
        public final Bank get(Payment object) {
            if (object == null) {
                return null;
            }
            Payer getPayer = object.getPayer();
            if (getPayer == null) {
                return null;
            }
            return getPayer.getBank();
        }
    };

    public static final ReadWriteLens<Payment, Bank> M_P_READ_WRITE_LENS = new ReadWriteLens<Payment, Bank>() {
        @Override
        public final Bank get(Payment object) {
            if (object == null) {
                return null;
            }
            Payer getPayer = object.getPayer();
            if (getPayer == null) {
                return null;
            }
            return getPayer.getBank();
        }

        @Override
        public final void set(Payment object, Bank property) {
            if (object == null) {
                return;
            }
            Payer getPayer = object.getPayer();
            if (getPayer == null) {
                return;
            }
            getPayer.setBank(property);
        }
    };

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
