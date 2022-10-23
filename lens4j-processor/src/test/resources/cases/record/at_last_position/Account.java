package cases.record.at_last_position;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(lenses = @Lens(path = "currency.code", lensName = "LENS", type = LensType.READ_WRITE))
public record Account(String id, Currency currency) {
}

record Currency(String code) {}
