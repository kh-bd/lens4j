package cases.generic.first_sub_class_supplied_simple_type;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(lenses = {@Lens(path = "value"), @Lens(path = "value", type = LensType.READ_WRITE)})
class Child extends Base<String> {
}