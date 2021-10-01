package cases.generic.type_resolved_to_generic_based_type;

class Pair<A, B> {
    private A left;
    private B right;

    public A getLeft() {
        return left;
    }

    public void setLeft(A left) {
        this.left = left;
    }

    public B getRight() {
        return right;
    }

    public void setRight(B right) {
        this.right = right;
    }

}