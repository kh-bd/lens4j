/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2021 VTB Group. All rights reserved.
 */

package com.github.lens.processor;

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
    public void generate_classWithOneGenReadAnnotations_generateValidId() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/Account.java"));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com/github/lens/Lenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("util/result/AccountLenses.java"));
    }

    @Test
    public void generate_classWithTwoGenReadAnnotations_generateValidId() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/Payment.java"));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com/github/lens/Lenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("util/result/PaymentLenses.java"));
    }

    @Test
    public void generate_twoClassesWithGenReadAnnotations_generateValidId() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/Account.java"),
                                JavaFileObjects.forResource("util/Payment.java"));

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com/github/lens/Lenses")
                .hasSourceEquivalentTo(JavaFileObjects.forResource("util/result/ComboLens.java"));
    }

    @Test
    public void generate_typeWithDifferentFactoryNames_compilationError() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/TypeWithDifferentFactoryNames.java"));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("Lens from one type should be have one factory");
    }

    @Test
    public void generate_annotationWithoutLensName_compilationError() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/AnnotationWithoutLensName.java"));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("annotation @com.github.lens.processor.annotations.GenReadLens is missing a default value for the element 'lensName'");
    }

    @Test
    public void generate_annotationWithoutLensPath_compilationError() {
        Compilation compilation =
                javac().withProcessors(new LensProcessor())
                        .compile(JavaFileObjects.forResource("util/AnnotationWithoutLensPath.java"));

        assertThat(compilation).failed();
        assertThat(compilation).hadErrorContaining("annotation @com.github.lens.processor.annotations.GenReadLens is missing a default value for the element 'path'");
    }
}
