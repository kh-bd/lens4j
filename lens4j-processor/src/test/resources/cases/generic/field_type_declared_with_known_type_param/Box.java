package cases.generic.field_type_declared_with_known_type_param;

class Box<A> {
    private A value;

    public A getValue() {
        return value;
    }

    public void setValue(A value) {
        this.value = value;
    }
}