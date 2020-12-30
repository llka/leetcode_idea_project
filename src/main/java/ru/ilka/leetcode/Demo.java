package ru.ilka.leetcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.ilka.leetcode.entity.ListNode;
import ru.ilka.leetcode.entity.Node;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Demo {
    private static final Logger logger = LogManager.getLogger(Demo.class);
    private static final String MULTIPLE_AUDIENCE_REGEX = "^\\[(\".+\",)+(\".+\")\\]$";
    private static final char LAND = '1';
    private static final char WATER = '0';
    // ReadMeGenerator readMeGenerator = new ReadMeGenerator();
    //  readMeGenerator.printInConsoleReadMeFromFolder(LEETCODE_GIT_FOLDER_PATH);
    private static String LEETCODE_GIT_FOLDER_PATH = "C:\\_Ilya\\projects\\leetcode";
    private static String secret = "secret!!!!";
    private static int N = 30000;
    private static int START = 0;

    public static void main(String... args) throws Exception {
        System.out.println("result: " +  Optional.ofNullable("a")
                .map(s -> isOk(s))
                .orElseGet(() -> isNull()));
    }
    private static String isOk(String s){
        System.out.println("ok");
        return "ok";
    }
    private static String isNull(){
        System.out.println("is null");
        return "null";
    }


    private static int findMaxValInTree(Node node) {
        if (node == null) {
            return 0;
        }

        ArrayDeque<Node> queue = new ArrayDeque<>();
        if (node.left != null) {
            queue.addLast(node.left);
        }
        if (node.right != null) {
            queue.addLast(node.right);
        }

        int maxValue = Integer.MIN_VALUE;
        logger.info("Queue: {}.\n Max Val{}", queue, maxValue);

        while (!queue.isEmpty()) {
            Node curr = queue.pollFirst();
            logger.info("Queue: {}.\n Current: {} \n Max Val{}", queue, curr, maxValue);
            maxValue = Math.max(maxValue, curr.val);
            if (curr.left != null) {
                queue.addLast(curr.left);
            }
            if (curr.right != null) {
                queue.addLast(curr.right);
            }
        }

        return maxValue;
    }

    public static List<List<String>> groupAnagrams(String[] items) {

        Map<Map<Character, Integer>, List<String>> gropedByAnagramsMap = new HashMap();

        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            Map<Character, Integer> itemMap = new HashMap();
            for (int j = 0; j < item.length(); j++) {
                Character c = item.charAt(j);
                itemMap.put(c, itemMap.getOrDefault(c, 0) + 1);
            }

            List<String> alreadyFounded = gropedByAnagramsMap.getOrDefault(itemMap, new ArrayList<String>());
            alreadyFounded.add(item);
            gropedByAnagramsMap.put(itemMap, alreadyFounded);
        }

        List<List<String>> result = new ArrayList();
        gropedByAnagramsMap.values()
                .forEach(list -> {
                    Collections.sort(list);
                    result.add(list);
                });

        result.sort(Comparator.comparing(List::size, Comparator.reverseOrder()));
        return result;
    }

    private static void calculateHowMuchBackingReelNeed() {
        List<Dano> data = List.of(new Dano(0.2, 150_000),
                new Dano(0.16, 230_000),
                new Dano(0.23, 100_000));
        List<Double> nylonV = new ArrayList<>();

        logger.info("For Nylon");
        for (Dano dano : data) {
            double vСylinder = Math.PI * dano.getDia() * dano.getDia() * dano.getLength() / 4;
            nylonV.add(vСylinder);
            logger.debug("Dia: {}, length: {}, vC: {}", dano.getDia(), dano.getLength(), vСylinder);
        }
        logger.info("Expected length for 0.091mm Nylon: {}", nylonV.stream()
                .map(v -> (4 * v) / (Math.PI * 0.091 * 0.091))
                .mapToDouble(Double::doubleValue)
                .sum() / 3.0);

        List<Dano> dataPE = List.of(new Dano(0.12, 300_000),
                new Dano(0.14, 250_000),
                new Dano(0.16, 180_000));
        List<Double> peV = new ArrayList<>();

        logger.info("For PE");
        for (Dano dano : dataPE) {
            double vСylinder = Math.PI * dano.getDia() * dano.getDia() * dano.getLength() / 4;
            peV.add(vСylinder);
            logger.debug("Dia: {}, length: {}, vC: {}", dano.getDia(), dano.getLength(), vСylinder);
        }

        double averageTotalV = peV.stream()
                .mapToDouble(Double::doubleValue)
                .sum() / 3.0;
        logger.info("Average total V PE: {}", averageTotalV);

        List<Dano> dataXPE = List.of(new Dano(0.091, 150_000));


        double ygkPeV = 0;
        for (Dano dano : dataXPE) {
            double vСylinder = Math.PI * dano.getDia() * dano.getDia() * dano.getLength() / 4;
            ygkPeV = vСylinder;
            logger.debug("Dia: {}, length: {}, vC: {}", dano.getDia(), dano.getLength(), vСylinder);
        }
        logger.info("YGK 0.3 PE V: {}", ygkPeV);

        double deltaVForBacking = averageTotalV - ygkPeV;
        logger.info("Delta V for backing: {}", deltaVForBacking);

        double expectedBackingLength = (4 * deltaVForBacking) / (Math.PI * 0.14 * 0.14);
        logger.info("Expected 0.14 mm backing length: {} m", expectedBackingLength / 1000);
    }

    // is valid parenthesis
    public static boolean isValid(String s) {
        Map<Character, Character> parenthesis = Map.of(
                '(', ')',
                '{', '}',
                '[', ']');
        ArrayDeque<Character> stack = new ArrayDeque<>();
        char[] array = s.toCharArray();

        for (Character ch : array) {
            // ch is opening
            if (parenthesis.containsKey(ch)) {
                stack.addFirst(ch);
            } else {
                // ch is closing
                if (stack.isEmpty()) {
                    return false;
                }
                Character previousOpening = stack.pollFirst();
                if (!ch.equals(parenthesis.get(previousOpening))) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public static int leftMostColumnWithOne(int[][] nums) {
        final int N = nums.length;
        final int M = nums[0].length;

        boolean foundOne = false;

        int i = 0;
        int j = M - 1;

        while (i < N && j >= 0) {
            if (nums[i][j] == 0) {
                i++;
            } else {
                // binaryMatrix.get(i, j) == 1
                foundOne = true;
                j--;
            }
        }

        return !foundOne ? -1 : j;
    }

    private static int binarySearchMostLeftOne(int[] nums, int leftIdx, int rightIdx) {
        int initialRight = rightIdx;
        int mostLeftOne = initialRight + 1;
        while (leftIdx <= rightIdx) {
            int middleIdx = leftIdx + (rightIdx - leftIdx) / 2;

            logger.debug("call");
            if (nums[middleIdx] == 1) {
                if (middleIdx < mostLeftOne) {
                    mostLeftOne = middleIdx;
                }
                logger.debug("call if one");
                if (middleIdx > 0 && nums[middleIdx - 1] == 1) {
                    rightIdx = middleIdx - 1;
                } else {
                    return mostLeftOne;
                }
            } else {
                leftIdx = middleIdx + 1;
            }
        }
        return mostLeftOne == initialRight + 1 ? -1 : mostLeftOne;
    }

    private static boolean eDostavkaAvailabilityCheckByZone(String zone) {
        final String E_DOSTAVKA_URL = "https://e-dostavka.by/dzone/?action=zonesDataJson";
        Document document = null;
        try {
            document = Jsoup.connect(E_DOSTAVKA_URL).get();
            logger.debug(document);
        } catch (IOException e) {
            logger.error("Can not load document from {}", E_DOSTAVKA_URL);
        }

        logger.info(document.getElementsByClass("zone zone_65"));

        return false;
    }

    private static void mapConfigurationUrlsToProviderEndpointsBuilder(String input) {
        StringBuilder result = new StringBuilder("ProviderEndpoints.builder()\n");
        Arrays.asList(input.split("\n"))
                .forEach(line -> {
                    line = line.trim();
                    int urlStartIndex = line.indexOf(":");
                    int httpsStartIndex = line.indexOf("http");

                    result.append(".").append(line.substring(0, urlStartIndex)).append("(\"");
                    result.append(line.substring(httpsStartIndex)).append("\")\n");
                });
        result.append(".build();");

        System.out.println(result.toString());
    }

    private static String parseAwsLogGroupsIntoDeleteScript(String logGroupsInput) {
        List<String> logGroups = Arrays.stream(logGroupsInput.split("\n"))
                .filter(s -> s.startsWith("ecs-hub-stack") || s.startsWith("fargate"))
                .collect(Collectors.toList());

        StringBuilder command = new StringBuilder();
        logGroups.forEach(logGroup -> command
                .append("aws logs delete-log-group --log-group-name ").append(logGroup).append("\n"));

        logger.info(command.toString());
        return command.toString();
    }

    private static void drawPath(String input, Map<Coordinate, Integer> map) {
        List<String> commands = Arrays.stream(input.split(",")).collect(Collectors.toList());
        int cur_i = START;
        int cur_j = START;
        int stepsCounter = 0;

        for (String command : commands) {
            char direction = command.charAt(0);
            int steps = Integer.parseInt(command.substring(1));
            switch (direction) {
                case 'R':
                    for (int i = 0; i < steps; i++) {
                        map.put(new Coordinate(cur_i, ++cur_j), ++stepsCounter);
                    }
                    break;
                case 'L':
                    for (int i = 0; i < steps; i++) {
                        map.put(new Coordinate(cur_i, --cur_j), ++stepsCounter);
                    }
                    break;
                case 'U':
                    for (int i = 0; i < steps; i++) {
                        map.put(new Coordinate(++cur_i, cur_j), ++stepsCounter);
                    }
                    break;
                case 'D':
                    for (int i = 0; i < steps; i++) {
                        map.put(new Coordinate(--cur_i, cur_j), ++stepsCounter);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown command direction " + direction);
            }
        }
    }

    private static int intCodeComputer(Integer[] nums) {
        for (int i = 0; i < nums.length; i += 4) {
            int operationCode = nums[i];
            if (operationCode == 99) {
                logger.debug(nums[0]);
                break;
            }
            int aAddress = nums[i + 1];
            int bAddress = nums[i + 2];
            int resultAddress = nums[i + 3];

            int a = nums[aAddress];
            int b = nums[bAddress];
            int result = 0;
            switch (operationCode) {
                case 1:
                    result = a + b;
                    break;
                case 2:
                    result = a * b;
                    break;
                default:
                    logger.error("Unknown operation code: {}", operationCode);
            }
            nums[resultAddress] = result;
        }
        return nums[0];
    }

    private static Long countFuel(long mass) {
        Long res = mass / 3 - 2;

        return res < 0 ? 0 : res;
    }

    private static int maxCamporator(int a, int b) {
        logger.debug("-----------");
        logger.info("a = " + a);
        logger.info("b = " + b);
        logger.info("max = " + (Math.max(a, b)));
        logger.debug("-----------");
        return Math.max(a, b);
    }

    /**
     * In a sorter array finds the index from which rotation starts.
     * For Example:
     * array: 4 5 6 7 0 1 2
     * index: 0 1 2 3 4 5 6
     * will return 4
     *
     * @param nums - sorted array at some point rotated.
     * @return rotations start index
     */
    private static int findRotationStartIndex(int[] nums) {
        int rightNum = nums[nums.length - 1];

        int leftIdx = 0;
        int rightIdx = nums.length - 1;

        while (leftIdx <= rightIdx) {
            int middleIdx = leftIdx + (rightIdx - leftIdx) / 2;

            if (nums[middleIdx] > rightNum && nums[middleIdx + 1] <= rightNum) {
                return middleIdx + 1;
            }

            if (nums[middleIdx] > rightNum && nums[middleIdx + 1] > rightNum) {
                leftIdx = middleIdx + 1;
            } else {
                rightIdx = middleIdx - 1;
            }
        }
        return -1;
    }

    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        boolean needToBeSorted = false;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            if (next == null) {
                if (needToBeSorted) {
                    return sortList(head);
                } else {
                    return head;
                }
            }

            if (curr.val > next.val) {
                needToBeSorted = true;
                swapValues(curr, curr.next);
            }
            curr = curr.next;
        }
        if (needToBeSorted) {
            return sortList(head);
        } else {
            return head;
        }
    }

    /**
     * Merge sort for Linked List
     */
    public static ListNode sortLinkedList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode a = new ListNode(head.val);
        ListNode aHead = a;
        ListNode b = new ListNode(head.next.val);
        ListNode bHead = b;

        head = head.next.next;

        divideLinkedListInto2Lists(head, a, b);

        a = sortLinkedList(aHead);
        b = sortLinkedList(bHead);

        return mergeSortedLinkedLists(a, b);
    }

    private static void divideLinkedListInto2Lists(ListNode head, ListNode a, ListNode b) {
        if (head == null) {
            return;
        } else {
            while (head != null) {
                a.next = new ListNode(head.val);
                a = a.next;
                head = head.next;
                if (head != null) {
                    b.next = new ListNode(head.val);
                    b = b.next;
                    head = head.next;
                }
            }
        }
    }

    private static ListNode mergeSortedLinkedLists(ListNode a, ListNode b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }

        ListNode merged;
        if (a.val < b.val) {
            merged = a;
            a = a.next;
        } else {
            merged = b;
            b = b.next;
        }
        ListNode headRef = merged;

        while (a != null || b != null) {
            if (a != null && b != null) {
                if (a.val < b.val) {
                    merged.next = a;
                    a = a.next;
                } else {
                    merged.next = b;
                    b = b.next;
                }
            } else if (a != null) {
                merged.next = a;
                a = a.next;
            } else if (b != null) {
                merged.next = b;
                b = b.next;
            } else {
                return headRef;
            }
            merged = merged.next;
        }
        return headRef;
    }

    //System.out.println(Boolean.valueOf("true"));

    //max comparator funny example
    //logger.debug(List.of(-100, -50, 0, 50, 100).stream().max(Demo::maxCamporator).get());

      /*  MyLinkedList linkedList = new MyLinkedList();
        linkedList.addAtHead(1);
        linkedList.addAtIndex(1, 2);  // linked list becomes 1->2
        logger.debug(linkedList.get(1));
        logger.debug(linkedList.get(0));
        logger.debug(linkedList.get(2));
        // returns 3

       */

        /*
        Solution solution = new Solution();

        ListNode n1 = new ListNode(-1);
        ListNode n2 = new ListNode(5);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(8);
//        ListNode n6  = new ListNode(6);


        n1.next = n2;
        n2.next = n3;
//        n3.next = n4;
//        n4.next = n5;
//        n5.next = n6;

        logger.info(findRotationStartIndex(new int[]{3, 5, 1}));
        */

    //String equals vs ==
/*        String a = "abc";
        String b = "abc";
        String c = new String("abc");
        String d = new String("abc");

        // true
        logger.debug(a == b);
        //false
        logger.debug(a == c);
        //false
        logger.debug(c == d);


        logger.info("now Integers");
        int intA = 123;
        Integer intB = new Integer(123);

        //true
        logger.debug(intA == intB);*/

    private static void swapValues(ListNode a, ListNode b) {
        a.val = a.val + b.val;
        b.val = a.val - b.val;
        a.val = a.val - b.val;
    }

    public static ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode curr = head.next.next;
        ListNode oddsHead = head;
        ListNode evensHead = head.next;
        ListNode odds = oddsHead;
        ListNode evens = evensHead;

        while (odds != null) {
            if (odds != null) {
                odds.next = curr;
                odds = odds.next;
            }
            if (evens != null) {
                evens.next = curr.next;
                evens = evens.next;
            }
            curr = curr.next != null ? curr.next.next : null;
        }

        odds.next = evensHead;
        return oddsHead;

    }

    public static ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        /* if head must be removed */
        if (head.val == val) {
            return removeElements(findValidNext(head, val), val);
        }

        ListNode curr = head;
        ListNode next = curr.next;

        while (next != null) {
            if (next.val == val) {
                curr.next = findValidNext(next, val);
                curr = curr.next;
                next = curr == null ? null : curr.next;
            } else {
                curr = next;
                next = curr.next;
            }
        }
        return head;
    }

    private static ListNode findValidNext(ListNode curr, int val) {
        if (curr.next != null) {
            while (curr != null) {
                if (curr.val == val) {
                    curr = curr.next;
                } else {
                    return curr;
                }
            }
        } else {
            return null;
        }
        return null;
    }

    public static long countAs(Collection<String> collection, String target) {
        //        Map<String, Long> map = collection.stream().collect(Collectors.groupingBy(str -> String.valueOf(str),
        //            (Supplier<HashMap<String, Long>>) HashMap::new,
        //            Collectors.counting()));
        //        return map.get("A");

        Collection<String> toBeRemoved = new ArrayList<>();

        for (String letter : collection) {
            if (target.equals(letter)) {
                toBeRemoved.add(letter);
            }
        }
        collection.removeAll(toBeRemoved);
        return toBeRemoved.size();
    }

//    public static int sum(Node root) {
//        Queue<Node> children = new ArrayDeque<>();
//        int count = 0;
//        if (root != null) {
//            children.add(root);
//        }
//
//        while (!children.isEmpty()) {
//            Node node = children.poll();
//            if (node != null) {
//                count += node.getVal();
//                children.addAll(node.getChildren());
//            }
//        }
//
//        return count;
//    }

    private static String getTimeAsString(Date date, String datePattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        return date != null ? dateFormat.format(date) : null;
    }

    public static synchronized void sync(String s) throws InterruptedException {
        logger.debug("s start");
        Thread thread = new Thread(() -> kek(secret));
        thread.start();
        TimeUnit.SECONDS.sleep(10);
        logger.debug(" s end");
    }

    public static void kek(String s) {
        logger.info("kek");
        logger.info(s);
    }

    public static int[] findMaxIsland(int[][] grid) {
        boolean[][] isVisitedGrid = new boolean[grid.length][grid[0].length];

        int maxCountKey = 0;
        int maxCountValue = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (!isVisitedGrid[i][j]) {
                    int islandKey = grid[i][j];
                    int count = traverseIslandAndCountFields(islandKey, grid, isVisitedGrid, i, j);

                    if (count > maxCountValue) {
                        maxCountKey = islandKey;
                        maxCountValue = count;
                    }
                }
            }
        }
        logger.debug(maxCountKey + ": " + maxCountValue);
        return new int[]{maxCountKey, maxCountValue};
    }

    // breath first search
    private static int traverseIslandAndCountFields(int value, int[][] grid, boolean[][] isVisitedGrid, int iInit, int jInit) {
        if (isVisitedGrid[iInit][jInit]) {
            return 0;
        }
        int counter = 0;

        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        queue.addFirst(new Coordinate(iInit, jInit));

        while (!queue.isEmpty()) {
            Coordinate coordinate = queue.pollFirst();
            int i = coordinate.i;
            int j = coordinate.j;

            counter++;
            isVisitedGrid[i][j] = true;

            if (isInBound(grid, i + 1, j) && grid[i + 1][j] == value && !isVisitedGrid[i + 1][j]) {
                queue.addLast(new Coordinate(i + 1, j));
            }
            if (isInBound(grid, i - 1, j) && grid[i - 1][j] == value && !isVisitedGrid[i - 1][j]) {
                queue.addLast(new Coordinate(i - 1, j));
            }
            if (isInBound(grid, i, j + 1) && grid[i][j + 1] == value && !isVisitedGrid[i][j + 1]) {
                queue.addLast(new Coordinate(i, j + 1));
            }
            if (isInBound(grid, i, j - 1) && grid[i][j - 1] == value && !isVisitedGrid[i][j - 1]) {
                queue.addLast(new Coordinate(i, j - 1));
            }
        }
        return counter;
    }

    private static boolean isInBound(int[][] grid, int i, int j) {
        return i >= 0 && i < grid.length &&
                j >= 0 && j < grid[i].length;
    }

    //task 2
    public int solution(String S, int[] X, int[] Y) {
        int dotsCounter = 0;

        return dotsCounter;
    }

    private long distance(int x, int y) {
        return x * x + y * y;
    }

    public int numIslands(char[][] grid) {
        if (grid.length == 0) {
            return 0;
        }

        boolean[][] isVisitedGrid = new boolean[grid.length][grid[0].length];

        int islandCounter = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (!isVisitedGrid[i][j]) {
                    if (grid[i][j] == LAND) {
                        traverseIsland(grid, isVisitedGrid, i, j);
                        islandCounter++;
                    }
                }
            }
        }
        return islandCounter;
    }

    // depth first search (because it's recursion)
    private void traverseIsland(char[][] grid, boolean[][] isVisitedGrid, int i, int j) {

        if (grid[i][j] == WATER || isVisitedGrid[i][j]) {
            return;
        }

        isVisitedGrid[i][j] = true;
        if (isInBound(grid, i + 1, j) && grid[i + 1][j] == LAND) {
            traverseIsland(grid, isVisitedGrid, i + 1, j);
        }
        if (isInBound(grid, i - 1, j) && grid[i - 1][j] == LAND) {
            traverseIsland(grid, isVisitedGrid, i - 1, j);
        }
        if (isInBound(grid, i, j + 1) && grid[i][j + 1] == LAND) {
            traverseIsland(grid, isVisitedGrid, i, j + 1);
        }
        if (isInBound(grid, i, j - 1) && grid[i][j - 1] == LAND) {
            traverseIsland(grid, isVisitedGrid, i, j - 1);
        }
    }

    private void traverseIslandBreadthFirstSearch(char[][] grid, boolean[][] isVisitedGrid, int iInit, int jInit) {
        if (grid[iInit][jInit] == WATER || isVisitedGrid[iInit][jInit]) {
            return;
        }
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        queue.addFirst(new Coordinate(iInit, jInit));

        while (!queue.isEmpty()) {
            Coordinate coordinate = queue.pollFirst();
            int i = coordinate.i;
            int j = coordinate.j;

            isVisitedGrid[i][j] = true;
            if (isInBound(grid, i + 1, j) && grid[i + 1][j] == LAND && !isVisitedGrid[i + 1][j]) {
                queue.addLast(new Coordinate(i + 1, j));
            }
            if (isInBound(grid, i - 1, j) && grid[i - 1][j] == LAND && !isVisitedGrid[i - 1][j]) {
                queue.addLast(new Coordinate(i - 1, j));
            }
            if (isInBound(grid, i, j + 1) && grid[i][j + 1] == LAND && !isVisitedGrid[i][j + 1]) {
                queue.addLast(new Coordinate(i, j + 1));
            }
            if (isInBound(grid, i, j - 1) && grid[i][j - 1] == LAND && !isVisitedGrid[i][j - 1]) {
                queue.addLast(new Coordinate(i, j - 1));
            }
        }
    }

    private boolean isInBound(char[][] grid, int i, int j) {
        return i >= 0 && i < grid.length &&
                j >= 0 && j < grid[i].length;
    }

    void start() {
        long[] a1 = {3, 4, 5};
        long[] a2 = fix(a1);
        System.out.print(a1[0] + a1[1] + a1[2] + " ");
        System.out.println(a2[0] + a2[1] + a2[2]);
    }

    long[] fix(long[] a3) {
        a3[1] = 7;
        return a3;
    }

    public int trap(int[] height) {
        int sum = 0;
        int max = 0;

        for (int i = 0; i < height.length; i++) {
            max = Math.max(max, height[i]);
        }

        boolean[][] fromLeft = new boolean[height.length][max];
        int currentMaxLeft = 0;
        for (int i = 0; i < height.length; i++) {
            if (height[i] > currentMaxLeft) {
                currentMaxLeft = height[i];
            } else {
                for (int j = height[i]; j < currentMaxLeft; j++) {
                    fromLeft[i][j] = true;
                }
            }
        }

        int currentMaxRight = 0;
        for (int i = height.length - 1; i > -1; i--) {
            if (height[i] > currentMaxRight) {
                currentMaxRight = height[i];
            } else {
                for (int j = height[i]; j < currentMaxRight; j++) {
                    if (fromLeft[i][j]) {
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    public int maxArea(int[] height) {
        int maxArea = 0;
        int leftIdx = 0;
        int rightIdx = height.length - 1;

        while (rightIdx > leftIdx) {
            maxArea = Math.max(maxArea, rightIdx - leftIdx) * Math.min(height[leftIdx], height[rightIdx]);
            if (height[leftIdx] < height[rightIdx]) {
                leftIdx++;
            } else {
                rightIdx--;
            }
        }
        return maxArea;
    }

    public int findDuplicate(int[] nums) {
        int BIG = nums.length * 2;
        int DOUBLE_BIG = BIG * 2;

        for (int i = 0; i < nums.length; i++) {
            int number = nums[i] > BIG ? (nums[i] % BIG) : nums[i];
            nums[number] += BIG;

            if (nums[number] >= DOUBLE_BIG) {
                return number;
            }
        }
        return -1;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        ListNode slow = head;
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return head;
    }

    public ListNode detectCycle(ListNode head) {

        if (head == null) {
            return null;
        }
        ListNode slow = head.next;
        ListNode fast = head.next == null ? null : head.next.next;

        while (slow != null && fast != null) {
            if (slow == fast) {
                ListNode slowFromHead = head;
                while (slow != slowFromHead) {
                    slow = slow.next;
                    slowFromHead = slowFromHead.next;
                }
                return slowFromHead;
            }
            slow = slow.next;
            fast = fast.next == null ? null : fast.next.next;
        }
        return null;

    }

    int removeObstacle(int numRows, int numColumns, List<List<Integer>> lot) {
        final int DESTINATION = 9;
        boolean[][] visited = new boolean[numRows][numColumns];

        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        queue.addLast(new Coordinate(0, 0));
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            Coordinate cur = queue.pollFirst();

            if (lot.get(cur.i).get(cur.j) == DESTINATION) {
                return cur.dist;
            }

            // up
            if (cur.i - 1 >= 0 && !isVisited(visited, lot, cur.i - 1, cur.j)) {
                queue.addLast(new Coordinate(cur.i - 1, cur.j, cur.dist + 1));
                visited[cur.i - 1][cur.j] = true;
            }

            // down
            if (cur.i + 1 < numRows && !isVisited(visited, lot, cur.i + 1, cur.j)) {
                queue.addLast(new Coordinate(cur.i + 1, cur.j, cur.dist + 1));
                visited[cur.i + 1][cur.j] = true;
            }

            // left
            if (cur.j - 1 >= 0 && !isVisited(visited, lot, cur.i, cur.j - 1)) {
                queue.addLast(new Coordinate(cur.i, cur.j - 1, cur.dist + 1));
                visited[cur.i][cur.j - 1] = true;
            }

            // right
            if (cur.j + 1 < numColumns && !isVisited(visited, lot, cur.i, cur.j + 1)) {
                queue.addLast(new Coordinate(cur.i, cur.j + 1, cur.dist + 1));
                visited[cur.i][cur.j + 1] = true;
            }
        }
        return -1;
    }

    private boolean isVisited(boolean[][] visited, List<List<Integer>> lot, int i, int j) {
        return visited[i][j] == true || lot.get(i).get(j) == 0;
    }

    public List<String> prioritizedOrders(int numOrders, List<String> orderList) {
        // WRITE YOUR CODE HERE
        List<String> primeOrders = new ArrayList<>();
        List<String> otherOrders = new ArrayList<>();
        List<String> result = new ArrayList<>(numOrders);

        for (String order : orderList) {
            if (isPrimeOrder(order)) {
                primeOrders.add(order);
            } else {
                otherOrders.add(order);
            }
        }

        Comparator<String> comparePrimes = Comparator.comparing(a -> a.substring(a.indexOf(" ")));
        Comparator<String> comparePrimesIds = Comparator.comparing(a -> a.substring(0, a.indexOf(" ")));

        primeOrders.sort(comparePrimes.thenComparing(comparePrimesIds));
        result.addAll(primeOrders);
        result.addAll(otherOrders);
        return result;
    }

    private boolean isPrimeOrder(String order) {
        final String PRIME_ORDER_REGEX = "^[a-zA-Z0-9]+( [a-zA-Z]+)+$";
        order = order.trim();
        return order.matches(PRIME_ORDER_REGEX);
    }

    public int removeElement(int[] nums, int val) {
        int length = nums.length;
        int i = 0;

        while (i < length) {
            if (nums[i] == val) {
                if (i < length - 1) {
                    nums[i] = nums[length - 1];
                }
                --length;
            } else {
                i++;
            }
        }
        return length;
    }

    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            if (nums[0] == target) {
                return 0;
            } else {
                return -1;
            }
        }

        // min number index
        int rotationStartIdx = findRotationStartIndex(nums);

        if (rotationStartIdx == -1) {
            return binarySearch(nums, target, 0, nums.length - 1);
        }

        if (nums[rotationStartIdx] == target) {
            return rotationStartIdx;
        }

        if (nums[0] <= target && target <= nums[rotationStartIdx - 1]) {
            return binarySearch(nums, target, 0, rotationStartIdx - 1);
        } else {
            return binarySearch(nums, target, rotationStartIdx, nums.length - 1);
        }
    }

    public int binarySearch(int[] nums, int target) {
        int leftIdx = 0;
        int rightIdx = nums.length - 1;

        while (leftIdx <= rightIdx) {
            int middleIdx = leftIdx + (rightIdx - leftIdx) / 2;

            if (nums[middleIdx] == target) {
                return middleIdx;
            }

            if (target < nums[middleIdx]) {
                rightIdx = middleIdx - 1;
            } else {
                leftIdx = middleIdx + 1;
            }
        }
        return -1;
    }

    private int binarySearch(int[] nums, int target, int leftIdx, int rightIdx) {
        while (leftIdx <= rightIdx) {
            int middleIdx = leftIdx + (rightIdx - leftIdx) / 2;

            if (nums[middleIdx] == target) {
                return middleIdx;
            }

            if (target < nums[middleIdx]) {
                rightIdx = middleIdx - 1;
            } else {
                leftIdx = middleIdx + 1;
            }
        }
        return -1;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        ListNode[] paired = new ListNode[lists.length % 2 == 0 ? lists.length / 2 : lists.length / 2 + 1];
        int pairedIdx = 0;
        for (int i = 0; i < lists.length; i++) {
            if (i + 1 < lists.length) {
                paired[pairedIdx++] = mergeSortedLinkedLists(lists[i], lists[++i]);
            } else {
                paired[pairedIdx++] = lists[i];
            }
        }

        if (paired.length == 1) {
            return paired[0];
        } else if (paired.length == 2) {
            return mergeSortedLinkedLists(paired[0], paired[1]);
        } else {
            return mergeKLists(paired);
        }
    }

    public void deleteNode(ListNode node) {
        while (node.next != null) {
            ListNode next = node.next;
            node.val = next.val;

            if (next.next == null) {
                node.next = null;
            } else {
                node = next;
            }
        }
    }

    @ToString
    public static class Node {
        public Node left;
        public Node right;
        public int val;

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public Node(int val) {
            this.val = val;
        }
    }

    @AllArgsConstructor
    @Data
    static class Dano {
        double dia;
        double length;
    }

    static class LRUCache {
        private final int capacity;
        private LinkedHashMap<Integer, Integer> map;
        private LinkedHashSet<Integer> set;


        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.map = new LinkedHashMap<>(capacity);
            this.set = new LinkedHashSet<>(capacity);
        }

        public int get(int key) {
            int value = map.getOrDefault(key, -1);
            if (value != -1) {
                updateOrder(key);
            }
            return value;
        }

        public void put(int key, int value) {
            if (map.size() >= capacity && !map.containsKey(key)) {
                Integer lastKey = set.stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("Set does not contain expected last element"));
                set.remove(lastKey);
                map.remove(lastKey);
            }

            map.remove(key);
            map.put(key, value);
            updateOrder(key);
        }

        private void updateOrder(int key) {
            set.remove(key);
            set.add(key);
        }
    }

    static class Coordinate {
        int i;
        int j;
        int dist;

        public Coordinate(int i, int j) {
            this.i = i;
            this.j = j;
            this.dist = 0;
        }

        public Coordinate(int i, int j, int dist) {
            this.i = i;
            this.j = j;
            this.dist = dist;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return i == that.i &&
                    j == that.j &&
                    dist == that.dist;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j, dist);
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "i=" + i +
                    ", j=" + j +
                    ", dist=" + dist +
                    '}';
        }
    }

    //    SELECT "ID", "DESCRIPTION", "STARTDATE", "ENDDATE"
    //    FROM "EVENTSONSITE"."EVENT"
    //    WHERE "ID" = 32346;
    //
    //
    //    UPDATE  "EVENTSONSITE"."EVENT"
    //    SET "STARTDATE" = '2014-01-01 00:00:00.000', "ENDDATE" = '2014-01-02 00:00:00.000'
    //    WHERE "ID" = 32346

}
