package cases.explicit_root.on_interface.root_interface;

import dev.khbd.lens4j.core.ReadLens;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.processing.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class ActionLenses {
    public static final ReadLens<Action, String> NAME = new ReadLens<Action, String>() {
        @Override
        public final String get(Action object) {
            if (object == null) {
                return null;
            }
            return object.name();
        }

    };

    private ActionLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}