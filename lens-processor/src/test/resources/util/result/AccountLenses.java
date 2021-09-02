package com.github.lens;

import com.github.lens.core.ReadLens;
import java.lang.String;
import javax.annotation.processing.Generated;
import util.Account;
import util.Currency;

@Generated("com.github.lens.processor.LensProcessor")
public final class Lenses {
    public static final ReadLens<Account, String> ACCOUNT_CUR_CODE_READ_LENS = com.github.lens.core.Lenses.readLens(Account::getCurrency)
            .andThen(com.github.lens.core.Lenses.readLens(Currency::getCode));

    public static final ReadLens<Account, String> ACCOUNT_CUR_CODE_READ_WRITE_LENS = com.github.lens.core.Lenses.readLens(Account::getCurrency)
            .andThen(com.github.lens.core.Lenses.readWriteLens(Currency::getCode, Currency::setCode));

    private Lenses() {
    }
}
