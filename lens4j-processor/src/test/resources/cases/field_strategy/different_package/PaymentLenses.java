package cases.field_strategy.different_package;

import cases.field_strategy.different_package.other.Account;
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
            return account.getProtectedNumber();
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
            return account.getProtectedNumber();
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
            account.setProtectedNumber(property);
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
            return account.getPackageNumber();
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
            return account.getPackageNumber();
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
            account.setPackageNumber(property);
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
