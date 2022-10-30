package dev.khbd.lens4j.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

/**
 * @author Alexey_Bodyak
 */
public class LensProcessorTest {

    @Test
    public void generate_classIsPackageButFactoryMarkedAsPublic_generateValidFactory() {
        CompilationDescription.of()
                .withFile("cases/factory_modifiers/public_factory/Account.java")
                .compile()
                .success()
                .generated("cases/factory_modifiers/public_factory/AccountLenses", "cases/factory_modifiers/public_factory/AccountLenses.java");
    }

    @Test
    public void generate_classIsPublicButFactoryMarkedAsPackage_generateValidFactory() {
        CompilationDescription.of()
                .withFile("cases/factory_modifiers/package_factory/Account.java")
                .compile()
                .success()
                .generated("cases/factory_modifiers/package_factory/AccountLenses", "cases/factory_modifiers/package_factory/AccountLenses.java");
    }

    @Test
    public void generate_parametrizedMethodIsUsed_compilationFailed() {
        CompilationDescription.of()
                .withFile("cases/method/generic/method_generics/Payment.java")
                .compile()
                .failed("Parametrized methods are not allowed");
    }

    @Test
    public void generate_methodReturnTypeIsGenericAndGenericsFromClass_generateValidFactory() {
        CompilationDescription.of()
                .withFile("cases/method/generic/class_generics/Payment.java")
                .compile()
                .success()
                .generated("cases/method/generic/class_generics/PaymentLenses", "cases/method/generic/class_generics/PaymentLenses.java");
    }

    @Test(dataProvider = "methodExistsButNotApplicableProvider")
    public void generate_methodExistButNotApplicable_compileError(String path) {
        CompilationDescription.of()
                .withFile(path)
                .compile()
                .failed("Method 'payer' was not found in class 'Payment'");
    }

    @DataProvider
    public static Object[][] methodExistsButNotApplicableProvider() {
        return new Object[][]{
                {"cases/method/not_found/_static/Payment.java"},
                {"cases/method/not_found/not_visible/Payment.java"},
                {"cases/method/not_found/return_void/Payment.java"},
                {"cases/method/not_found/with_arguments/Payment.java"}
        };
    }

    @Test
    public void generate_methodUsedCorrectly_generateValidFactory() {
        CompilationDescription.of()
                .withFile("cases/method/found/Payment.java")
                .withCommons()
                .compile()
                .success()
                .generated("cases/method/found/PaymentLenses", "cases/method/found/PaymentLenses.java");
    }

    @Test
    public void generate_arrayLengthIsSupported_generateValidFactory() {
        CompilationDescription.of()
                .withFile("cases/array_length_support/Payment.java")
                .withCommons()
                .compile()
                .success()
                .generated("cases/array_length_support/PaymentLenses");
    }

    @Test
    public void generate_notSupportedArrayProperty_compilationError() {
        CompilationDescription.of()
                .withFile("cases/array_without_supported_property/Payment.java")
                .compile()
                .failed("Arrays property 'length1' is not supported");
    }

    @Test
    public void generate_notSupportedPropertyAfterLengthArrayProperty_compilationError() {
        CompilationDescription.of()
                .withFile("cases/array_without_supported_property/PaymentWithPropertyAfterLength.java")
                .compile()
                .failed("Non-declared types are allowed only at last position in path");
    }

    @Test
    public void generate_methodAtFirstPositionInSinglePartLens_compilationError() {
        CompilationDescription.of()
                .withFile("cases/method/wrong_position/single_part_path/Payment.java")
                .compile()
                .failed("Methods are not allowed at last position of read-write lenses");
    }

    @Test
    public void generate_userOverrideLensModifiers_generateValidFactory() {
        CompilationDescription.of()
                .withFiles(
                        "cases/lens_modifiers/Account.java",
                        "common/Currency.java"
                )
                .compile()
                .success()
                .generated("cases/lens_modifiers/AccountLenses");
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
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/generic/field_type_declared_with_unknown_type_param/Box.java"),
                                JavaFileObjects.forResource("cases/generic/field_type_declared_with_unknown_type_param/AbstractPayment.java"),
                                JavaFileObjects.forResource("cases/generic/field_type_declared_with_unknown_type_param/Payment.java"),
                                JavaFileObjects.forResource("common/Currency.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/generic/field_type_declared_with_unknown_type_param/PaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/generic/field_type_declared_with_unknown_type_param/PaymentLenses.java"));
    }

    @Test
    public void generate_fieldTypeIsDeclaredWithKnownTypeVar_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/generic/field_type_declared_with_known_type_param/Box.java"),
                                JavaFileObjects.forResource("cases/generic/field_type_declared_with_known_type_param/Payment.java"),
                                JavaFileObjects.forResource("common/Currency.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/generic/field_type_declared_with_known_type_param/PaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/generic/field_type_declared_with_known_type_param/PaymentLenses.java"));
    }

    @Test
    public void generate_genericResolvedToGenericBasedType_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/generic/type_resolved_to_generic_based_type/Box.java"),
                                JavaFileObjects.forResource("cases/generic/type_resolved_to_generic_based_type/BoxedCurrency.java"),
                                JavaFileObjects.forResource("cases/generic/type_resolved_to_generic_based_type/CurrencyPair.java"),
                                JavaFileObjects.forResource("cases/generic/type_resolved_to_generic_based_type/Pair.java"),
                                JavaFileObjects.forResource("common/Currency.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/generic/type_resolved_to_generic_based_type/CurrencyPairLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/generic/type_resolved_to_generic_based_type/CurrencyPairLenses.java"));
    }

    @Test
    public void generate_childResolveGenericWithSimpleClass_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/generic/first_sub_class_supplied_simple_type/Base.java"),
                                JavaFileObjects.forResource("cases/generic/first_sub_class_supplied_simple_type/Child.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/generic/first_sub_class_supplied_simple_type/ChildLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/generic/first_sub_class_supplied_simple_type/ChildLenses.java"));
    }

    @Test
    public void generate_secondChildResolveGenericWithSimpleClass_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/generic/second_sub_class_supplied_simple_type/Base.java"),
                                JavaFileObjects.forResource("cases/generic/second_sub_class_supplied_simple_type/FirstChild.java"),
                                JavaFileObjects.forResource("cases/generic/second_sub_class_supplied_simple_type/SecondChild.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/generic/second_sub_class_supplied_simple_type/SecondChildLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/generic/second_sub_class_supplied_simple_type/SecondChildLenses.java"));
    }

    @Test
    public void generate_lensPathContainsArrayType_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("cases/array_type_at_the_end/WithArray.java"));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/array_type_at_the_end/WithArrayLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/array_type_at_the_end/WithArrayLenses.java"));
    }

    @Test
    public void generate_lensPathContainsPrimitiveType_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("cases/primitive_type_at_the_end/WithPrimitive.java"));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/primitive_type_at_the_end/WithPrimitiveLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/primitive_type_at_the_end/WithPrimitiveLenses.java"));
    }

    @Test
    public void generate_lensPathHasPrimitiveTypeInTheMiddle_compilationError() {
        CompilationDescription.of()
                .withFile("cases/primitive_type_in_the_middle/WithPrimitive.java")
                .compile()
                .failed("Non-declared types are allowed only at last position in path");
    }

    @Test
    public void generate_lensesWithSinglePropertyPath_generateValidFactory() {
        CompilationDescription.of()
                .withFile("cases/single_property/Account.java")
                .withCommons()
                .compile()
                .success()
                .generated("cases/single_property/AccountLenses");
    }

    @Test
    public void generate_classWithTwoGenReadAnnotations_generateValidPaymentFactory() {
        CompilationDescription.of()
                .withFile("cases/multi_property/Payment.java")
                .withCommons()
                .compile()
                .success()
                .generated("cases/multi_property/PaymentLenses");
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

    @Test
    public void generate_annotationWithSpecificFactoryName_generateValidSpecificFactory() {
        CompilationDescription.of()
                .withFile("cases/specific_factory_name/AccountWithSpecificFactoryName.java")
                .withCommons()
                .compile()
                .success()
                .generated("cases/specific_factory_name/SpecificFactoryName");
    }

    @Test
    public void generate_annotationWithDeCapitalizeSpecificFactoryName_generateValidSpecificFactory() {
        CompilationDescription.of()
                .withFile("cases/decapitalize_factory_name/AccountWithDeCapitalizeSpecificFactoryName.java")
                .withCommons()
                .compile()
                .success()
                .generated("cases/decapitalize_factory_name/SpecificFactoryName", "cases/decapitalize_factory_name/CapitalizeSpecificFactoryName.java");
    }

    @Test
    public void generate_annotationWithEmptyLensName_generateValidSpecificFactory() {
        CompilationDescription.of()
                .withFile("cases/empty_lens_name/AccountWithEmptyLensName.java")
                .withCommons()
                .compile()
                .success()
                .generated("cases/empty_lens_name/AccountWithEmptyLensNameLenses");
    }

    @Test
    public void generate_annotationFromPackagePrivateClass_generateValidPackagePrivateFactory() {
        CompilationDescription.of()
                .withFile("cases/package_private_class/PackagePrivateAccount.java")
                .withCommons()
                .compile()
                .success()
                .generated("cases/package_private_class/PackagePrivateAccountLenses");
    }

    @Test
    public void generate_lensPathContainsPropertyFromBaseClass_generateValidFactory() {
        CompilationDescription.of()
                .withFile("cases/sub_class/SubClass.java")
                .withCommons()
                .compile()
                .success()
                .generated("cases/sub_class/SubClassLenses");
    }

    @Test
    public void generate_typeHasNotUniqueLensNames_compilationError() {
        CompilationDescription.of()
                .withFile("cases/duplicate_lens_names/DuplicateNames.java")
                .compile()
                .failed("Lens names for type should be unique");
    }

    @Test
    public void generate_lensPathIsEmpty_compilationError() {
        CompilationDescription.of()
                .withFile("cases/lens_with_empty_path/WithEmptyPath.java")
                .compile()
                .failed("Lens path is incorrect");
    }

    @Test
    public void generate_annotationOnInterface_compilationError() {
        CompilationDescription.of()
                .withFile("cases/on_interface/LensOnInterface.java")
                .compile()
                .failed("@GenLenses is not allowed here");
    }

    @Test
    public void generate_annotationWithoutLensPath_compilationError() {
        CompilationDescription.of()
                .withFile("cases/lens_without_path/AnnotationWithoutLensPath.java")
                .compile()
                .failed("annotation @dev.khbd.lens4j.core.annotations.Lens is missing a default value for the element 'path'");
    }

    @Test
    public void generate_pathContainsWrongFieldName_compilationError() {
        CompilationDescription.of()
                .withFile("cases/field_not_found/FieldNotFound.java")
                .compile()
                .failed("Field 'bid' was not found in class 'Currency'");
    }

    @Test
    public void generate_innerClassAnnotatedGenLenses_generateFactory() {
        CompilationDescription.of()
                .withFile("cases/inner_class/Outer.java")
                .withCommons()
                .compile()
                .success()
                .generated("cases/inner_class/OuterInner1Inner2Lenses", "cases/inner_class/OuterInner1Inner2Lenses.java");
    }

    @Test
    public void generate_nessClassAnnotatedGenLenses_generateFactory() {
        CompilationDescription.of()
                .withFile("cases/nested_class/Outer.java")
                .withCommons()
                .compile()
                .success()
                .generated("cases/nested_class/OuterInner1Inner2Lenses", "cases/nested_class/OuterInner1Inner2Lenses.java");
    }

    @Test
    public void generate_classIsGeneric_compilationError() {
        CompilationDescription.of()
                .withFile("cases/generic/generic_class/GenericClass.java")
                .compile()
                .failed("@GenLenses is not allowed on generic classes");
    }

    @Test
    public void generate_someFieldsAreNotPrivate_generateValidFactory() {
        CompilationDescription.of()
                .withFile("cases/field_strategy/Payment.java")
                .compile()
                .success()
                .generated("cases/field_strategy/PaymentLenses");
    }
}
