package mapstruct;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.ilka.leetcode.mapstructdemo.Dto;
import ru.ilka.leetcode.mapstructdemo.Entity;
import ru.ilka.leetcode.mapstructdemo.Mapper;
import ru.ilka.leetcode.mapstructdemo.TypeEnum;

public class MapstructTest {

    @Test
    public void toDto_shouldMapEnumIntoString(){
        Entity entity = new Entity(1, TypeEnum.AION_POOL);
        Dto expectedDto = new Dto(entity.getId(), entity.getType().getDisplayedName());

        Dto actual = Mapper.INSTANCE.toDto(entity);

        Assert.assertEquals(entity.getType().getDisplayedName(), actual.getType());
    }

    @Test
    public void toEntity_shouldMapDisplayedNameIntoEnum(){
        Entity entity = new Entity(1, TypeEnum.AION_POOL);
        Dto dto = new Dto(entity.getId(), entity.getType().getDisplayedName());

        Entity actual = Mapper.INSTANCE.toModel(dto);

        Assert.assertEquals(actual, entity);
    }
}
