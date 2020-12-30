package ru.ilka.leetcode.mapstructdemo;

public class TypeEnumMapper {
    public TypeEnum asTypeEnum(String typeDisplayedName) {
        return TypeEnum.getByDisplayedName(typeDisplayedName);
    }

    public String asString(TypeEnum typeEnum){
        return typeEnum.getDisplayedName();
    }
}
