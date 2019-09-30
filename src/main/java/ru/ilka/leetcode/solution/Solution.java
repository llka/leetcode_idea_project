package ru.ilka.leetcode.solution;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Solution {
    private static final Logger logger = LogManager.getLogger(Solution.class);

    public long fibonacciDpTabulation(int n) {
        if (n < 2) {
            return n;
        }

        long[] mem = new long[n + 1];
        mem[0] = 0;
        mem[1] = 1;
        for (int i = 2; i <= n ; i++) {
            mem[i] = mem[i - 2] + mem[i - 1];
        }
        return mem[n];
    }


    public long fibonacciRec(int n) {
        if (n < 2) {
            return n;
        }
        return fibonacciRec(n - 2) + fibonacciRec(n - 1);
    }

    private long dp(long[] mem, int n) {
        long result = n;
        if (mem[n] == 0) {
            if (n < 2) {
                result = n;
            } else {
                result = dp(mem, n - 2) + dp(mem, n - 1);
            }
            mem[n] = result;
        }
        return result;
    }

}
