package cases.sub_class;

import common.Currency;
import dev.khbd.lens4j.core.ReadLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class SubClassLenses {
    public static final ReadLens<SubClass, String> PUBLIC_CURRENCY_CODE_READ_LENS = new ReadLens<SubClass, String>() {
        @Override
        public final String get(SubClass object) {
            if (object == null) {
                return null;
            }
            Currency publicCurrency = object.publicCurrency;
            if (publicCurrency == null) {
                return null;
            }
            return publicCurrency.getCode();
        }
    };

    public static final ReadLens<SubClass, String> PACKAGE_CURRENCY_CODE_READ_LENS = new ReadLens<SubClass, String>() {
        @Override
        public final String get(SubClass object) {
            if (object == null) {
                return null;
            }
            Currency packageCurrency = object.packageCurrency;
            if (packageCurrency == null) {
                return null;
            }
            return packageCurrency.getCode();
        }
    };

    public static final ReadLens<SubClass, String> PROTECTED_CURRENCY_CODE_READ_LENS = new ReadLens<SubClass, String>() {
        @Override
        public final String get(SubClass object) {
            if (object == null) {
                return null;
            }
            Currency protectedCurrency = object.protectedCurrency;
            if (protectedCurrency == null) {
                return null;
            }
            return protectedCurrency.getCode();
        }
    };

    public static final ReadLens<SubClass, String> PRIVATE_CURRENCY_CODE_READ_LENS = new ReadLens<SubClass, String>() {
        @Override
        public final String get(SubClass object) {
            if (object == null) {
                return null;
            }
            Currency privateCurrency = object.getPrivateCurrency();
            if (privateCurrency == null) {
                return null;
            }
            return privateCurrency.getCode();
        }
    };

    private SubClassLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}
