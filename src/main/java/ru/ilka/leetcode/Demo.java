package ru.ilka.leetcode;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ilka.leetcode.entity.ListNode;
import ru.ilka.leetcode.entity.Node;
import ru.ilka.leetcode.solution.Solution;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Demo {
    private static final Logger logger = LogManager.getLogger(Demo.class);

    @Autowired
    private Solution solution;


    private static String LEETCODE_GIT_FOLDER_PATH = "C:\\_Ilya\\projects\\leetcode";


    private static String secret = "secret!!!!";
    private static int N = 90;

    public static void main(String... args) throws Exception {

        System.out.println(Boolean.valueOf("true"));


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

        // ReadMeGenerator readMeGenerator = new ReadMeGenerator();
        //  readMeGenerator.printInConsoleReadMeFromFolder(LEETCODE_GIT_FOLDER_PATH);
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


    class Coordinate {
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
        if (a == null) return b;
        if (b == null) return a;

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

    public static int sum(Node root) {
        Queue<Node> children = new ArrayDeque<>();
        int count = 0;
        if (root != null) {
            children.add(root);
        }

        while (!children.isEmpty()) {
            Node node = children.poll();
            if (node != null) {
                count += node.getVal();
                children.addAll(node.getChildren());
            }
        }

        return count;
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

//    SELECT "ID", "DESCRIPTION", "STARTDATE", "ENDDATE"
//    FROM "EVENTSONSITE"."EVENT"
//    WHERE "ID" = 32346;
//
//
//    UPDATE  "EVENTSONSITE"."EVENT"
//    SET "STARTDATE" = '2014-01-01 00:00:00.000', "ENDDATE" = '2014-01-02 00:00:00.000'
//    WHERE "ID" = 32346


}