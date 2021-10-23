# Lens4j is a lightweight lens library for Java

# What is a lens?

***

To understand what lens is and where to use it, consider following example:  
Our domain contains several entities: `Payment`, `Account` and `Currency`.

```java
class Currency {
    String code;

    // accessors here
}

class Account {
    String accountNumber;
    Currency currency;

    // accessors here
}

class Payment {
    Double amount;
    Account payerAccount;
    Account receiverAccount;

    // accessors here
}
```

We have to implement a function to validate that payer account currency is not empty. A usual way to do that would look
like this:

```java
class PaymentValidator {

    boolean isPayerAccountCurrencyNotEmpty(Payment payment) {
        if (payment == null) {
            return false;
        }

        Account payerAccount = payment.getPayerAccount();
        if (payerAccount == null) {
            return false;
        }

        Currency currency = payerAccount.getCurrency();
        if (currency == null) {
            return false;
        }

        return currency.getCode() != null;
    }
}
```

Such implementation has several drawbacks:

* It is too easy to do it wrong. We have to check each property before dereference it.
* The biggest part of the method is boilerplate and only last line is a real logic.

Lens is a simple functional interface with method `get`, which can extract some value of type `P` from instance of
type `O`.

```java
interface ReadLens<O, P> {
    P get(O object);
}
```

Suppose we have a instance of type `ReadLens<Payment, String>` which encapsulate currency code extracting logic. Having
such lens instance we can reimplement our function. I will look like this:

```java
class PaymentValidator {

    static final ReadLens<Payment, String> PAYER_ACCOUNT_CODE_LENS = ...;

    boolean isPayerAccountCurrencyNotEmpty(Payment payment) {
        return PAYER_ACCOUNT_CODE_LENS.get(payment) != null;
    }
}
```

# How to construct an instance of lens?

***
We can construct lenses manually with combinator functions or use annotation processor to construct them at compile
time.

## Constructing lenses manually

There are several combinator functions to combine lenses with each other: `endThenF` and `composeF`. These functions
have analogical semantic as `Function#endThen` and `Function#compose`. To construct `PAYER_ACCOUNT_CODE_LENS` we can do
the following:

```java
class PaymentValidator {

    static final ReadLens<Payment, String> PAYER_ACCOUNT_CODE_LENS =
            Lenses.readLens(Payment::getPayerAccount)
                    .andThenF(Accout::getCurrency)
                    .andThenF(Currency::getCode);
}
```

Sometimes it is necessary to construct lenses by hand but most of the time we can do it automatically, at compile time.

## Constructing lenses at compile time

To construct the same lens instance we can annotate our `Payment` class with
`GenLenses` annotations.

```java

@GenLenses(lenses = @Lens(path = "payerAccount.currency.code"))
class Payment {
    Double amount;
    Account payerAccount;
    Account receiverAccount;
}
```

`PaymentLenses` factory class will be generated at compile time. It will look like this:

```java
final class PaymentLenses {

    public static final ReadLens<Payment, String> PAYER_ACCOUNT_CODE_LENS =
            Lenses.readLens(Payment::getPayerAccount)
                    .andThen(Lenses.readLens(Accout::getCurrency))
                    .andThen(Lenses.readLens(Currency::getCode));
}
```

Now, we can use `PaymentLenses#PAYER_ACCOUNT_CODE_LENS` in our code

```java
class PaymentValidator {

    boolean isPayerAccountCurrencyNotEmpty(Payment payment) {
        return PaymentLenses.PAYER_ACCOUNT_CODE_LENS.get(payment) != null;
    }
}
```

# Using lens4j

***

## Maven

For maven-based projects, add the following to your `pom.xml` file:

```xml
<!-- version property -->
<properties>
    <lens4j.version>0.1.4</lens4j.version>
</properties>
```

```xml
<!-- dependency for core api -->
<dependencies>
    <dependency>
        <groupId>dev.khbd.lens4j</groupId>
        <artifactId>lens4j-core</artifactId>
        <version>${lens4j.version}</version>
    </dependency>
</dependencies>
```

```xml
<!-- processor configuration -->
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <annotationProcessorPaths>
                    <path>
                        <groupId>dev.khbd.lens4j</groupId>
                        <artifactId>lens4j-processor</artifactId>
                        <version>${lens4j.version}</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```

# Intellij IDEA support

***

To add lens4j support to Intellij,
install [Lens4j intellij plugin](https://github.com/KhadanovichSergey/lens4j-intellij-plugin)

# How to run benchmarks?

***

To run benchmarks do several steps:

- pull project to your machine
- run from root directory `mvn package -Pbenchmark`
- go to `lens4j-benchmark/target` directory. `lens4j-benchmark-${version}-jar-with-dependencies.jar` should be generated
- run
  command `java -cp ./lens4j-benchmark-${version}-jar-with-dependencies.jar dev.khbd.lens4j.benchmark.BenchmarkRunner -rf json`
- `jmh-result.json` report should be generated
- view it through [jmh visualizer](https://jmh.morethan.io/)