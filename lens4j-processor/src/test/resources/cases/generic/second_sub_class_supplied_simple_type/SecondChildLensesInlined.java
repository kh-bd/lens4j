package cases.generic.second_sub_class_supplied_simple_type;

import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class SecondChildLenses {
    public static final ReadLens<SecondChild, int[]> A_READ_LENS = new ReadLens<SecondChild, int[]>() {
        @Override
        public final int[] get(SecondChild object) {
            if (object == null) {
                return null;
            }
            return object.getA();
        }
    };

    public static final ReadWriteLens<SecondChild, int[]> A_READ_WRITE_LENS = new ReadWriteLens<SecondChild, int[]>() {
        @Override
        public final int[] get(SecondChild object) {
            if (object == null) {
                return null;
            }
            return object.getA();
        }

        @Override
        public final void set(SecondChild object, int[] property) {
            if (object == null) {
                return;
            }
            object.setA(property);
        }
    };

    public static final ReadLens<SecondChild, String> B_READ_LENS = new ReadLens<SecondChild, String>() {
        @Override
        public final String get(SecondChild object) {
            if (object == null) {
                return null;
            }
            return object.getB();
        }
    };

    public static final ReadWriteLens<SecondChild, String> B_READ_WRITE_LENS = new ReadWriteLens<SecondChild, String>() {
        @Override
        public final String get(SecondChild object) {
            if (object == null) {
                return null;
            }
            return object.getB();
        }

        @Override
        public final void set(SecondChild object, String property) {
            if (object == null) {
                return;
            }
            object.setB(property);
        }
    };

    private SecondChildLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
