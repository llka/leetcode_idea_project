package ru.ilka.leetcode.entity;

import java.util.Objects;

public class MapEntry_Hashcode1_EqualsFalse {
    private int val;
    private String name;

    public MapEntry_Hashcode1_EqualsFalse(int val, String name) {
        this.val = val;
        this.name = name;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "MapEntry_Hashcode1_EqualsFalse{" +
            "val=" + val +
            ", name='" + name + '\'' +
            '}';
    }
}
