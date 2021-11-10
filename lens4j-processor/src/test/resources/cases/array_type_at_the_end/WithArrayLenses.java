package cases.array_type_at_the_end;

import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class WithArrayLenses {
    public static final ReadLens<WithArray, byte[]> PRIMITIVE_ARRAY_READ_LENS = Lenses.readLens(WithArray::getPrimitiveArray);

    public static final ReadWriteLens<WithArray, byte[]> PRIMITIVE_ARRAY_READ_WRITE_LENS = Lenses.readWriteLens(WithArray::getPrimitiveArray, WithArray::setPrimitiveArray);

    public static final ReadLens<WithArray, String[]> DECLAR_ARRAY_READ_LENS = Lenses.readLens(WithArray::getDeclarArray);

    public static final ReadWriteLens<WithArray, String[]> DECLAR_ARRAY_READ_WRITE_LENS = Lenses.readWriteLens(WithArray::getDeclarArray, WithArray::setDeclarArray);

    private WithArrayLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
