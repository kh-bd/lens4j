package cases.generic.type_resolved_to_generic_based_type_with_unknown_type_var;

class Box<V> {
    V value;

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
