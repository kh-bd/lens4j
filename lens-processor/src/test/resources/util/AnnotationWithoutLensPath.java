package util;

import com.github.lens.core.annotations.GenReadLens;

@GenReadLens(lensName = "LENS_NAME")
public class AnnotationWithoutLensPath {
    private String id;
    private Currency currency;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }
}