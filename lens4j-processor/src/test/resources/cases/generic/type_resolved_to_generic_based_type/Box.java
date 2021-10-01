package cases.generic.type_resolved_to_generic_based_type;

class Box<A> {
    private A value;

    public A getValue() {
        return value;
    }

    public void setValue(A value) {
        this.value = value;
    }
}