package dev.khbd.lens4j.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class CombinedReadLensTest {

    private static final ReadLens<Account, String> ACCOUNT_CUR_CODE_LENS =
            Lenses.readLens(Account::getCurrency)
                    .andThen(Lenses.readLens(Currency::getCode));

    @Test
    public void get_accountIsNull_returnNull() {
        String code = ACCOUNT_CUR_CODE_LENS.get(null);

        assertThat(code).isNull();
    }

    @Test
    public void get_currencyIsNull_returnNull() {
        String code = ACCOUNT_CUR_CODE_LENS.get(new Account());

        assertThat(code).isNull();
    }

    @Test
    public void get_currencyCodeIsNull_returnNull() {
        String code = ACCOUNT_CUR_CODE_LENS.get(new Account().setCurrency(new Currency()));

        assertThat(code).isNull();
    }

    @Test
    public void get_currencyCodeIsNotNull_returnIt() {
        String code = ACCOUNT_CUR_CODE_LENS.get(new Account().setCurrency(new Currency().setCode("code")));

        assertThat(code).isEqualTo("code");
    }

    static class Account {
        Currency currency;

        public Account() {
        }

        public Currency getCurrency() {
            return currency;
        }

        public Account setCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }
    }

    static class Currency {
        String code;

        public Currency() {
        }

        public String getCode() {
            return code;
        }

        public Currency setCode(String code) {
            this.code = code;
            return this;
        }
    }
}
