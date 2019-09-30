package ru.ilka.leetcode.entity;

import java.util.Objects;

public class MapEntry {
    private int val;
    private String name;

    public MapEntry(int val, String name) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapEntry that = (MapEntry) o;
        return val == that.val &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, name);
    }

    @Override
    public String toString() {
        return "MapEntry{" +
            "val=" + val +
            ", name='" + name + '\'' +
            '}';
    }
}
