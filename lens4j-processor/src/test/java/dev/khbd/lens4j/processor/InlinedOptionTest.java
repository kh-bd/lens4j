package dev.khbd.lens4j.processor;

import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class InlinedOptionTest {

    @Test
    public void generate_inlinedOptionIsNotProvided_turnItOnImplicitly() {
        CompilationDescription.of()
                .withFile("dev.khbd.Payment", """
                        package dev.khbd;
                        
                        import dev.khbd.lens4j.core.annotations.GenLenses;
                        import dev.khbd.lens4j.core.annotations.Lens;
                        
                        @GenLenses(lenses = @Lens(path = "amount"))
                        public record Payment(Long amount) {
                        }
                        """)
                .compile()
                .success()
                .generated("dev.khbd.PaymentLenses", """
                        package dev.khbd;
                         
                        import dev.khbd.lens4j.core.ReadLens;
                        import java.lang.Long;
                        import java.lang.Override;
                        import java.lang.UnsupportedOperationException;
                        import javax.annotation.processing.Generated;
                        
                        @Generated("dev.khbd.lens4j.processor.LensProcessor")
                        public final class PaymentLenses {
                            public static final ReadLens<Payment, Long> AMOUNT_READ_LENS = new ReadLens<Payment, Long>() {
                                @Override
                                public final Long get(Payment object) {
                                    if (object == null) {
                                        return null;
                                    }
                                    return object.amount();
                                }
                              };
                        
                            private PaymentLenses() {
                                throw new UnsupportedOperationException("Can not create instance of factory class");
                            }
                        }
                        """);
    }

}
