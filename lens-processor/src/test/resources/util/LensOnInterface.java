package util;

import com.github.lens.core.annotations.GenLenses;
import com.github.lens.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "account.id"))
public interface LensOnInterface {
    Account getAccount();
}
