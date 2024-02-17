package cases.field_strategy;

import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, String> ACCOUNT_PRIVATE_NUMBER_READ_LENS = new ReadLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Account account = object.getAccount();
            if (account == null) {
                return null;
            }
            return account.getPrivateNumber();
        }
    };

    public static final ReadWriteLens<Payment, String> ACCOUNT_PRIVATE_NUMBER_READ_WRITE_LENS = new ReadWriteLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Account account = object.getAccount();
            if (account == null) {
                return null;
            }
            return account.getPrivateNumber();
        }

        @Override
        public final void set(Payment object, String property) {
            if (object == null) {
                return;
            }
            Account account = object.getAccount();
            if (account == null) {
                return;
            }
            account.setPrivateNumber(property);
        }
    };

    public static final ReadLens<Payment, String> ACCOUNT_PROTECTED_NUMBER_READ_LENS = new ReadLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Account account = object.getAccount();
            if (account == null) {
                return null;
            }
            return account.protectedNumber;
        }
    };

    public static final ReadWriteLens<Payment, String> ACCOUNT_PROTECTED_NUMBER_READ_WRITE_LENS = new ReadWriteLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Account account = object.getAccount();
            if (account == null) {
                return null;
            }
            return account.protectedNumber;
        }

        @Override
        public final void set(Payment object, String property) {
            if (object == null) {
                return;
            }
            Account account = object.getAccount();
            if (account == null) {
                return;
            }
            account.protectedNumber = property;
        }
    };

    public static final ReadLens<Payment, String> ACCOUNT_PACKAGE_NUMBER_READ_LENS = new ReadLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Account account = object.getAccount();
            if (account == null) {
                return null;
            }
            return account.packageNumber;
        }
    };

    public static final ReadWriteLens<Payment, String> ACCOUNT_PACKAGE_NUMBER_READ_WRITE_LENS = new ReadWriteLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Account account = object.getAccount();
            if (account == null) {
                return null;
            }
            return account.packageNumber;
        }

        @Override
        public final void set(Payment object, String property) {
            if (object == null) {
                return;
            }
            Account account = object.getAccount();
            if (account == null) {
                return;
            }
            account.packageNumber = property;
        }
    };

    public static final ReadLens<Payment, String> ACCOUNT_PUBLIC_NUMBER_READ_LENS = new ReadLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Account account = object.getAccount();
            if (account == null) {
                return null;
            }
            return account.publicNumber;
        }
    };

    public static final ReadWriteLens<Payment, String> ACCOUNT_PUBLIC_NUMBER_READ_WRITE_LENS = new ReadWriteLens<Payment, String>() {
        @Override
        public final String get(Payment object) {
            if (object == null) {
                return null;
            }
            Account account = object.getAccount();
            if (account == null) {
                return null;
            }
            return account.publicNumber;
        }

        @Override
        public final void set(Payment object, String property) {
            if (object == null) {
                return;
            }
            Account account = object.getAccount();
            if (account == null) {
                return;
            }
            account.publicNumber = property;
        }
    };

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
