package dev.khbd.lens4j.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;

import static com.google.testing.compile.CompilationSubject.assertThat;

/**
 * @author Sergei_Khadanovich
 */
public class CompilationResult {

    private final Compilation compilation;

    public CompilationResult(Compilation compilation) {
        this.compilation = compilation;
    }

    public CompilationResult success() {
        assertThat(compilation).succeeded();
        return this;
    }

    public CompilationResult generated(String fileName, String path) {
        assertThat(compilation)
                .generatedSourceFile(fileName)
                .hasSourceEquivalentTo(JavaFileObjects.forResource(path));
        return this;
    }

    public CompilationResult generated(String fileName) {
        return generated(fileName, fileName + ".java");
    }

    public CompilationResult failed(String... messages) {
        assertThat(compilation).failed();
        for (String message : messages) {
            assertThat(compilation).hadErrorContaining(message);
        }
        return this;
    }

    public CompilationResult warn(String... messages) {
        for (String message : messages) {
            assertThat(compilation).hadWarningContaining(message);
        }
        return this;
    }

}
