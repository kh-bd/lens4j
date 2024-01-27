package cases.several_factories_to_same_class.success;

import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class FactoryImpl {
    public static final ReadLens<Customer, String> CUSTOMER_NAME = Lenses.readLens(Customer::getName);

    public static final ReadLens<Client, String> CLIENT_NAME = Lenses.readLens(Client::getName);

    private FactoryImpl() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}