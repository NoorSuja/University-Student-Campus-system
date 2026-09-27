# University Student Record and Campus Route Management System
CIT300 Data Structures and Algorithms — Graded Practical Assignment 1

## Group Members
*(Fill in every member's real details — required by the assignment brief.)*

| Name | Student ID | Assigned Responsibility | Individual Contribution |
|------|-----------|--------------------------|--------------------------|
| [MNF N Suja] | [23DA2-0484] | Linked list & student-record management | Designed and implemented StudentLinkedList.java, including add, update, delete, search, and display methods. Handled duplicate Student ID validation logic. Tested edge cases such as adding to an empty list and deleting the only remaining record. |
| [ML Hafila] | [23DA2-0509] | Stack & queue implementation | Implemented ActionStack.java (recent actions/history log) and ServiceQueue.java (FIFO service request queue). Integrated both into the main menu (options 5, 6, 7) and tested enqueue/dequeue and push/pop behavior, including empty-queue and empty-stack cases. |
| [SA Ifath] | [23DA2-1067] | BST & hashing/search functionality | Implemented StudentBST.java with insert, search, in-order display, and delete methods to keep records sorted by Student ID. Designed the hash function and separate-chaining logic in StudentHashTable.java, including automatic resizing when the load factor exceeds 75%. |
| [MR Jeeshan Begum] | [23DA2-0775] | Graph, campus locations & BFS/DFS | Designed the adjacency-list structure in CampusGraph.java. Implemented add/remove location and add/remove connection methods, plus BFS and DFS traversal. Tested traversal correctness on a sample campus map with multiple locations and roads. |
| All Members | — | Integration, testing, documentation, GitHub collaboration | Participated in group meetings, reviewed each other's code, tested the integrated system end-to-end, contributed commits/pull requests on GitHub, helped write the README, and took part in recording the demo video. |

## Project Structure
```
CIT300_Project/
├── README.md
└── src/
    └── campus/
        ├── Student.java            # Student record model
        ├── StudentLinkedList.java  # Linked list: add/update/delete/search/display
        ├── ActionStack.java        # Stack: recent actions / history log
        ├── ServiceQueue.java       # Queue: service requests (FIFO)
        ├── StudentBST.java         # BST: organize/search students by ID
        ├── StudentHashTable.java   # Hash table: O(1) average ID search
        ├── CampusGraph.java        # Graph: locations & roads, BFS/DFS
        └── Main.java               # Menu-driven console entry point
```

## How Each Requirement Is Covered
| # | Requirement | Where |
|---|-------------|-------|
| 1 | Student record fields | `Student.java` |
| 2 | Linked list storage | `StudentLinkedList.java` |
| 3 | Stack for recent actions/history | `ActionStack.java` |
| 4 | Queue for service requests | `ServiceQueue.java` |
| 5 | BST for organizing/searching by ID | `StudentBST.java` |
| 6 | Hashing for fast ID search | `StudentHashTable.java` |
| 7–11 | Graph: locations, connections, add/remove, display, BFS/DFS | `CampusGraph.java` |
| 12 | Add/update/delete/search/display for students | `Main.java` (menu options 1–4, 9) |
| 13 | Menu-driven console interface | `Main.java` |
| 14 | Input validation & error handling | Throughout `Main.java` (duplicate IDs, invalid marks, missing records, unavailable connections) |

## How to Compile and Run
From the `CIT300_Project` folder:
```bash
cd src
javac campus/*.java
java campus.Main
```

## Design Notes
- The **linked list** is the single source of truth for student records; the
  **BST** and **hash table** are kept in sync with it whenever a record is
  added, updated, or deleted, so all three structures always agree.
- The **hash table** uses separate chaining and resizes automatically once
  it gets more than 75% full, keeping ID lookups close to O(1).
- The **BST** is unbalanced (a plain BST, not AVL) for simplicity — this can
  be swapped for an AVL implementation if your module wants self-balancing
  as extra marks; the assignment allows either.
- The **graph** is undirected (roads go both ways) and uses an adjacency
  list (`LinkedHashMap<String, List<String>>`), supporting both BFS and DFS.
- Input validation covers: empty input, non-numeric input, marks outside
  0–100, duplicate student IDs, duplicate/unknown locations, missing
  records, and attempts to connect locations that don't exist.

## Suggested Next Steps for Your Group
1. Add your real names/IDs above and confirm each member can explain their part.
2. Compile and test every menu option (1–16), including edge cases (empty
   list, duplicate IDs, invalid marks, non-existent locations).
3. Push to GitHub using branches/commits per member, then open pull requests
   to merge into `main` — the brief asks for evidence of this workflow.
4. Record your demo video (under 15 minutes) with everyone's face visible
   and each member demonstrating their own component.
5. Follow the submission checklist in the assignment brief exactly
   (Google Drive Editor access for both emails, TXT file with the link, etc.)
   before the 29 September deadline.
