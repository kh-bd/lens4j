package util;

import com.github.lens.core.annotations.GenLenses;
import com.github.lens.core.annotations.Lens;

@GenLenses(lenses = @Lens(lensName = "LENS_NAME"))
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