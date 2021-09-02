package util;

import com.github.lens.core.annotations.GenReadLens;

@GenReadLens(factoryName = "FactoryName1", lensName = "CUR_CODE_LENS", path = "currency.code")
@GenReadLens(factoryName = "FactoryName2", lensName = "CUR_ID_LENS", path = "currency.id")
public class TypeWithDifferentFactoryNames {
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