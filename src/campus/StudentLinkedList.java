package campus;

/**
 * Requirement 2 & 12: Linked list used as the primary store for student
 * records. Provides add, update, delete, search, and display operations.
 */
public class StudentLinkedList {

    private static class Node {
        Student data;
        Node next;
        Node(Student data) { this.data = data; }
    }

    private Node head;
    private int size;

    public boolean isEmpty() { return head == null; }
    public int size() { return size; }

    /** Returns true if a student with this ID already exists. */
    public boolean contains(String studentId) {
        return findNode(studentId) != null;
    }

    private Node findNode(String studentId) {
        Node current = head;
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(studentId)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    /** Adds a new student record. Returns false if the ID is a duplicate. */
    public boolean add(Student student) {
        if (contains(student.getStudentId())) {
            return false; // duplicate ID
        }
        Node newNode = new Node(student);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) current = current.next;
            current.next = newNode;
        }
        size++;
        return true;
    }

    /** Updates an existing student's name/programme/marks. */
    public boolean update(String studentId, String name, String programme, Double marks) {
        Node node = findNode(studentId);
        if (node == null) return false;
        if (name != null && !name.isBlank()) node.data.setName(name);
        if (programme != null && !programme.isBlank()) node.data.setProgramme(programme);
        if (marks != null) node.data.setMarks(marks);
        return true;
    }

    /** Removes a student record by ID. Returns the removed Student, or null if not found. */
    public Student delete(String studentId) {
        Node current = head;
        Node previous = null;
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(studentId)) {
                if (previous == null) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.data;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    /** Linear search by ID (used to compare against hashing performance). */
    public Student search(String studentId) {
        Node node = findNode(studentId);
        return node == null ? null : node.data;
    }

    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("---- All Student Records (Linked List) ----");
        Node current = head;
        int count = 1;
        while (current != null) {
            System.out.println(count + ". " + current.data);
            current = current.next;
            count++;
        }
    }
}
