package cases.generic.second_sub_class_supplied_simple_type;

import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
final class SecondChildLenses {
    public static final ReadLens<SecondChild, int[]> A_READ_LENS = Lenses.readLens(SecondChild::getA);

    public static final ReadWriteLens<SecondChild, int[]> A_READ_WRITE_LENS = Lenses.readWriteLens(SecondChild::getA, SecondChild::setA);

    public static final ReadLens<SecondChild, String> B_READ_LENS = Lenses.readLens(SecondChild::getB);

    public static final ReadWriteLens<SecondChild, String> B_READ_WRITE_LENS = Lenses.readWriteLens(SecondChild::getB, SecondChild::setB);

    private SecondChildLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
