package cases.generic.first_sub_class_supplied_simple_type;

import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class ChildLenses {
    public static final ReadLens<Child, String> VALUE_READ_LENS = Lenses.readLens(Child::getValue);

    public static final ReadWriteLens<Child, String> VALUE_READ_WRITE_LENS = Lenses.readWriteLens(Child::getValue, Child::setValue);

    private ChildLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
