package cases.primitive_type_at_the_end;

import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Boolean;
import java.lang.Byte;
import java.lang.Character;
import java.lang.Double;
import java.lang.Float;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Short;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class WithPrimitiveLenses {
    public static final ReadLens<WithPrimitive, Byte> B_READ_LENS = Lenses.readLens(WithPrimitive::getB);

    public static final ReadWriteLens<WithPrimitive, Byte> B_READ_WRITE_LENS = Lenses.readWriteLens(WithPrimitive::getB, WithPrimitive::setB);

    public static final ReadLens<WithPrimitive, Short> SH_READ_LENS = Lenses.readLens(WithPrimitive::getSh);

    public static final ReadWriteLens<WithPrimitive, Short> SH_READ_WRITE_LENS = Lenses.readWriteLens(WithPrimitive::getSh, WithPrimitive::setSh);

    public static final ReadLens<WithPrimitive, Integer> I_READ_LENS = Lenses.readLens(WithPrimitive::getI);

    public static final ReadWriteLens<WithPrimitive, Integer> I_READ_WRITE_LENS = Lenses.readWriteLens(WithPrimitive::getI, WithPrimitive::setI);

    public static final ReadLens<WithPrimitive, Long> L_READ_LENS = Lenses.readLens(WithPrimitive::getL);

    public static final ReadWriteLens<WithPrimitive, Long> L_READ_WRITE_LENS = Lenses.readWriteLens(WithPrimitive::getL, WithPrimitive::setL);

    public static final ReadLens<WithPrimitive, Float> F_READ_LENS = Lenses.readLens(WithPrimitive::getF);

    public static final ReadWriteLens<WithPrimitive, Float> F_READ_WRITE_LENS = Lenses.readWriteLens(WithPrimitive::getF, WithPrimitive::setF);

    public static final ReadLens<WithPrimitive, Double> D_READ_LENS = Lenses.readLens(WithPrimitive::getD);

    public static final ReadWriteLens<WithPrimitive, Double> D_READ_WRITE_LENS = Lenses.readWriteLens(WithPrimitive::getD, WithPrimitive::setD);

    public static final ReadLens<WithPrimitive, Character> CH_READ_LENS = Lenses.readLens(WithPrimitive::getCh);

    public static final ReadWriteLens<WithPrimitive, Character> CH_READ_WRITE_LENS = Lenses.readWriteLens(WithPrimitive::getCh, WithPrimitive::setCh);

    public static final ReadLens<WithPrimitive, Boolean> BOOL_READ_LENS = Lenses.readLens(WithPrimitive::getBool);

    public static final ReadWriteLens<WithPrimitive, Boolean> BOOL_READ_WRITE_LENS = Lenses.readWriteLens(WithPrimitive::getBool, WithPrimitive::setBool);

    private WithPrimitiveLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
