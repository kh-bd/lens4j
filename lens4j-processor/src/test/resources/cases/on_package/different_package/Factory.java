package cases.on_package.different_package;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class Factory {
    public static final ReadLens<Currency, String> CODE = new ReadLens<Currency, String>() {
        @Override
        public final String get(Currency object) {
            if (object == null) {
                return null;
            }
            return object.getCode();
        }
    };

    private Factory() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
