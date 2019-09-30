package ru.ilka.leetcode.entity;

public class StaticParent {
    static {
        System.out.println("parent static");
    }

    {
        System.out.println("parent logic");
    }

    public StaticParent() {
        System.out.println("Parent constructor");
    }
}
