package cases.record.method;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(
        lenses = {
                @Lens(path = "currency()", lensName = "CUR_LENS"),
                @Lens(path = "currency().code", lensName = "CUR_CODE_LENS", type = LensType.READ_WRITE)
        }
)
public record Account(String id, Currency currency) {
}
