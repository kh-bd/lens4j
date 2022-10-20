package dev.khbd.lens4j.processor;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey_Bodyak
 */
public class LensProcessorTest {

    @Test
    public void generate_classIsRecordAndMethodAccessed_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/record/method/Account.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/record/method/AccountLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/record/method/AccountLenses.java"));
    }

    @Test
    public void generate_classIsRecordAndFieldAccessed_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/record/field/Account.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/record/field/AccountLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/record/field/AccountLenses.java"));
    }

    @Test
    public void generate_classIsPackageButFactoryMarkedAsPublic_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/factory_modifiers/public_factory/Account.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/factory_modifiers/public_factory/AccountLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/factory_modifiers/public_factory/AccountLenses.java"));
    }

    @Test
    public void generate_classIsPublicButFactoryMarkedAsPackage_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/factory_modifiers/package_factory/Account.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/factory_modifiers/package_factory/AccountLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/factory_modifiers/package_factory/AccountLenses.java"));
    }

    @Test
    public void generate_parametrizedMethodIsUsed_compilationFailed() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/method/generic/method_generics/Payment.java")
                        ));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("Parametrized methods are not allowed");
    }

    @Test
    public void generate_methodReturnTypeIsGenericAndGenericsFromClass_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/method/generic/class_generics/Payment.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/method/generic/class_generics/PaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/method/generic/class_generics/PaymentLenses.java"));
    }

    @Test(dataProvider = "methodExistsButNotApplicableProvider")
    public void generate_methodExistButNotApplicable_compileError(String path) {
        JavaFileObject fileObject = JavaFileObjects.forResource(path);
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("Method 'payer' was not found in class 'Payment'");

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
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(
                                JavaFileObjects.forResource("cases/method/found/Payment.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/method/found/PaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/method/found/PaymentLenses.java"));
    }

    @Test
    public void generate_arrayLengthIsSupported_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(
                                JavaFileObjects.forResource("cases/array_length_support/Payment.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/array_length_support/PaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/array_length_support/PaymentLenses.java"));
    }

    @Test
    public void generate_notSupportedArrayProperty_compilationError() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(
                                JavaFileObjects.forResource("cases/array_without_supported_property/Payment.java")
                        ));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("Arrays property 'length1' is not supported");
    }

    @Test
    public void generate_notSupportedPropertyAfterLengthArrayProperty_compilationError() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(
                                JavaFileObjects.forResource("cases/array_without_supported_property/PaymentWithPropertyAfterLength.java")
                        ));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("Non-declared types are allowed only at last position in path");
    }

    @Test
    public void generate_methodAtFirstPositionInSinglePartLens_compilationError() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/method/wrong_position/single_part_path/Payment.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("Methods are not allowed at last position of read-write lenses");
    }

    @Test
    public void generate_userOverrideLensModifiers_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/lens_modifiers/Account.java"),
                                JavaFileObjects.forResource("common/Currency.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/lens_modifiers/AccountLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/lens_modifiers/AccountLenses.java"));
    }

    @Test
    public void generate_genericHasProjectionAtDeclarationSite_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/generic/projection/From.java"),
                                JavaFileObjects.forResource("cases/generic/projection/StrFrom.java"),
                                JavaFileObjects.forResource("cases/generic/projection/AbstractPayment.java"),
                                JavaFileObjects.forResource("cases/generic/projection/Payment.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/generic/projection/PaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/generic/projection/PaymentLenses.java"));
    }

    @Test
    public void generate_resolvedTypeIsDeclaredWithUnKnownTypeVar_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(List.of(
                                JavaFileObjects.forResource("cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/Box.java"),
                                JavaFileObjects.forResource("cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/AbstractPayment.java"),
                                JavaFileObjects.forResource("cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/BoxedToPayment.java"),
                                JavaFileObjects.forResource("cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/Payments.java")
                        ));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/StrPaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/StrPaymentLenses.java"));
        assertThat(compilation)
                .generatedSourceFile("cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/ArrayPaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/ArrayPaymentLenses.java"));
        assertThat(compilation)
                .generatedSourceFile("cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/GenPaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/generic/type_resolved_to_generic_based_type_with_unknown_type_var/GenPaymentLenses.java"));
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
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/primitive_type_in_the_middle/WithPrimitive.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(fileObject);

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("Non-declared types are allowed only at last position in path");
    }

    @Test
    public void generate_lensesWithSinglePropertyPath_generateValidFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(JavaFileObjects.forResource("cases/single_property/Account.java")));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/single_property/AccountLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/single_property/AccountLenses.java"));
    }

    @Test
    public void generate_classWithTwoGenReadAnnotations_generateValidPaymentFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(JavaFileObjects.forResource("cases/multi_property/Payment.java")));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/multi_property/PaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/multi_property/PaymentLenses.java"));
    }

    @Test
    public void generate_twoClassesWithGenLensesAnnotations_generateValidFactories() {
        JavaFileObject accountFile = JavaFileObjects.forResource("cases/single_property/Account.java");
        JavaFileObject paymentFile = JavaFileObjects.forResource("cases/multi_property/Payment.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(accountFile, paymentFile));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/single_property/AccountLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/single_property/AccountLenses.java"));
        assertThat(compilation)
                .generatedSourceFile("cases/multi_property/PaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/multi_property/PaymentLenses.java"));
    }

    @Test
    public void generate_annotationWithSpecificFactoryName_generateValidSpecificFactory() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/specific_factory_name/AccountWithSpecificFactoryName.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/specific_factory_name/SpecificFactoryName")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/specific_factory_name/SpecificFactoryName.java"));
    }

    @Test
    public void generate_annotationWithDeCapitalizeSpecificFactoryName_generateValidSpecificFactory() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/decapitalize_factory_name/AccountWithDeCapitalizeSpecificFactoryName.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/decapitalize_factory_name/SpecificFactoryName")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/decapitalize_factory_name/CapitalizeSpecificFactoryName.java"));
    }

    @Test
    public void generate_annotationWithEmptyLensName_generateValidSpecificFactory() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/empty_lens_name/AccountWithEmptyLensName.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/empty_lens_name/AccountWithEmptyLensNameLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/empty_lens_name/AccountWithEmptyLensNameLenses.java"));
    }

    @Test
    public void generate_annotationFromPackagePrivateClass_generateValidPackagePrivateFactory() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/package_private_class/PackagePrivateAccount.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/package_private_class/PackagePrivateAccountLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/package_private_class/PackagePrivateAccountLenses.java"));
    }

    @Test
    public void generate_lensPathContainsPropertyFromBaseClass_generateValidFactory() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/sub_class/SubClass.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/sub_class/SubClassLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/sub_class/SubClassLenses.java"));
    }

    @Test
    public void generate_typeHasNotUniqueLensNames_compilationError() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/duplicate_lens_names/DuplicateNames.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("Lens names for type should be unique");
    }

    @Test
    public void generate_lensPathIsEmpty_compilationError() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/lens_with_empty_path/WithEmptyPath.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("Lens path is incorrect");
    }

    @Test
    public void generate_annotationOnInterface_compilationError() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/on_interface/LensOnInterface.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("@GenLenses is not allowed here");
    }

    @Test
    public void generate_annotationWithoutLensPath_compilationError() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/lens_without_path/AnnotationWithoutLensPath.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("annotation @dev.khbd.lens4j.core.annotations.Lens is missing a default value for the element 'path'");
    }

    @Test
    public void generate_pathContainsWrongFieldName_compilationError() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/field_not_found/FieldNotFound.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("Field 'bid' was not found in class 'Currency'");
    }

    @Test
    public void generate_innerClassAnnotatedGenLenses_generateFactory() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/inner_class/Outer.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/inner_class/OuterInner1Inner2Lenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/inner_class/OuterInner1Inner2Lenses.java"));
    }

    @Test
    public void generate_nessClassAnnotatedGenLenses_generateFactory() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/nested_class/Outer.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/nested_class/OuterInner1Inner2Lenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/nested_class/OuterInner1Inner2Lenses.java"));
    }

    @Test
    public void generate_classIsGeneric_compilationError() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/generic/generic_class/GenericClass.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(fileObject);

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("@GenLenses is not allowed on generic classes");
    }

    @Test
    public void generate_someFieldsAreNotPrivate_generateValidFactory() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/field_strategy/Payment.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(fileObject);

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("cases/field_strategy/PaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("cases/field_strategy/PaymentLenses.java"));
    }

    private List<JavaFileObject> withPathObjects(JavaFileObject... objects) {
        List<JavaFileObject> result = new ArrayList<>(List.of(objects));
        result.addAll(
                List.of(
                        JavaFileObjects.forResource("common/Bank.java"),
                        JavaFileObjects.forResource("common/Currency.java"),
                        JavaFileObjects.forResource("common/Payer.java"),
                        JavaFileObjects.forResource("common/Receiver.java")
                )
        );
        return result;
    }
}
