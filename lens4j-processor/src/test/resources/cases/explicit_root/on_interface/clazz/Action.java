package cases.explicit_root.on_interface.clazz;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(root = Customer.class, lenses = @Lens(path = "name", lensName = "NAME"))
public interface Action {
}

class Customer {

    private String name;

    public String getName() {
        return name;
    }
}