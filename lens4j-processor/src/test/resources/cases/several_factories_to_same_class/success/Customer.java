package cases.several_factories_to_same_class.success;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "name", lensName = "CUSTOMER_NAME"), factoryName = "FactoryImpl")
public class Customer {

    private String name;

    public String getName() {
        return name;
    }
}