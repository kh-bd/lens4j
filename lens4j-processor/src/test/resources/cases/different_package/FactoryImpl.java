package cases.different_package;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class FactoryImpl {
    public static final ReadLens<Currency, String> CODE_READ_LENS = new ReadLens<Currency, String>() {
        @Override
        public final String get(Currency object) {
            if (object == null) {
                return null;
            }
            return object.getCode();
        }

    };

    private FactoryImpl() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}