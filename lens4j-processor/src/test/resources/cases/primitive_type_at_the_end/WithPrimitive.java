package cases.primitive_type_at_the_end;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(
        lenses = {
                @Lens(path = "b"),
                @Lens(path = "b", type = LensType.READ_WRITE),
                @Lens(path = "sh"),
                @Lens(path = "sh", type = LensType.READ_WRITE),
                @Lens(path = "i"),
                @Lens(path = "i", type = LensType.READ_WRITE),
                @Lens(path = "l"),
                @Lens(path = "l", type = LensType.READ_WRITE),
                @Lens(path = "f"),
                @Lens(path = "f", type = LensType.READ_WRITE),
                @Lens(path = "d"),
                @Lens(path = "d", type = LensType.READ_WRITE),
                @Lens(path = "ch"),
                @Lens(path = "ch", type = LensType.READ_WRITE),
                @Lens(path = "bool"),
                @Lens(path = "bool", type = LensType.READ_WRITE),
        }
)
public class WithPrimitive {
    byte b;
    short sh;
    int i;
    long l;
    float f;
    double d;
    char ch;
    boolean bool;

    void setI(int i) {
        this.i= i;
    }

    int getI() {
        return i;
    }

    byte getB() {
        return b;
    }

    void setB(byte b) {
        this.b = b;
    }

    short getSh() {
        return sh;
    }

    void setSh(short sh) {
        this.sh = sh;
    }

    long getL() {
        return l;
    }

    void setL(long l) {
        this.l = l;
    }

    float getF() {
        return f;
    }

    void setF(float f) {
        this.f = f;
    }

    double getD() {
        return d;
    }

    void setD(double d) {
        this.d = d;
    }

    char getCh() {
        return ch;
    }

    void setCh(char ch) {
        this.ch = ch;
    }

    boolean getBool() {
        return bool;
    }

    void setBool(boolean bool) {
        this.bool = bool;
    }
}
