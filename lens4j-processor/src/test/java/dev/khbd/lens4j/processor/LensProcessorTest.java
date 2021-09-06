package dev.khbd.lens4j.processor;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.testng.annotations.Test;

/**
 * @author Alexey_Bodyak
 */
public class LensProcessorTest {

    @Test
    public void generate_classWithOneGenReadAnnotations_generateValidAccountFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/examples/Account.java"));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("util/examples/AccountLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("util/result/AccountLenses.java"));
    }

    @Test
    public void generate_classWithTwoGenReadAnnotations_generateValidPaymentFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/examples/Payment.java"));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("util/examples/PaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("util/result/PaymentLenses.java"));
    }

    @Test
    public void generate_twoClassesWithGenLensesAnnotations_generateValidFactories() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/examples/Account.java"),
                                JavaFileObjects.forResource("util/examples/Payment.java"));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("util/examples/AccountLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("util/result/AccountLenses.java"));
        assertThat(compilation)
                .generatedSourceFile("util/examples/PaymentLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("util/result/PaymentLenses.java"));
    }

    @Test
    public void generate_annotationWithSpecificFactoryName_generateValidSpecificFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/examples/AccountWithSpecificFactoryName.java"));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("util/examples/SpecificFactoryName")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("util/result/SpecificFactoryName.java"));
    }

    @Test
    public void generate_annotationWithDeCapitalizeSpecificFactoryName_generateValidSpecificFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/examples/AccountWithDeCapitalizeSpecificFactoryName.java"));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("util/examples/SpecificFactoryName")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("util/result/CapitalizeSpecificFactoryName.java"));
    }

    @Test
    public void generate_annotationWithEmptyLensName_generateValidSpecificFactory() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/examples/AccountWithEmptyLensName.java"));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("util/examples/AccountWithEmptyLensNameLenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("util/result/AccountWithEmptyLensNameLenses.java"));
    }

    @Test
    public void generate_typeHasNotUniqueLensNames_compilationError() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/examples/AccountWithTheSameLensNames.java"));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("Lens names for type should be unique");
    }

    @Test
    public void generate_lensPathIsEmpty_compilationError() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/examples/AccountWithEmptyPath.java"));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("Lens path should be not empty");
    }

    @Test
    public void generate_annotationOnInterface_compilationError() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/examples/LensOnInterface.java"));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("@GenLenses is not allowed here");
    }

    @Test
    public void generate_annotationWithoutLensPath_compilationError() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/examples/AnnotationWithoutLensPath.java"));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("annotation @dev.khbd.lens4j.core.annotations.Lens is missing a default value for the element 'path'");
    }
}
