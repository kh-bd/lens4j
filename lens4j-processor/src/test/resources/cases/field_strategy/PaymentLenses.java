package cases.field_strategy;

import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class PaymentLenses {
    public static final ReadLens<Payment, String> ACCOUNT_PRIVATE_NUMBER_READ_LENS = Lenses.readLens(Payment::getAccount).andThen(Lenses.readLens(Account::getPrivateNumber));

    public static final ReadWriteLens<Payment, String> ACCOUNT_PRIVATE_NUMBER_READ_WRITE_LENS = Lenses.readLens(Payment::getAccount).andThen(Lenses.readWriteLens(Account::getPrivateNumber, Account::setPrivateNumber));

    public static final ReadLens<Payment, String> ACCOUNT_PROTECTED_NUMBER_READ_LENS = Lenses.readLens(Payment::getAccount).andThen(Lenses.readLens((Account o) -> o.protectedNumber));

    public static final ReadWriteLens<Payment, String> ACCOUNT_PROTECTED_NUMBER_READ_WRITE_LENS = Lenses.readLens(Payment::getAccount).andThen(Lenses.readWriteLens((Account o) -> o.protectedNumber, (Account o, String p) -> o.protectedNumber = p));

    public static final ReadLens<Payment, String> ACCOUNT_PACKAGE_NUMBER_READ_LENS = Lenses.readLens(Payment::getAccount).andThen(Lenses.readLens((Account o) -> o.packageNumber));

    public static final ReadWriteLens<Payment, String> ACCOUNT_PACKAGE_NUMBER_READ_WRITE_LENS = Lenses.readLens(Payment::getAccount).andThen(Lenses.readWriteLens((Account o) -> o.packageNumber, (Account o, String p) -> o.packageNumber = p));

    public static final ReadLens<Payment, String> ACCOUNT_PUBLIC_NUMBER_READ_LENS = Lenses.readLens(Payment::getAccount).andThen(Lenses.readLens((Account o) -> o.publicNumber));

    public static final ReadWriteLens<Payment, String> ACCOUNT_PUBLIC_NUMBER_READ_WRITE_LENS = Lenses.readLens(Payment::getAccount).andThen(Lenses.readWriteLens((Account o) -> o.publicNumber, (Account o, String p) -> o.publicNumber = p));

    private PaymentLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}