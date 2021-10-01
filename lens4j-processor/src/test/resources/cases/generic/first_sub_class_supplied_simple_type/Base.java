package cases.generic.first_sub_class_supplied_simple_type;

class Base<A> {
    private A value;

    public void setValue(A value) {
        this.value = value;
    }

    public A getValue() {
        return value;
    }
}