package util;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "account.id"))
public interface LensOnInterface {
    Account getAccount();
}
