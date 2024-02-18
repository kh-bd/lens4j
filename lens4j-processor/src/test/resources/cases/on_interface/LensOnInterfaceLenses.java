package cases.on_interface;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.Integer;
import java.lang.Override;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class LensOnInterfaceLenses {
    public static final ReadLens<LensOnInterface, Integer> GET_CURRENCY_ID_READ_LENS = new ReadLens<LensOnInterface, Integer>() {
        @Override
        public final Integer get(LensOnInterface object) {
            if (object == null) {
                return null;
            }
            Currency getCurrency = object.getCurrency();
            if (getCurrency == null) {
                return null;
            }
            return getCurrency.getId();
        }
    };

    private LensOnInterfaceLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
