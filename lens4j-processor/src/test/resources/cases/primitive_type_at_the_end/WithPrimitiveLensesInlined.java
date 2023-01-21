package cases.primitive_type_at_the_end;

import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import java.lang.Boolean;
import java.lang.Byte;
import java.lang.Character;
import java.lang.Double;
import java.lang.Float;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.Short;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class WithPrimitiveLenses {
    public static final ReadLens<WithPrimitive, Byte> B_READ_LENS = new ReadLens<WithPrimitive, Byte>() {
        @Override
        public final Byte get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getB();
        }
    };

    public static final ReadWriteLens<WithPrimitive, Byte> B_READ_WRITE_LENS = new ReadWriteLens<WithPrimitive, Byte>() {
        @Override
        public final Byte get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getB();
        }

        @Override
        public final void set(WithPrimitive object, Byte property) {
            if (object == null) {
                return;
            }
            object.setB(property);
        }
    };

    public static final ReadLens<WithPrimitive, Short> SH_READ_LENS = new ReadLens<WithPrimitive, Short>() {
        @Override
        public final Short get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getSh();
        }
    };

    public static final ReadWriteLens<WithPrimitive, Short> SH_READ_WRITE_LENS = new ReadWriteLens<WithPrimitive, Short>() {
        @Override
        public final Short get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getSh();
        }

        @Override
        public final void set(WithPrimitive object, Short property) {
            if (object == null) {
                return;
            }
            object.setSh(property);
        }
    };

    public static final ReadLens<WithPrimitive, Integer> I_READ_LENS = new ReadLens<WithPrimitive, Integer>() {
        @Override
        public final Integer get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getI();
        }
    };

    public static final ReadWriteLens<WithPrimitive, Integer> I_READ_WRITE_LENS = new ReadWriteLens<WithPrimitive, Integer>() {
        @Override
        public final Integer get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getI();
        }

        @Override
        public final void set(WithPrimitive object, Integer property) {
            if (object == null) {
                return;
            }
            object.setI(property);
        }
    };

    public static final ReadLens<WithPrimitive, Long> L_READ_LENS = new ReadLens<WithPrimitive, Long>() {
        @Override
        public final Long get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getL();
        }
    };

    public static final ReadWriteLens<WithPrimitive, Long> L_READ_WRITE_LENS = new ReadWriteLens<WithPrimitive, Long>() {
        @Override
        public final Long get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getL();
        }

        @Override
        public final void set(WithPrimitive object, Long property) {
            if (object == null) {
                return;
            }
            object.setL(property);
        }
    };

    public static final ReadLens<WithPrimitive, Float> F_READ_LENS = new ReadLens<WithPrimitive, Float>() {
        @Override
        public final Float get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getF();
        }
    };

    public static final ReadWriteLens<WithPrimitive, Float> F_READ_WRITE_LENS = new ReadWriteLens<WithPrimitive, Float>() {
        @Override
        public final Float get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getF();
        }

        @Override
        public final void set(WithPrimitive object, Float property) {
            if (object == null) {
                return;
            }
            object.setF(property);
        }
    };

    public static final ReadLens<WithPrimitive, Double> D_READ_LENS = new ReadLens<WithPrimitive, Double>() {
        @Override
        public final Double get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getD();
        }
    };

    public static final ReadWriteLens<WithPrimitive, Double> D_READ_WRITE_LENS = new ReadWriteLens<WithPrimitive, Double>() {
        @Override
        public final Double get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getD();
        }

        @Override
        public final void set(WithPrimitive object, Double property) {
            if (object == null) {
                return;
            }
            object.setD(property);
        }
    };

    public static final ReadLens<WithPrimitive, Character> CH_READ_LENS = new ReadLens<WithPrimitive, Character>() {
        @Override
        public final Character get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getCh();
        }
    };

    public static final ReadWriteLens<WithPrimitive, Character> CH_READ_WRITE_LENS = new ReadWriteLens<WithPrimitive, Character>() {
        @Override
        public final Character get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getCh();
        }

        @Override
        public final void set(WithPrimitive object, Character property) {
            if (object == null) {
                return;
            }
            object.setCh(property);
        }
    };

    public static final ReadLens<WithPrimitive, Boolean> BOOL_READ_LENS = new ReadLens<WithPrimitive, Boolean>() {
        @Override
        public final Boolean get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getBool();
        }
    };

    public static final ReadWriteLens<WithPrimitive, Boolean> BOOL_READ_WRITE_LENS = new ReadWriteLens<WithPrimitive, Boolean>() {
        @Override
        public final Boolean get(WithPrimitive object) {
            if (object == null) {
                return null;
            }
            return object.getBool();
        }

        @Override
        public final void set(WithPrimitive object, Boolean property) {
            if (object == null) {
                return;
            }
            object.setBool(property);
        }
    };

    private WithPrimitiveLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
