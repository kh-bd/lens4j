package cases.method.not_found._static;

import common.Payer;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "payer()"))
public class Payment {

    private Payer payer;

    public static Payer payer() { // ignored
        return payer;
    }
}