package cases.on_interface;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "getCurrency().id"))
public interface LensOnInterface {
    Currency getCurrency();
}
