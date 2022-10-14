package dev.khbd.lens4j.benchmark.util;

import dev.khbd.lens4j.benchmark.domain.Account;
import dev.khbd.lens4j.benchmark.domain.Currency;
import dev.khbd.lens4j.benchmark.domain.Payment;
import dev.khbd.lens4j.benchmark.domain.PaymentLenses;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;

import java.util.Optional;

/**
 * @author Sergei_Khadanovich
 */
public final class PaymentUtil {

    private static final ReadLens<Payment, String> CODE_LENS =
            Lenses.readLens(Payment::getPayerAccount)
                    .andThenF(Account::getCurrency)
                    .andThenF(Currency::getCode);

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

    public static String generatedLensGet(Payment payment) {
        return PaymentLenses.CODE_LENS.get(payment);
    }

    public static String manualLensGet(Payment payment) {
        return CODE_LENS.get(payment);
    }
}
