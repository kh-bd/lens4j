package cases.on_enum;

import dev.khbd.lens4j.core.ReadLens;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import javax.annotation.Generated;

@Generated("dev.khbd.lens4j.processor.LensProcessor")
public final class ConfigTypeLenses {
    public static final ReadLens<ConfigType, Integer> MSG_LENGTH_READ_LENS = new ReadLens<ConfigType, Integer>() {
        @Override
        public final Integer get(ConfigType object) {
            if (object == null) {
                return null;
            }
            String msg = object.getMsg();
            if (msg == null) {
                return null;
            }
            return msg.length();
        }

    };

    private ConfigTypeLenses() {
        throw new UnsupportedOperationException("Can not create instance of factory class");
    }
}