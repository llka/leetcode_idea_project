package ru.ilka.leetcode.entity;

public class MyLinkedList {
    class Node {
        int val;
        Node prev;
        Node next;

        Node(int val, Node prev, Node next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;
    private int length;

    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {
        this.length = 0;
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (!isIndexValid(index)) {
            return -1;
        }
        return getNode(index).val;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        Node cur = new Node(val, null, head);

        if (head == null) {
            head = cur;
            tail = head;
        } else {
            head.prev = cur;
            head = cur;
        }
        length++;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        Node cur = new Node(val, tail, null);
        if (tail == null) {
            head = cur;
        } else {
            tail.next = cur;
            cur.prev = tail;
        }
        tail = cur;
        length++;
    }

    /**
     * Add a node of value val before the index-th node in the linked list.
     * If index equals to the length of linked list,
     * the node will be appended to the end of linked list.
     * If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (index > length) {
            return;
        }
        if (index == length) {
            addAtTail(val);
        } else if (index <= 0) {
            addAtHead(val);
        } else {
            Node next = getNode(index);
            if (next != null) {
                Node prev = next.prev;
                Node cur = new Node(val, prev, next);
                if (prev != null) {
                    prev.next = cur;
                }
                next.prev = cur;
                length++;
            }
        }
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (!isIndexValid(index)) {
            return;
        }

        Node cur = getNode(index);

        Node next = cur.next;
        Node prev = cur.prev;
        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }
        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }
        length--;
    }

    private Node getNode(int index) {
        if (!isIndexValid(index)) {
            return null;
        }

        Node curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr;
    }

    private boolean isIndexValid(int index) {
        return index >= 0 && index < length;
    }
}
