package ru.ilka.leetcode.sort;

import java.util.List;

public class BubbleSort {
    public List<Integer> sort(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if (list.get(j) < list.get(i)) {
                    swap(list, j, i);
                }
            }
        }
        return list;
    }

    private void swap(List<Integer> list, int a, int b) {
        list.set(a, list.get(a) + list.get(b));
        list.set(b, list.get(a) - list.get(b));
        list.set(a, list.get(a) - list.get(b));
    }
}
