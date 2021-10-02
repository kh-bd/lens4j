package cases.sub_class;

import common.Currency;

public class BaseClass {
    Currency packageCurrency;
    public Currency publicCurrency;
    protected Currency protectedCurrency;
    private Currency privateCurrency;

    public Currency getPackageCurrency() {
        return packageCurrency;
    }

    public Currency getPublicCurrency() {
        return publicCurrency;
    }

    public Currency getProtectedCurrency() {
        return protectedCurrency;
    }

    public Currency getPrivateCurrency() {
        return privateCurrency;
    }
}
