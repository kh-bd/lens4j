package util.examples;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "currency.id"))
public interface LensOnInterface {
    Currency getCurrency();
}
