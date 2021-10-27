package cases.method.not_found.return_void;

import common.Payer;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "payer()"))
public class Payment {

    private Payer payer;

    public void payer() { // ignored
    }
}