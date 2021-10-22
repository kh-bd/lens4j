package dev.khbd.lens4j.benchmark.util;

import dev.khbd.lens4j.benchmark.domain.Account;
import dev.khbd.lens4j.benchmark.domain.Currency;
import dev.khbd.lens4j.benchmark.domain.Payment;
import dev.khbd.lens4j.benchmark.domain.PaymentLenses;

import java.util.Optional;

/**
 * @author Sergei_Khadanovich
 */
public final class PaymentUtil {

    public static String manualGet(Payment payment) {
        if (payment == null) {
            return null;
        }
        Account payerAccount = payment.getPayerAccount();
        if (payerAccount == null) {
            return null;
        }
        Currency currency = payerAccount.getCurrency();
        if (currency == null) {
            return null;
        }
        return currency.getCode();
    }

    public static String optionalGet(Payment payment) {
        return Optional.ofNullable(payment)
                .map(Payment::getPayerAccount)
                .map(Account::getCurrency)
                .map(Currency::getCode)
                .orElse(null);
    }

    public static String lensGet(Payment payment) {
        return PaymentLenses.CODE_LENS.get(payment);
    }
}
