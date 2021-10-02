package cases.generic.second_sub_class_supplied_simple_type;

import dev.khbd.lens4j.core.annotations.GenLenses;
import dev.khbd.lens4j.core.annotations.Lens;
import dev.khbd.lens4j.core.annotations.LensType;

@GenLenses(lenses = {
        @Lens(path = "a"),
        @Lens(path = "a", type = LensType.READ_WRITE),
        @Lens(path = "b"),
        @Lens(path = "b", type = LensType.READ_WRITE),
})
class SecondChild extends FirstChild<int[]> {
}