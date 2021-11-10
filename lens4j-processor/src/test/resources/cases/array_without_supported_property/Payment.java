package cases.array_length_support;

import common.Payer;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(
        lenses = {
                @Lens(path = "payers.length1", lensName = "ARRAY_LENGTH_READ_LENS")
        }
)
public class Payment {
    private Payer[] payers;

    public Payer[] getPayers() {
        return payers;
    }

    public void setPayers(Payer[] payers) {
        this.payers = payers;
    }
}