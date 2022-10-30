package cases.multi_property;

import common.Bank;
import common.Payer;
import common.Receiver;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, String> PAYMENT_RECEIVER_BANK_BIC_READ_LENS = new ReadLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Receiver receiver = object.getReceiver();
            if (receiver == null) {
                return null;
            }
            Bank bank = receiver.getBank();
            if (bank == null) {
                return null;
            }
            return bank.getBic();
        }
    };

    public static final ReadWriteLens<Payment, String> PAYMENT_RECEIVER_BANK_BIC_READ_WRITE_LENS = new ReadWriteLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Receiver receiver = object.getReceiver();
            if (receiver == null) {
                return null;
            }
            Bank bank = receiver.getBank();
            if (bank == null) {
                return null;
            }
            return bank.getBic();
        }

        @Override
        public final void set(Payment object, String property) {
            if (object == null) {
                return;
            }
            Receiver receiver = object.getReceiver();
            if (receiver == null) {
                return;
            }
            Bank bank = receiver.getBank();
            if (bank == null) {
                return;
            }
            bank.setBic(property);
        }
    };

    public static final ReadLens<Payment, String> PAYMENT_PAYER_BANK_BIC_READ_LENS = new ReadLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Payer payer = object.getPayer();
            if (payer == null) {
                return null;
            }
            Bank bank = payer.getBank();
            if (bank == null) {
                return null;
            }
            return bank.getBic();
        }
    };

    public static final ReadWriteLens<Payment, String> PAYMENT_PAYER_BANK_BIC_READ_WRITE_LENS = new ReadWriteLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Payer payer = object.getPayer();
            if (payer == null) {
                return null;
            }
            Bank bank = payer.getBank();
            if (bank == null) {
                return null;
            }
            return bank.getBic();
        }

        @Override
        public final void set(Payment object, String property) {
            if (object == null) {
                return;
            }
            Payer payer = object.getPayer();
            if (payer == null) {
                return;
            }
            Bank bank = payer.getBank();
            if (bank == null) {
                return;
            }
            bank.setBic(property);
        }
    };

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
