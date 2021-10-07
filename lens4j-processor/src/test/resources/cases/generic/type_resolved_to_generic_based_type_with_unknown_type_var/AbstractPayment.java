package cases.generic.type_resolved_to_generic_based_type_with_unknown_type_var;

class AbstractPayment<F, T> {
    private F from;
    private T to;

    public F getFrom() {
        return from;
    }

    public void setFrom(F from) {
        this.from = from;
    }

    public T getTo() {
        return to;
    }

    public void setTo(T to) {
        this.to = to;
    }
}
