package campus;

import java.util.List;
import java.util.Scanner;

/**
 * Requirement 13 & 14: Menu-driven console interface that wires together
 * the linked list, stack, queue, BST, hash table, and graph to satisfy
 * the University Student Record and Campus Route Management System spec.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final StudentLinkedList studentList = new StudentLinkedList();
    private static final ActionStack recentActions = new ActionStack();
    private static final ServiceQueue serviceQueue = new ServiceQueue();
    private static final StudentBST studentTree = new StudentBST();
    private static final StudentHashTable studentHash = new StudentHashTable();
    private static final CampusGraph campusGraph = new CampusGraph();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> updateStudent();
                case 3 -> deleteStudent();
                case 4 -> studentList.displayAll();
                case 5 -> addServiceRequest();
                case 6 -> processNextServiceRequest();
                case 7 -> recentActions.displayAll();
                case 8 -> studentTree.displayInOrder();
                case 9 -> searchStudentByHash();
                case 10 -> addCampusLocation();
                case 11 -> removeCampusLocation();
                case 12 -> addCampusConnection();
                case 13 -> removeCampusConnection();
                case 14 -> campusGraph.displayConnections();
                case 15 -> traverseCampus();
                case 16 -> {
                    running = false;
                    System.out.println("Exiting. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please select a number from 1 to 16.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("========================================================");
        System.out.println(" University Student Record & Campus Route Management ");
        System.out.println("========================================================");
        System.out.println(" 1. Add Student Record");
        System.out.println(" 2. Update Student Record");
        System.out.println(" 3. Delete Student Record");
        System.out.println(" 4. Display All Records using Linked List");
        System.out.println(" 5. Add Service Request to Queue");
        System.out.println(" 6. Process Next Service Request");
        System.out.println(" 7. Display Recent Actions using Stack");
        System.out.println(" 8. Display Students using BST");
        System.out.println(" 9. Search Student using Hashing");
        System.out.println("10. Add Campus Location");
        System.out.println("11. Remove Campus Location");
        System.out.println("12. Add Campus Connection/Road");
        System.out.println("13. Remove Campus Connection/Road");
        System.out.println("14. Display Campus Connections");
        System.out.println("15. Traverse Campus Locations using BFS or DFS");
        System.out.println("16. Exit");
    }

    // ---------------------- Student record operations ----------------------

    private static void addStudent() {
        String id = readNonEmptyString("Enter Student ID: ");
        if (studentList.contains(id)) {
            System.out.println("Error: A student with ID " + id + " already exists.");
            return;
        }
        String name = readNonEmptyString("Enter Name: ");
        String programme = readNonEmptyString("Enter Programme: ");
        double marks = readValidMarks("Enter Marks (0-100): ");

        Student student = new Student(id, name, programme, marks);
        studentList.add(student);
        studentTree.insert(student);
        studentHash.insert(id, student);
        recentActions.push("ADDED student " + id + " (" + name + ")");
        System.out.println("Student record added successfully.");
    }

    private static void updateStudent() {
        String id = readNonEmptyString("Enter Student ID to update: ");
        if (!studentList.contains(id)) {
            System.out.println("Error: No student found with ID " + id + ".");
            return;
        }
        System.out.println("Leave a field blank to keep its current value.");
        String name = readOptionalString("Enter new Name: ");
        String programme = readOptionalString("Enter new Programme: ");
        String marksInput = readOptionalString("Enter new Marks (0-100): ");
        Double marks = null;
        if (!marksInput.isBlank()) {
            try {
                marks = Double.parseDouble(marksInput);
                if (marks < 0 || marks > 100) {
                    System.out.println("Invalid marks entered. Marks must be between 0 and 100. Update aborted for marks.");
                    marks = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid marks format. Marks left unchanged.");
            }
        }
        studentList.update(id, name, programme, marks);
        // keep hash table and tree in sync with the (possibly) updated record
        Student updated = studentList.search(id);
        studentHash.insert(id, updated);
        studentTree.delete(id);
        studentTree.insert(updated);
        recentActions.push("UPDATED student " + id);
        System.out.println("Student record updated successfully.");
    }

    private static void deleteStudent() {
        String id = readNonEmptyString("Enter Student ID to delete: ");
        Student removed = studentList.delete(id);
        if (removed == null) {
            System.out.println("Error: No student found with ID " + id + ".");
            return;
        }
        studentTree.delete(id);
        studentHash.remove(id);
        recentActions.push("DELETED student " + id + " (" + removed.getName() + ")");
        System.out.println("Student record deleted successfully.");
    }

    private static void searchStudentByHash() {
        String id = readNonEmptyString("Enter Student ID to search: ");
        Student student = studentHash.search(id);
        if (student == null) {
            System.out.println("No student found with ID " + id + ".");
        } else {
            System.out.println("Found: " + student);
        }
    }

    // ---------------------- Service queue operations ----------------------

    private static void addServiceRequest() {
        String request = readNonEmptyString("Describe the service request (e.g. Student ID + reason): ");
        serviceQueue.enqueue(request);
        recentActions.push("QUEUED service request: " + request);
        System.out.println("Service request added to the queue.");
    }

    private static void processNextServiceRequest() {
        if (serviceQueue.isEmpty()) {
            System.out.println("There are no pending service requests.");
            return;
        }
        String request = serviceQueue.dequeue();
        recentActions.push("PROCESSED service request: " + request);
        System.out.println("Now processing: " + request);
    }

    // ---------------------- Campus graph operations ----------------------

    private static void addCampusLocation() {
        String location = readNonEmptyString("Enter new campus location name: ");
        if (campusGraph.addLocation(location)) {
            recentActions.push("ADDED campus location " + location);
            System.out.println("Location added successfully.");
        } else {
            System.out.println("Error: Location \"" + location + "\" already exists.");
        }
    }

    private static void removeCampusLocation() {
        String location = readNonEmptyString("Enter campus location to remove: ");
        if (campusGraph.removeLocation(location)) {
            recentActions.push("REMOVED campus location " + location);
            System.out.println("Location removed successfully.");
        } else {
            System.out.println("Error: Location \"" + location + "\" was not found.");
        }
    }

    private static void addCampusConnection() {
        String from = readNonEmptyString("Enter first location: ");
        String to = readNonEmptyString("Enter second location: ");
        if (!campusGraph.hasLocation(from) || !campusGraph.hasLocation(to)) {
            System.out.println("Error: Both locations must already exist. Add them first (option 10).");
            return;
        }
        if (campusGraph.addConnection(from, to)) {
            recentActions.push("ADDED connection " + from + " <-> " + to);
            System.out.println("Connection added successfully.");
        } else {
            System.out.println("Error: Connection already exists or locations are invalid.");
        }
    }

    private static void removeCampusConnection() {
        String from = readNonEmptyString("Enter first location: ");
        String to = readNonEmptyString("Enter second location: ");
        if (campusGraph.removeConnection(from, to)) {
            recentActions.push("REMOVED connection " + from + " <-> " + to);
            System.out.println("Connection removed successfully.");
        } else {
            System.out.println("Error: That connection does not exist (or locations are unavailable).");
        }
    }

    private static void traverseCampus() {
        if (campusGraph.getAllLocations().isEmpty()) {
            System.out.println("No campus locations exist yet. Add some first (option 10).");
            return;
        }
        String start = readNonEmptyString("Enter the starting location: ");
        if (!campusGraph.hasLocation(start)) {
            System.out.println("Error: Location \"" + start + "\" does not exist.");
            return;
        }
        String type = readNonEmptyString("Traverse using (B)FS or (D)FS? ");
        List<String> order;
        if (type.equalsIgnoreCase("B")) {
            order = campusGraph.bfs(start);
            System.out.println("BFS Traversal Order: " + order);
        } else if (type.equalsIgnoreCase("D")) {
            order = campusGraph.dfs(start);
            System.out.println("DFS Traversal Order: " + order);
        } else {
            System.out.println("Invalid choice. Please enter B or D.");
        }
    }

    // ---------------------- Input helper / validation methods ----------------------

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    private static String readOptionalString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static double readValidMarks(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double marks = Double.parseDouble(input);
                if (marks < 0 || marks > 100) {
                    System.out.println("Invalid marks. Please enter a value between 0 and 100.");
                    continue;
                }
                return marks;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }
}
