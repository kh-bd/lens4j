package dev.khbd.lens4j.processor;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexey_Bodyak
 */
public class LensProcessorSuccessTest {

    @DataProvider
    public static Object[][] singleFileSuccessCases() {
        return new Object[][]{
                {"cases/factory_modifiers/public_factory/Account.java", "cases/factory_modifiers/public_factory/AccountLenses"},
                {"cases/factory_modifiers/package_factory/Account.java", "cases/factory_modifiers/package_factory/AccountLenses"},
                {"cases/method/generic/class_generics/Payment.java", "cases/method/generic/class_generics/PaymentLenses"},
                {"cases/method/found/Payment.java", "cases/method/found/PaymentLenses"},
                {"cases/array_type_at_the_end/WithArray.java", "cases/array_type_at_the_end/WithArrayLenses"},
                {"cases/array_length_support/Payment.java", "cases/array_length_support/PaymentLenses"},
                {"cases/primitive_type_at_the_end/WithPrimitive.java", "cases/primitive_type_at_the_end/WithPrimitiveLenses"},
                {"cases/single_property/Account.java", "cases/single_property/AccountLenses"},
                {"cases/multi_property/Payment.java", "cases/multi_property/PaymentLenses"},
                {"cases/specific_factory_name/AccountWithSpecificFactoryName.java", "cases/specific_factory_name/SpecificFactoryName"},
                {"cases/empty_lens_name/AccountWithEmptyLensName.java", "cases/empty_lens_name/AccountWithEmptyLensNameLenses"},
                {"cases/package_private_class/PackagePrivateAccount.java", "cases/package_private_class/PackagePrivateAccountLenses"},
                {"cases/inner_class/Outer.java", "cases/inner_class/OuterInner1Inner2Lenses"},
                {"cases/nested_class/Outer.java", "cases/nested_class/OuterInner1Inner2Lenses"},
                {"cases/decapitalize_factory_name/Account.java", "cases/decapitalize_factory_name/SpecificFactoryName"},
                {"cases/field_strategy/Payment.java", "cases/field_strategy/PaymentLenses"},
                {"cases/lens_modifiers/Account.java", "cases/lens_modifiers/AccountLenses"}
        };
    }

    @Test(dataProvider = "singleFileSuccessCases")
    public void generate_singleFile_generateValidFactory(String file, String factoryFile) {
        CompilationDescription.of()
                .withFile(file)
                .withCommons()
                .compile()
                .success()
                .generated(factoryFile, factoryFile + ".java");
    }


    @DataProvider
    public static Object[][] multiFileSuccessCases() {
        return new Object[][]{
                {
                        Arrays.asList(
                                "cases/generic/projection/From.java",
                                "cases/generic/projection/StrFrom.java",
                                "cases/generic/projection/AbstractPayment.java",
                                "cases/generic/projection/Payment.java"
                        ),
                        Collections.singletonList("cases/generic/projection/PaymentLenses")
                },
                {
                        Arrays.asList(
                                "cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/Box.java",
                                "cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/AbstractPayment.java",
                                "cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/BoxedToPayment.java",
                                "cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/Payments.java"
                        ),
                        Arrays.asList(
                                "cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/ArrayPaymentLenses",
                                "cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/GenPaymentLenses",
                                "cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/StrPaymentLenses"
                        )
                },
                {
                        Arrays.asList(
                                "cases/single_property/Account.java",
                                "cases/multi_property/Payment.java"
                        ),
                        Arrays.asList(
                                "cases/single_property/AccountLenses",
                                "cases/multi_property/PaymentLenses"
                        )
                },
                {
                        Arrays.asList(
                                "cases/generic/field_type_declared_with_unknown_type_param/Box.java",
                                "cases/generic/field_type_declared_with_unknown_type_param/AbstractPayment.java",
                                "cases/generic/field_type_declared_with_unknown_type_param/Payment.java"
                        ),
                        Collections.singletonList("cases/generic/field_type_declared_with_unknown_type_param/PaymentLenses")
                },
                {
                        Arrays.asList(
                                "cases/generic/field_type_declared_with_known_type_param/Box.java",
                                "cases/generic/field_type_declared_with_known_type_param/Payment.java"
                        ),
                        Collections.singletonList("cases/generic/field_type_declared_with_known_type_param/PaymentLenses")
                },
                {
                        Arrays.asList(
                                "cases/generic/type_resolved_to_generic_based_type/Box.java",
                                "cases/generic/type_resolved_to_generic_based_type/BoxedCurrency.java",
                                "cases/generic/type_resolved_to_generic_based_type/CurrencyPair.java",
                                "cases/generic/type_resolved_to_generic_based_type/Pair.java"
                        ),
                        Collections.singletonList("cases/generic/type_resolved_to_generic_based_type/CurrencyPairLenses")
                },
                {
                        Arrays.asList(
                                "cases/generic/first_sub_class_supplied_simple_type/Base.java",
                                "cases/generic/first_sub_class_supplied_simple_type/Child.java"
                        ),
                        Collections.singletonList("cases/generic/first_sub_class_supplied_simple_type/ChildLenses")
                },
                {
                        Arrays.asList(
                                "cases/generic/second_sub_class_supplied_simple_type/Base.java",
                                "cases/generic/second_sub_class_supplied_simple_type/FirstChild.java",
                                "cases/generic/second_sub_class_supplied_simple_type/SecondChild.java"
                        ),
                        Collections.singletonList("cases/generic/second_sub_class_supplied_simple_type/SecondChildLenses")
                },
                {
                        Arrays.asList(
                                "cases/sub_class/BaseClass.java",
                                "cases/sub_class/SubClass.java"
                        ),
                        Collections.singletonList("cases/sub_class/SubClassLenses")
                },
                {
                        Arrays.asList(
                                "cases/several_factories_to_same_class/success/Customer.java",
                                "cases/several_factories_to_same_class/success/Client.java"
                        ),
                        Collections.singletonList("cases/several_factories_to_same_class/success/FactoryImpl")
                },
                {
                        Arrays.asList(
                                "cases/field_strategy/different_package/other/Account.java",
                                "cases/field_strategy/different_package/Payment.java"
                        ),
                        Collections.singletonList("cases/field_strategy/different_package/PaymentLenses")
                }
        };
    }

    @Test(dataProvider = "multiFileSuccessCases")
    public void generate_multiFile_generateValidFactories(List<String> sources, List<String> expected) {
        CompilationResult result = CompilationDescription.of()
                .withCommons()
                .withFiles(sources.toArray(new String[0]))
                .compile();

        result.success();
        for (String factoryFile : expected) {
            result.generated(factoryFile, factoryFile + ".java");
        }
    }

}
