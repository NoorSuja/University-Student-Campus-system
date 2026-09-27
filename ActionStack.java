package campus;

import java.util.EmptyStackException;

/**
 * Requirement 3: Stack used to keep track of recent actions performed on
 * the system (add/update/delete), giving a simple history/undo-style log.
 * Implemented from scratch using a singly linked structure (LIFO).
 */
public class ActionStack {

    private static class Node {
        String action;
        Node next;
        Node(String action) { this.action = action; }
    }

    private Node top;
    private int size;

    public void push(String action) {
        Node newNode = new Node(action);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public String pop() {
        if (isEmpty()) throw new EmptyStackException();
        String action = top.action;
        top = top.next;
        size--;
        return action;
    }

    public String peek() {
        if (isEmpty()) throw new EmptyStackException();
        return top.action;
    }

    public boolean isEmpty() { return top == null; }
    public int size() { return size; }

    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No recent actions recorded.");
            return;
        }
        System.out.println("---- Recent Actions (most recent first) ----");
        Node current = top;
        int count = 1;
        while (current != null) {
            System.out.println(count + ". " + current.action);
            current = current.next;
            count++;
        }
    }
}
