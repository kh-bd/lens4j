package cases.array_type_at_the_end;

import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class WithArrayLenses {
    public static final ReadLens<WithArray, byte[]> PRIMITIVE_ARRAY_READ_LENS = new ReadLens<WithArray, byte[]>() {
        @Override
        public final byte[] get(WithArray object) {
            if (object == null) {
                return null;
            }
            return object.getPrimitiveArray();
        }
    };

    public static final ReadWriteLens<WithArray, byte[]> PRIMITIVE_ARRAY_READ_WRITE_LENS = new ReadWriteLens<WithArray, byte[]>() {
        @Override
        public final byte[] get(WithArray object) {
            if (object == null) {
                return null;
            }
            return object.getPrimitiveArray();
        }

        @Override
        public final void set(WithArray object, byte[] property) {
            if (object == null) {
                return;
            }
            object.setPrimitiveArray(property);
        }
    };

    public static final ReadLens<WithArray, String[]> DECLAR_ARRAY_READ_LENS = new ReadLens<WithArray, String[]>() {
        @Override
        public final String[] get(WithArray object) {
            if (object == null) {
                return null;
            }
            return object.getDeclarArray();
        }
    };

    public static final ReadWriteLens<WithArray, String[]> DECLAR_ARRAY_READ_WRITE_LENS = new ReadWriteLens<WithArray, String[]>() {
        @Override
        public final String[] get(WithArray object) {
            if (object == null) {
                return null;
            }
            return object.getDeclarArray();
        }

        @Override
        public final void set(WithArray object, String[] property) {
            if (object == null) {
                return;
            }
            object.setDeclarArray(property);
        }
    };

    private WithArrayLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
