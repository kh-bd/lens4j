package dev.khbd.lens4j.processor;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Alexey_Bodyak
 */
public class LensProcessorSuccessTest {

    @DataProvider
    public static Object[][] simpleSuccessCases() {
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
                {"cases/sub_class/SubClass.java", "cases/sub_class/SubClassLenses"},
                {"cases/inner_class/Outer.java", "cases/inner_class/OuterInner1Inner2Lenses"},
                {"cases/nested_class/Outer.java", "cases/nested_class/OuterInner1Inner2Lenses"},
                {"cases/decapitalize_factory_name/Account.java", "cases/decapitalize_factory_name/SpecificFactoryName"},
                {"cases/field_strategy/Payment.java", "cases/field_strategy/PaymentLenses"},
                {"cases/lens_modifiers/Account.java", "cases/lens_modifiers/AccountLenses"}
        };
    }

    @Test(dataProvider = "simpleSuccessCases")
    public void generate_allIsOk_generateValidFactory(String file, String factoryFile) {
        CompilationDescription.of()
                .withFile(file)
                .withCommons()
                .compile()
                .success()
                .generated(factoryFile);
    }

    @DataProvider
    public static Object[][] inlinedSimpleSuccessCases() {
        return new Object[][]{
                {"cases/factory_modifiers/public_factory/Account.java", "cases/factory_modifiers/public_factory/AccountLenses", "cases/factory_modifiers/public_factory/AccountLensesInlined.java"},
                {"cases/factory_modifiers/package_factory/Account.java", "cases/factory_modifiers/package_factory/AccountLenses", "cases/factory_modifiers/package_factory/AccountLensesInlined.java"}
        };
    }

    @Test(dataProvider = "inlinedSimpleSuccessCases")
    public void generate_allIsOkAndInlinedFlagIsOn_generateValidFactory(String file, String factoryFile, String factoryPath) {
        CompilationDescription.of()
                .withFile(file)
                .withCommons()
                .withInlinedOption()
                .compile()
                .success()
                .generated(factoryFile, factoryPath);
    }

    @Test
    public void generate_genericHasProjectionAtDeclarationSite_generateValidFactory() {
        CompilationDescription.of()
                .withFiles(
                        "cases/generic/projection/From.java",
                        "cases/generic/projection/StrFrom.java",
                        "cases/generic/projection/AbstractPayment.java",
                        "cases/generic/projection/Payment.java"
                )
                .compile()
                .success()
                .generated("cases/generic/projection/PaymentLenses");
    }

    @Test
    public void generate_resolvedTypeIsDeclaredWithUnKnownTypeVar_generateValidFactory() {
        CompilationDescription.of()
                .withFiles(
                        "cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/Box.java",
                        "cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/AbstractPayment.java",
                        "cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/BoxedToPayment.java",
                        "cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/Payments.java"
                )
                .compile()
                .success()
                .generated("cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/ArrayPaymentLenses")
                .generated("cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/GenPaymentLenses")
                .generated("cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/StrPaymentLenses");
    }

    @Test
    public void generate_fieldTypeIsDeclaredWithUnKnownTypeVar_generateValidFactory() {
        CompilationDescription.of()
                .withFiles(
                        "cases/generic/field_type_declared_with_unknown_type_param/Box.java",
                        "cases/generic/field_type_declared_with_unknown_type_param/AbstractPayment.java",
                        "cases/generic/field_type_declared_with_unknown_type_param/Payment.java",
                        "common/Currency.java"
                )
                .compile()
                .success()
                .generated("cases/generic/field_type_declared_with_unknown_type_param/PaymentLenses");
    }

    @Test
    public void generate_fieldTypeIsDeclaredWithKnownTypeVar_generateValidFactory() {
        CompilationDescription.of()
                .withFiles(
                        "cases/generic/field_type_declared_with_known_type_param/Box.java",
                        "cases/generic/field_type_declared_with_known_type_param/Payment.java",
                        "common/Currency.java"
                )
                .compile()
                .success()
                .generated("cases/generic/field_type_declared_with_known_type_param/PaymentLenses");
    }

    @Test
    public void generate_genericResolvedToGenericBasedType_generateValidFactory() {
        CompilationDescription.of()
                .withFiles(
                        "cases/generic/type_resolved_to_generic_based_type/Box.java",
                        "cases/generic/type_resolved_to_generic_based_type/BoxedCurrency.java",
                        "cases/generic/type_resolved_to_generic_based_type/CurrencyPair.java",
                        "cases/generic/type_resolved_to_generic_based_type/Pair.java",
                        "common/Currency.java"
                )
                .compile()
                .success()
                .generated("cases/generic/type_resolved_to_generic_based_type/CurrencyPairLenses");
    }

    @Test
    public void generate_childResolveGenericWithSimpleClass_generateValidFactory() {
        CompilationDescription.of()
                .withFiles(
                        "cases/generic/first_sub_class_supplied_simple_type/Base.java",
                        "cases/generic/first_sub_class_supplied_simple_type/Child.java"
                )
                .compile()
                .success()
                .generated("cases/generic/first_sub_class_supplied_simple_type/ChildLenses");
    }

    @Test
    public void generate_secondChildResolveGenericWithSimpleClass_generateValidFactory() {
        CompilationDescription.of()
                .withFiles(
                        "cases/generic/second_sub_class_supplied_simple_type/Base.java",
                        "cases/generic/second_sub_class_supplied_simple_type/FirstChild.java",
                        "cases/generic/second_sub_class_supplied_simple_type/SecondChild.java"
                )
                .compile()
                .success()
                .generated("cases/generic/second_sub_class_supplied_simple_type/SecondChildLenses");
    }

    @Test
    public void generate_twoClassesWithGenLensesAnnotations_generateValidFactories() {
        CompilationDescription.of()
                .withFile("cases/single_property/Account.java")
                .withFile("cases/multi_property/Payment.java")
                .withCommons()
                .compile()
                .success()
                .generated("cases/single_property/AccountLenses")
                .generated("cases/multi_property/PaymentLenses");
    }

}
