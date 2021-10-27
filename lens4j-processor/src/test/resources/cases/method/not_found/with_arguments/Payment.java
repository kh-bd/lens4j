package cases.method.not_found.with_arguments;

import common.Payer;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "payer()"))
public class Payment {

    private Payer payer;

    public Payer payer(int index) { // ignored
        return payer;
    }
}