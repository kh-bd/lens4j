package dev.khbd.lens4j.processor;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.testng.annotations.Test;

import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey_Bodyak
 */
public class LensProcessorTest {

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
        assertThat(compilation).hadErrorContaining("Lens path should be not empty");
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
    public void generate_innerClassAnnotatedGenLenses_compilationError() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/inner_class/GenLensesOnInnerClass.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(withPathObjects(fileObject));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("@GenLenses is not allowed on inner classes");
    }

    @Test
    public void generate_classIsGeneric_compilationError() {
        JavaFileObject fileObject = JavaFileObjects.forResource("cases/generic_class/GenericClass.java");
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(fileObject);

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("@GenLenses is not allowed on generic classes");
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
