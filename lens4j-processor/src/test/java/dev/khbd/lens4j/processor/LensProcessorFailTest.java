package dev.khbd.lens4j.processor;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class LensProcessorFailTest {

    @DataProvider
    public static Object[][] failCases() {
        return new Object[][]{
                {"cases/method/generic/method_generics/Payment.java", "Parametrized methods are not allowed"},
                {"cases/method/not_found/_static/Payment.java", "Method 'payer' was not found in class 'Payment'"},
                {"cases/method/not_found/not_visible/Payment.java", "Method 'payer' was not found in class 'Payment'"},
                {"cases/method/not_found/return_void/Payment.java", "Method 'payer' was not found in class 'Payment'"},
                {"cases/method/not_found/with_arguments/Payment.java", "Method 'payer' was not found in class 'Payment'"},
                {"cases/array_without_supported_property/Payment.java", "Arrays property 'length1' is not supported"},
                {"cases/array_without_supported_property/PaymentWithPropertyAfterLength.java", "Non-declared types are allowed only at last position in path"},
                {"cases/method/wrong_position/single_part_path/Payment.java", "Methods are not allowed at last position of read-write lenses"},
                {"cases/primitive_type_in_the_middle/WithPrimitive.java", "Non-declared types are allowed only at last position in path"},
                {"cases/duplicate_lens_names/DuplicateNames.java", "Lens names for type should be unique"},
                {"cases/lens_with_empty_path/WithEmptyPath.java", "Lens path is incorrect"},
                {"cases/on_interface/LensOnInterface.java", "@GenLenses is not allowed here"},
                {"cases/lens_without_path/AnnotationWithoutLensPath.java", "annotation @dev.khbd.lens4j.core.annotations.Lens is missing a default value for the element 'path'"},
                {"cases/field_not_found/FieldNotFound.java", "Field 'bid' was not found in class 'Currency'"},
                {"cases/generic/generic_class/GenericClass.java", "@GenLenses is not allowed on generic classes"}
        };
    }

    @Test(dataProvider = "failCases")
    public void generate_someProblemExists_failToGenerate(String path, String msg) {
        CompilationDescription.of()
                .withFile(path)
                .compile()
                .failed(msg);
    }

    @Test(dataProvider = "failCases")
    public void generate_inliningEnabledAndSomeProblemExists_failToGenerate(String path, String msg) {
        CompilationDescription.of()
                .withFile(path)
                .withInlinedOption()
                .compile()
                .failed(msg);
    }

}
