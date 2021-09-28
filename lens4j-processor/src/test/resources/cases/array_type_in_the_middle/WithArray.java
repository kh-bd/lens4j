package cases.array_type_in_the_middle;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "primitiveArray.length"))
public class WithArray {
    byte[] primitiveArray;

    public byte[] getPrimitiveArray() {
        return primitiveArray;
    }

    public void setPrimitiveArray(byte[] primitiveArray) {
        this.primitiveArray = primitiveArray;
    }
}
