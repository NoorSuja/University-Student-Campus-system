package campus;

import java.util.NoSuchElementException;

/**
 * Requirement 4: Queue used to manage student service requests in the
 * order they arrive (FIFO). Implemented from scratch using a singly
 * linked structure with head/tail pointers for O(1) enqueue/dequeue.
 */
public class ServiceQueue {

    private static class Node {
        String request;
        Node next;
        Node(String request) { this.request = request; }
    }

    private Node front;
    private Node rear;
    private int size;

    public void enqueue(String request) {
        Node newNode = new Node(request);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public String dequeue() {
        if (isEmpty()) throw new NoSuchElementException("No service requests in queue.");
        String request = front.request;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return request;
    }

    public boolean isEmpty() { return front == null; }
    public int size() { return size; }

    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No pending service requests.");
            return;
        }
        System.out.println("---- Pending Service Requests (front to back) ----");
        Node current = front;
        int count = 1;
        while (current != null) {
            System.out.println(count + ". " + current.request);
            current = current.next;
            count++;
        }
    }
}
