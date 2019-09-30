package ru.ilka.leetcode.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    private int val;
    private List<Node> children;

    public Node(int val, List<Node> children) {
        this.val = val;
        this.children = children;
    }

    public Node(int val, Node... children) {
        this.val = val;
        this.children = Arrays.asList(children);
    }

    public Node(int val) {
        this.val = val;
        this.children = new ArrayList<>();
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
}
