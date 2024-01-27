package dev.khbd.lens4j.processor;

import static com.google.testing.compile.Compiler.javac;

import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;

import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei_Khadanovich
 */
public class CompilationDescription {

    private final List<JavaFileObject> files = new ArrayList<>();

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
        Compiler compiler = javac().withProcessors(new LensProcessor());
        return new CompilationResult(compiler.compile(files));
    }

    public static CompilationDescription of() {
        return new CompilationDescription();
    }
}
