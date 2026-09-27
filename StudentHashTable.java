package campus;

/**
 * Requirement 6: Hash table (separate chaining) keyed by Student ID,
 * giving average O(1) search performance, used by menu option
 * "Search Student using Hashing".
 */
public class StudentHashTable {

    private static class Entry {
        String key;
        Student value;
        Entry next;
        Entry(String key, Student value) { this.key = key; this.value = value; }
    }

    private Entry[] buckets;
    private int capacity;
    private int count;

    public StudentHashTable() {
        this(16);
    }

    public StudentHashTable(int capacity) {
        this.capacity = capacity;
        this.buckets = new Entry[capacity];
    }

    private int hash(String key) {
        // Simple, deterministic hash based on character codes.
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 31 + key.charAt(i)) & 0x7fffffff;
        }
        return hash % capacity;
    }

    public void insert(String studentId, Student student) {
        if (count >= capacity * 0.75) resize();
        int index = hash(studentId);
        Entry current = buckets[index];
        while (current != null) {
            if (current.key.equalsIgnoreCase(studentId)) {
                current.value = student; // update existing
                return;
            }
            current = current.next;
        }
        Entry newEntry = new Entry(studentId, student);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        count++;
    }

    public Student search(String studentId) {
        int index = hash(studentId);
        Entry current = buckets[index];
        while (current != null) {
            if (current.key.equalsIgnoreCase(studentId)) return current.value;
            current = current.next;
        }
        return null;
    }

    public boolean remove(String studentId) {
        int index = hash(studentId);
        Entry current = buckets[index];
        Entry previous = null;
        while (current != null) {
            if (current.key.equalsIgnoreCase(studentId)) {
                if (previous == null) buckets[index] = current.next;
                else previous.next = current.next;
                count--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    private void resize() {
        Entry[] oldBuckets = buckets;
        capacity *= 2;
        buckets = new Entry[capacity];
        count = 0;
        for (Entry head : oldBuckets) {
            Entry current = head;
            while (current != null) {
                insert(current.key, current.value);
                current = current.next;
            }
        }
    }

    public int size() { return count; }
}
