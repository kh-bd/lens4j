package dev.khbd.lens4j.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;

import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.List;

import static com.google.testing.compile.Compiler.javac;

/**
 * @author Sergei_Khadanovich
 */
public class CompilationDescription {

    private final List<JavaFileObject> files = new ArrayList<>();
    private boolean inlined = false;

    private CompilationDescription() {
    }

    public CompilationDescription withFile(String file) {
        files.add(JavaFileObjects.forResource(file));
        return this;
    }

    public CompilationDescription withFiles(String... files) {
        for (String file : files) {
            withFile(file);
        }
        return this;
    }

    public CompilationDescription withInlinedOption() {
        this.inlined = true;
        return this;
    }

    public CompilationDescription withCommons() {
        files.addAll(
                List.of(
                        JavaFileObjects.forResource("common/Bank.java"),
                        JavaFileObjects.forResource("common/Currency.java"),
                        JavaFileObjects.forResource("common/Payer.java"),
                        JavaFileObjects.forResource("common/Receiver.java")
                )
        );
        return this;
    }

    public CompilationResult compile() {
        Compilation compilation = javac().withProcessors(new LensProcessor())
                .withOptions("-Alenses.generate.inlined=" + inlined)
                .compile(files);
        return new CompilationResult(compilation);
    }

    public static CompilationDescription of() {
        return new CompilationDescription();
    }
}
