package ru.ilka.leetcode.mapstructdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Entity {
    private int id;
    private TypeEnum type;
}
