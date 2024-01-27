package cases.several_factories_to_same_class.success;

import dev.khbd.lens4j.core.ReadLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class FactoryImpl {
    public static final ReadLens<Customer, String> CUSTOMER_NAME = new ReadLens<Customer, String>() {
        @Override
        public final String get(Customer object) {
            if (object == null) {
                return null;
            }
            return object.getName();
        }

    };

    public static final ReadLens<Client, String> CLIENT_NAME = new ReadLens<Client, String>() {
        @Override
        public final String get(Client object) {
            if (object == null) {
                return null;
            }
            return object.getName();
        }

    };

    private FactoryImpl() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}