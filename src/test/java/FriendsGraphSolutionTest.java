import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.ilka.leetcode.solution.FriendsGraphSolution;

import java.util.Map;
import java.util.stream.Stream;

public class FriendsGraphSolutionTest {

    private FriendsGraphSolution friendsGraphSolution = new FriendsGraphSolution();

    private static Stream<SolutionTestArgs> argumentsProvider() {
        return Stream.of(SolutionTestArgs.builder()
                        .relationships(new String[]{"Vanja:Sergio", "Sergio:Pedro", "Pedro:Martin", "Martin:Pablo",
                                "Pablo:Sergio", "Jelena:Ivan", "Jelena:Alan", "Alan:Tomislav"})
                        .names(new String[]{"Tomislav", "Martin"})
                        .result(Map.of("Tomislav", 3, "Martin", 4))
                        .build(),
                SolutionTestArgs.builder()
                        .relationships(new String[]{"Alvaro:Alex", "Roman:Nikola", "Octavia:Barbara", "Joao:Marko",
                                "Luis:Vanja", "Gabriel:Gustavo", "Alan:Pablo", "Ivan:Andres", "Artem:Anne",
                                "Martin:Alessandro", "Sebastian:Vinny", "Eduardo:Francis", "Zoltan:Vlad"})
                        .names(new String[]{"Zoltan", "Ivan"})
                        .result(Map.of("Zoltan", 1, "Ivan", 1))
                        .build(),
                SolutionTestArgs.builder()
                        .relationships(new String[]{"David:Francis", "Francis:Alessandro", "Alessandro:Ivan",
                                "Tomislav:Ivan", "Anne:Tomislav", "Anne:David", "Francis:Tomislav"})
                        .names(new String[]{"Francis", "David"})
                        .result(Map.of("Francis", 5, "David", 5))
                        .build(),
                SolutionTestArgs.builder()
                        .relationships(new String[]{"Alessandro:Anna", "Anna:Anne", "Anne:Barbara", "Barbara:David",
                                "David:Francis", "Francis:Eduardo", "Eduardo:Anna", "Eduardo:Alessandro",
                                "Luis:Marko", "Joao:Vlad", "Vlad:Luka", "Luka:Nikola", "Nikola:Roman",
                                "Vlad:Roman", "Vlad:Vinny", "Vinny:Roman", "Vlad:Andres", "Vinny:Ivan"})
                        .names(new String[]{"Barbara", "Joao"})
                        .result(Map.of("Barbara", 6, "Joao", 7))
                        .build()
        );
    }

    @ParameterizedTest(name = "attributes: {0}")
    @MethodSource("argumentsProvider")
    public void solutionTest(SolutionTestArgs args) {
        Assert.assertEquals(friendsGraphSolution.numberOfFriends(args.getRelationships(), args.getNames()),
                args.getResult());
    }

    @Data
    @Builder
    @AllArgsConstructor
    static class SolutionTestArgs {
        String[] relationships;
        String[] names;
        Map<String, Integer> result;
    }
}


