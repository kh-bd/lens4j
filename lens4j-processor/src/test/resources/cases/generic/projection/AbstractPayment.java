package cases.generic.projection;

class AbstractPayment<V, F extends From<V>> {

    private F from;

    public void setFrom(F from) {
        this.from = from;
    }

    public F getFrom() {
        return from;
    }
}
