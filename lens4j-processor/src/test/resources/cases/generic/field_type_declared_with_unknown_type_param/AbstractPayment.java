package cases.generic.field_type_declared_with_unknown_type_param;

public class AbstractPayment<V> {

    private Box<V> boxed;
    private Box<Box<V>> boxedBoxed;

    public Box<V> getBoxed() {
        return boxed;
    }

    public void setBoxed(Box<V> boxed) {
        this.boxed = boxed;
    }

    public Box<Box<V>> getBoxedBoxed() {
        return boxedBoxed;
    }

    public void setBoxedBoxed(Box<Box<V>> boxedBoxed) {
        this.boxedBoxed = boxedBoxed;
    }

}
