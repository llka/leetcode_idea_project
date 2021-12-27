package ru.ilka.leetcode.matrix;

import java.util.concurrent.ThreadLocalRandom;

public class MatrixService {


    public long[][] multiplyFast(long[][] a, long[][] b) {
        if (!areCompatibleForMultiplication(a, b)) {
            throw new MatrixException("Matrices are not compatible for multiplication!");
        }

        long[][] c = new long[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                for (int k = 0; k < b[0].length; k++) {
                    c[i][k] += a[i][j] * b[j][k];
                }
            }
        }

        return c;
    }

    public long[][] multiply(long[][] a, long[][] b) {
        if (!areCompatibleForMultiplication(a, b)) {
            throw new MatrixException("Matrices are not compatible for multiplication!");
        }

        long[][] c = new long[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int k = 0; k < b[0].length; k++) {
                for (int j = 0; j < a[0].length; j++) {
                    c[i][k] += a[i][j] * b[j][k];
                }
            }
        }

        return c;
    }

    public void print(long[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                System.out.print(a[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public long[][] generate(int l, int m) {
        long[][] matrix = new long[l][m];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = ThreadLocalRandom.current().nextLong(0, 9999);
            }
        }
        return matrix;
    }

    private boolean areCompatibleForMultiplication(long[][] a, long[][] b) {
        return a[0].length == b.length;
    }

}
