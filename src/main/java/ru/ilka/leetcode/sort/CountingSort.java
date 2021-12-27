package ru.ilka.leetcode.sort;

public class CountingSort {
    private int min;
    private int max;

    public CountingSort(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int[] sort(int[] array) {
        int[] countingArray = new int[max + 1];

        for (int i = 0; i < array.length; i++) {
            countingArray[array[i]]++;
        }

        int indexInArray = 0;
        for (int i = 0; i < max; i++) {
            if (countingArray[i] > 0) {
                for (int j = 0; j < countingArray[i]; j++) {
                    array[indexInArray] = i;
                    indexInArray++;
                }
            }
        }
        return array;
    }
}
