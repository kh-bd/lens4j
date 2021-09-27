package cases.primitive_type_in_the_middle;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "b.bitCount"))
public class WithPrimitive {
    byte b;

    byte getB() {
        return b;
    }

    void setB(byte b) {
        this.b = b;
    }
}
