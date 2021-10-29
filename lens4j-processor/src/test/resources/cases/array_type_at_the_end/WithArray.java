package cases.array_type_at_the_end;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(
        lenses = {
                @Lens(path = "primitiveArray"),
                @Lens(path = "primitiveArray", type = LensType.READ_WRITE),
                @Lens(path = "declarArray"),
                @Lens(path = "declarArray", type = LensType.READ_WRITE)
        }
)
public class WithArray {
    private byte[] primitiveArray;
    private String[] declarArray;

    public byte[] getPrimitiveArray() {
        return primitiveArray;
    }

    public void setPrimitiveArray(byte[] primitiveArray) {
        this.primitiveArray = primitiveArray;
    }

    public String[] getDeclarArray() {
        return declarArray;
    }

    public void setDeclarArray(String[] declarArray) {
        this.declarArray = declarArray;
    }
}
