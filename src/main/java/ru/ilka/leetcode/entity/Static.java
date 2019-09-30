package ru.ilka.leetcode.entity;

public class Static extends StaticParent {
    public static int a;
    private int b;

    {
        System.out.println("Hi, logic 1");
        System.out.println("a=" + Static.a + ", b=" + b);
    }

    static {
        System.out.println("Hi, 1 static");
    }

    {
        System.out.println("Hi, logic 2");
        System.out.println("a=" + Static.a + ", b=" + b);
    }

    static {
        System.out.println("Hi, 2 static");
        a = 10;
    }

    {
        System.out.println("Hi, logic 3");
        b = 51;
        System.out.println("a=" + Static.a + ", b=" + b);
    }

    public Static(int a, int b) {
        System.out.println("a=" + Static.a + ", b=" + this.b);
        Static.a = a;
        this.b = b;
        System.out.println("a=" + Static.a + ", b=" + this.b);
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
