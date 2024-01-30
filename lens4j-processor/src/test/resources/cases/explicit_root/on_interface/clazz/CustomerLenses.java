package cases.explicit_root.on_interface.clazz;

import dev.khbd.lens4j.core.ReadLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class CustomerLenses {
    public static final ReadLens<Customer, String> NAME = new ReadLens<Customer, String>() {
        @Override
        public final String get(Customer object) {
            if (object == null) {
                return null;
            }
            return object.getName();
        }

    };

    private CustomerLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}