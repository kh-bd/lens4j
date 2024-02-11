package cases.repeat.more_factories;

import dev.khbd.lens4j.core.ReadLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class FactoryImpl1 {
    public static final ReadLens<Customer, String> CUSTOMER_NAME1 = new ReadLens<Customer, String>() {
        @Override
        public final String get(Customer object) {
            if (object == null) {
                return null;
            }
            return object.getName();
        }

    };

    private FactoryImpl1() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}