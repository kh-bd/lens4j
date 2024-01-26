package dev.khbd.lens4j.processor;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Sergei_Khadanovich
 */
public class LensProcessorFailTest {

    @DataProvider
    public static Object[][] failCases() {
        return new Object[][]{
                {new String[]{"cases/record/at_last_position/Account.java"}, "Record properties are not allowed at last position of read-write lenses"},
                {new String[]{"cases/method/generic/method_generics/Payment.java"}, "Parametrized methods are not allowed"},
                {new String[]{"cases/method/not_found/_static/Payment.java"}, "Method 'payer' was not found in class 'Payment'"},
                {new String[]{"cases/method/not_found/not_visible/Payment.java"}, "Method 'payer' was not found in class 'Payment'"},
                {new String[]{"cases/method/not_found/return_void/Payment.java"}, "Method 'payer' was not found in class 'Payment'"},
                {new String[]{"cases/method/not_found/with_arguments/Payment.java"}, "Method 'payer' was not found in class 'Payment'"},
                {new String[]{"cases/array_without_supported_property/Payment.java"}, "Arrays property 'length1' is not supported"},
                {new String[]{"cases/array_without_supported_property/PaymentWithPropertyAfterLength.java"}, "Non-declared types are allowed only at last position in path"},
                {new String[]{"cases/method/wrong_position/single_part_path/Payment.java"}, "Methods are not allowed at last position of read-write lenses"},
                {new String[]{"cases/primitive_type_in_the_middle/WithPrimitive.java"}, "Non-declared types are allowed only at last position in path"},
                {new String[]{"cases/duplicate_lens_names/DuplicateNames.java"}, "Lens names for type should be unique"},
                {new String[]{"cases/lens_with_empty_path/WithEmptyPath.java"}, "Lens path is incorrect"},
                {new String[]{"cases/on_interface/LensOnInterface.java"}, "@GenLenses is not allowed here"},
                {new String[]{"cases/lens_without_path/AnnotationWithoutLensPath.java"}, "annotation @dev.khbd.lens4j.core.annotations.Lens is missing a default value for the element 'path'"},
                {new String[]{"cases/field_not_found/FieldNotFound.java"}, "Field 'bid' was not found in class 'Currency'"},
                {new String[]{"cases/generic/generic_class/GenericClass.java"}, "@GenLenses is not allowed on generic classes"},
                {new String[]{"cases/several_factories_to_same_class/modifiers_differ/Client.java", "cases/several_factories_to_same_class/modifiers_differ/Customer.java"}, "Several @GenLenses annotations point to same factories class, but they cannot be merged into 'cases.several_factories_to_same_class.modifiers_differ.FactoryImpl'"}
        };
    }

    @Test(dataProvider = "failCases")
    public void generate_someProblemExists_failToGenerate(String[] path, String msg) {
        CompilationDescription.of()
                .withFiles(path)
                .composed()
                .compile()
                .failed(msg);
    }

    @Test(dataProvider = "failCases")
    public void generate_inliningEnabledAndSomeProblemExists_failToGenerate(String[] path, String msg) {
        CompilationDescription.of()
                .withFiles(path)
                .inlined()
                .compile()
                .failed(msg);
    }

}
