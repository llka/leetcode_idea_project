package ru.ilka.leetcode.solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FriendsGraphSolution {
    private static final Integer NOT_DEFINED_GROUP = -1;

    public Map<String, Integer> numberOfFriends(String[] relationships, String[] names) {
        Map<String, Integer> friendsMap = new HashMap<>();
        Map<String, Set<String>> friendsGraph = new HashMap<>();
        Map<String, Integer> friendsGroups = new HashMap<>();

        int groupNumber = 0;

        // init bidirectional graph of friends
        Arrays.asList(relationships).forEach(rel -> {
            String[] relationship = rel.split(":");

            String from = relationship[0];
            String to = relationship[1];

            Set<String> toSet = friendsGraph.getOrDefault(from, new HashSet<>());
            toSet.add(to);
            toSet.remove(from);
            friendsGraph.put(from, toSet);

            Set<String> fromSet = friendsGraph.getOrDefault(to, new HashSet<>());
            fromSet.add(from);
            fromSet.remove(to);
            friendsGraph.put(to, fromSet);

            friendsGroups.put(from, NOT_DEFINED_GROUP);
            friendsGroups.put(to, NOT_DEFINED_GROUP);
        });


        // поиск связных компонет графа
        // group friends via dfs
        for (String friend : friendsGroups.keySet()) {
            if (NOT_DEFINED_GROUP.equals(friendsGroups.get(friend))) {
                dfsAndGroup(friend, friendsGraph, friendsGroups, ++groupNumber);
            }
        }

        Map<Integer, Long> groupsSizes = friendsGroups.values()
                .stream()
                .collect(Collectors.groupingBy(group -> group, HashMap::new, Collectors.counting()));

        Arrays.asList(names).forEach(name -> {
            int group = friendsGroups.get(name);
            int groupSize = groupsSizes.get(group).intValue();
            friendsMap.put(name, groupSize - 1);
        });

        return friendsMap;
    }

    private void dfsAndGroup(String vertex, Map<String, Set<String>> friendsGraphMap,
                             Map<String, Integer> vertexGroups, int groupNumber) {
        if (!NOT_DEFINED_GROUP.equals(vertexGroups.get(vertex))) {
            return;
        }

        vertexGroups.put(vertex, groupNumber);

        Optional.ofNullable(friendsGraphMap.get(vertex))
                .orElse(Collections.emptySet())
                .forEach(friend -> dfsAndGroup(friend, friendsGraphMap, vertexGroups, groupNumber));

    }
}
