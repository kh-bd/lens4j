package cases.repeat;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "name", lensName = "CUSTOMER_NAME1"), factoryName = "CustomerLenses")
@GenLenses(lenses = @Lens(path = "name", lensName = "CUSTOMER_NAME2"), factoryName = "CustomerLenses")
public class Customer {

    private String name;

    public String getName() {
        return name;
    }
}