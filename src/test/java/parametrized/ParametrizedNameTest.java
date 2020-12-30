package parametrized;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

public class ParametrizedNameTest {
    private static final Logger logger = LogManager.getLogger(ParametrizedNameTest.class);

    static Stream<ArgsEnum> attributesProviders() {
        return Arrays.stream(ArgsEnum.values());
    }

    @BeforeEach
    void reportCurrentProvider(TestInfo info, TestReporter reporter) {
        info.getTestMethod().ifPresentOrElse(m -> logger.info(m.getName() + ": " + info.getDisplayName()),
                () -> logger.info(info.getDisplayName()));
    }

    @ParameterizedTest(name = "provider: {0}")
    @MethodSource("attributesProviders")
    public void test(ArgsEnum arg, TestInfo info, TestReporter reporter) {
        TestMethod test = () -> {
            throw new IllegalArgumentException("as");
        };
        reportFailedTestDetails(info, reporter, test);
    }

    private void reportFailedTestDetails(TestInfo info, TestReporter reporter, TestMethod test) {
        try {
            test.execute();
        } catch (Exception exception) {
            reporter.publishEntry(info.getDisplayName(), "Failed with exception: " + exception.getMessage());
        }
    }

    @FunctionalInterface
    interface TestMethod {
        void execute();
    }


}
