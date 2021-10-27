package cases.method.found;

import common.Payer;
import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;
import java.lang.UnsupportedOperationException;

@GenLenses(
        lenses = {
                @Lens(path = "getPayer()", lensName = "M_READ_LENS"),
                @Lens(path = "payer.getBank()", lensName = "P_M_READ_LENS"),
                @Lens(path = "getPayer().getBank()", lensName = "M_M_READ_LENS"),
                @Lens(path = "getPayer().bank", lensName = "M_P_READ_LENS"),
                @Lens(path = "getPayer().bank", lensName = "M_P_READ_WRITE_LENS", type = LensType.READ_WRITE)
        }
)
public class Payment {
    private Payer payer;

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }
}