@GenLenses(root = Currency.class, lenses = @Lens(path = "code", lensName = "CODE"), factoryName = "Factory", accessLevel = GenLenses.AccessLevel.PUBLIC)
package cases.on_package.different_package;

import common.Currency;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

