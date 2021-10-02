package cases.sub_class;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = {
        @Lens(path = "publicCurrency.code"),
        @Lens(path = "packageCurrency.code"),
        @Lens(path = "protectedCurrency.code"),
        @Lens(path = "privateCurrency.code")
})
public class SubClass extends BaseClass {
}
