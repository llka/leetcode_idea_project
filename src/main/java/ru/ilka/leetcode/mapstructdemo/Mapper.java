package ru.ilka.leetcode.mapstructdemo;

import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper(uses = TypeEnumMapper.class)
public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

//    @Mapping(target = "type", source = "type.displayedName")
    Dto toDto(Entity entity);

    Entity toModel(Dto dto);
}
