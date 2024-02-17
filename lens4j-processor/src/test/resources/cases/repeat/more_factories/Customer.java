package cases.repeat.more_factories;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "name", lensName = "CUSTOMER_NAME1"), factoryName = "FactoryImpl1")
@GenLenses(lenses = @Lens(path = "name", lensName = "CUSTOMER_NAME2"), factoryName = "FactoryImpl2")
public class Customer {

    private String name;

    public String getName() {
        return name;
    }
}