package cases.explicit_root.on_interface.root_interface;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(root = Action.class, lenses = @Lens(path = "name()", lensName = "NAME"))
public interface Action {

    String name();
}
