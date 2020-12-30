package ru.ilka.leetcode.solution;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.ilka.leetcode.Demo;
import ru.ilka.leetcode.config.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(Parameterized.class)
//@Runners(value = BlockJUnit4ClassRunner.class, others = {SpringJUnit4ClassRunner.class, Parameterized.class})
@ContextConfiguration(classes = {SpringConfig.class})
public class SolutionTest {
    private static final Logger logger = LogManager.getLogger(SolutionTest.class);

    @Autowired
    private Solution solution;

    private int testCase;
    private int expectedResult;

    //    public SolutionTest(int testCase, int expectedResult) {
    //        this.testCase = testCase;
    //        this.expectedResult = expectedResult;
    //    }

    //    @Parameterized.Parameters(name = "{index}: case - {0}, result - {1}")
    //    public static Collection<Object[]> testData() {
    //        return Arrays.asList(new Object[][]{
    //                {5, -1},
    //                {6, -1},
    //                {7, -1},
    //                {8, -1},
    //                {9, -1},
    //                {10, -1},
    //                {13, -1}
    //        });
    //    }

    char[] tasks = {'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'};

    @Test
    public void shouldOutputSolution() {
       // logger.info(solution.solution(new int[]{4, 2, 2, 5, 1, 5, 8} ));
    }
}
