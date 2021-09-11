package util.examples;

import common.Currency;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Integer;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class PackagePrivateAccountLenses {
    public static final ReadLens<PackagePrivateAccount, String> ACCOUNT_NUMBER = Lenses.readLens(PackagePrivateAccount::getAccountNumber);

    public static final ReadWriteLens<PackagePrivateAccount, String> ACCOUNT_NUMBER_READ_WRITE_LENS = Lenses.readWriteLens(PackagePrivateAccount::getAccountNumber, PackagePrivateAccount::setAccountNumber);

    public static final ReadLens<PackagePrivateAccount, String> ACCOUNT_CURRENCY_CODE_READ_LENS = Lenses.readLens(PackagePrivateAccount::getCurrency).andThen(Lenses.readLens(Currency::getCode));

    public static final ReadWriteLens<PackagePrivateAccount, Integer> ACCOUNT_CURRENCY_CODE_READ_WRITE_LENS = Lenses.readLens(PackagePrivateAccount::getCurrency).andThen(Lenses.readWriteLens(Currency::getId, Currency::setId));

    private PackagePrivateAccountLenses() {
    }
}
