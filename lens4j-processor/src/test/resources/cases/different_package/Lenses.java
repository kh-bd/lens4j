package cases.different_package;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(root = Currency.class, lenses = @Lens(path = "code"), factoryName = "FactoryImpl")
public class Lenses {
}