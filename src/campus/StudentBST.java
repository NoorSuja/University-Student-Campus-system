package campus;

/**
 * Requirement 5: Binary Search Tree used to organize and search student
 * records by Student ID. Supports insert, search, in-order display
 * (which naturally lists students sorted by ID), and delete so the tree
 * stays in sync when a student record is removed.
 */
public class StudentBST {

    private static class TreeNode {
        Student data;
        TreeNode left, right;
        TreeNode(Student data) { this.data = data; }
    }

    private TreeNode root;

    public void insert(Student student) {
        root = insertRec(root, student);
    }

    private TreeNode insertRec(TreeNode node, Student student) {
        if (node == null) return new TreeNode(student);
        int cmp = student.getStudentId().compareToIgnoreCase(node.data.getStudentId());
        if (cmp < 0) {
            node.left = insertRec(node.left, student);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, student);
        }
        // if cmp == 0 (duplicate ID) we simply ignore; the linked list layer
        // is responsible for rejecting duplicates before this is called.
        return node;
    }

    public Student search(String studentId) {
        TreeNode current = root;
        while (current != null) {
            int cmp = studentId.compareToIgnoreCase(current.data.getStudentId());
            if (cmp == 0) return current.data;
            current = cmp < 0 ? current.left : current.right;
        }
        return null;
    }

    public void delete(String studentId) {
        root = deleteRec(root, studentId);
    }

    private TreeNode deleteRec(TreeNode node, String studentId) {
        if (node == null) return null;
        int cmp = studentId.compareToIgnoreCase(node.data.getStudentId());
        if (cmp < 0) {
            node.left = deleteRec(node.left, studentId);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, studentId);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            TreeNode successor = node.right;
            while (successor.left != null) successor = successor.left;
            node.data = successor.data;
            node.right = deleteRec(node.right, successor.data.getStudentId());
        }
        return node;
    }

    public void displayInOrder() {
        if (root == null) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("---- Students Sorted by ID (BST In-Order) ----");
        int[] count = {1};
        inOrderRec(root, count);
    }

    private void inOrderRec(TreeNode node, int[] count) {
        if (node == null) return;
        inOrderRec(node.left, count);
        System.out.println(count[0] + ". " + node.data);
        count[0]++;
        inOrderRec(node.right, count);
    }
}
