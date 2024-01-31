package cases.on_enum;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;

@GenLenses(lenses = @Lens(path = "msg.length()"))
public enum ConfigType {

    FIRST("first"),
    SECOND("second");

    private final String msg;

    ConfigType(String msg) {
        this.msg = msg;
    }

    String getMsg() {
        return msg;
    }

}