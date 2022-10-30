# Lens4j is a lightweight lens library for Java

[![Lens4j Maven](https://img.shields.io/maven-central/v/dev.khbd.lens4j/lens4j?color=brightgreen)](https://mvnrepository.com/artifact/dev.khbd.lens4j/lens4j)
[![CI](https://github.com/kh-bd/lens4j/actions/workflows/main-tests.yml/badge.svg)](https://github.com/kh-bd/lens4j/actions/workflows/main-tests.yml)

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
    <lens4j.version>${LATEST}</lens4j.version>
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

# Generating inlined lenses (experimental)

Lenses can be generated in different way, so called, inlined way.
Inlined generation is experimental and disabled by default.
To enable inlined generation set option `lenses.generate.inlined` to `true`.

For maven-based projects, add the following:

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
                <compilerArgs>
                    <compilerArg>-Alenses.generate.inlined=true</compilerArg>
                </compilerArgs>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Inlined lenses are look like manually written code, so instead of such code

```java
final class PaymentLenses {

    public static final ReadLens<Payment, String> PAYER_ACCOUNT_CODE_LENS =
            Lenses.readLens(Payment::getPayerAccount)
                    .andThen(Lenses.readLens(Accout::getCurrency))
                    .andThen(Lenses.readLens(Currency::getCode));
}
```

something like that will be generated:

```java

final class PaymentLenses {

    public static final ReadLens<Payment, String> PAYER_ACCOUNT_CODE_LENS = new ReadLens<>() {
        @Override
        String get(Payment object) {
            if (object == null) {
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
    };
}
```

See comparison between inlined and not-inlined lenses [here](https://jmh.morethan.io/?sources=https://raw.githubusercontent.com/kh-bd/lens4j/main/readme/benchmark/jmh_v_017_result.json,https://raw.githubusercontent.com/kh-bd/lens4j/main/readme/benchmark/jmh_latest_result.json).

# Intellij IDEA support

***

To add lens4j support to Intellij,
install [Lens4j intellij plugin](https://github.com/kh-bd/lens4j-intellij-plugin)

# Benchmarks

***

All benchmarks were run on:

- Machine: MacBook Pro 2015
- Processor: 2.2 GHz Quad-Core Intel Core i7
- Memory: 16 GB 1600MHz DDR3

See latest benchmark
result [here](https://jmh.morethan.io/?source=https://raw.githubusercontent.com/kh-bd/lens4j/main/readme/benchmark/jmh_latest_result.json)
.

As you can see, generated lenses are as fast as manually written code, but lenses which were build
manually with `Lenses.compose` api are several times slower than generated ones.
Lenses' performance is a subject for father optimization. Any help is welcome :)

## How to run benchmarks on your own machine?

***

To run benchmarks do several steps:

- pull project to your machine
- run from root directory `mvn package -Pbenchmark`
- go to `lens4j-benchmark/target` directory. `lens4j-benchmark-${version}-jar-with-dependencies.jar` should be generated
- run
  command `java -cp ./lens4j-benchmark-${version}-jar-with-dependencies.jar dev.khbd.lens4j.benchmark.BenchmarkRunner -rf json`
- `jmh-result.json` report should be generated
- view it through [jmh visualizer](https://jmh.morethan.io/)