package cases.factory_modifiers.different_access;

import dev.khbd.lens4j.core.ReadLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class AccountLenses {
    public static final ReadLens<Account, String> LENS = new ReadLens<Account, String>() {
        @Override
        public final String get(Account object) {
            if (object == null) {
                return null;
            }
            return object.getAccountNumber();
        }
    };

    private AccountLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
