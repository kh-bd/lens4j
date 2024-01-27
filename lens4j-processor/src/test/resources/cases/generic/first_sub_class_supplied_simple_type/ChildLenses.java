package cases.generic.first_sub_class_supplied_simple_type;

import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class ChildLenses {
    public static final ReadLens<Child, String> VALUE_READ_LENS = new ReadLens<Child, String>() {
        @Override
        public final String get(Child object) {
            if (object == null) {
                return null;
            }
            return object.getValue();
        }
    };

    public static final ReadWriteLens<Child, String> VALUE_READ_WRITE_LENS = new ReadWriteLens<Child, String>() {
        @Override
        public final String get(Child object) {
            if (object == null) {
                return null;
            }
            return object.getValue();
        }

        @Override
        public final void set(Child object, String property) {
            if (object == null) {
                return;
            }
            object.setValue(property);
        }
    };

    private ChildLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
