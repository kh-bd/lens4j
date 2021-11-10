package cases.array_length_support;

import common.Payer;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(
        lenses = {
                @Lens(path = "payers.length.someProperty", lensName = "ARRAY_LENGTH_READ_LENS")
        }
)
public class PaymentWithPropertyAfterLength {
    private Payer[] payers;

    public Payer[] getPayers() {
        return payers;
    }

    public void setPayers(Payer[] payers) {
        this.payers = payers;
    }
}