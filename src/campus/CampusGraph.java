package campus;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Requirements 7-11: Undirected graph modelling campus locations
 * (vertices) and roads/paths/connections (edges), using an adjacency
 * list. Supports add/remove locations, add/remove connections, display
 * of the network, and both BFS and DFS traversal.
 */
public class CampusGraph {

    // LinkedHashMap keeps insertion order, which makes display output stable.
    private final Map<String, List<String>> adjacencyList = new LinkedHashMap<>();

    public boolean addLocation(String location) {
        if (adjacencyList.containsKey(location)) {
            return false; // duplicate location
        }
        adjacencyList.put(location, new ArrayList<>());
        return true;
    }

    public boolean removeLocation(String location) {
        if (!adjacencyList.containsKey(location)) return false;
        adjacencyList.remove(location);
        // remove this location from every other location's neighbour list
        for (List<String> neighbours : adjacencyList.values()) {
            neighbours.remove(location);
        }
        return true;
    }

    public boolean hasLocation(String location) {
        return adjacencyList.containsKey(location);
    }

    /** Adds an undirected connection/road between two existing locations. */
    public boolean addConnection(String from, String to) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            return false; // one or both locations don't exist
        }
        if (from.equals(to)) return false; // no self-loops
        List<String> fromNeighbours = adjacencyList.get(from);
        if (fromNeighbours.contains(to)) return false; // connection already exists
        fromNeighbours.add(to);
        adjacencyList.get(to).add(from);
        return true;
    }

    public boolean removeConnection(String from, String to) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            return false;
        }
        boolean removedOne = adjacencyList.get(from).remove(to);
        boolean removedTwo = adjacencyList.get(to).remove(from);
        return removedOne || removedTwo;
    }

    public void displayConnections() {
        if (adjacencyList.isEmpty()) {
            System.out.println("No campus locations have been added yet.");
            return;
        }
        System.out.println("---- Campus Network (Adjacency List) ----");
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            String neighbours = entry.getValue().isEmpty()
                    ? "(no connections)"
                    : String.join(", ", entry.getValue());
            System.out.println(entry.getKey() + " -> " + neighbours);
        }
    }

    /** Breadth-First Search traversal starting from a given location. */
    public List<String> bfs(String start) {
        List<String> visitedOrder = new ArrayList<>();
        if (!adjacencyList.containsKey(start)) return visitedOrder;

        Set<String> visited = new java.util.HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            visitedOrder.add(current);
            for (String neighbour : adjacencyList.get(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        return visitedOrder;
    }

    /** Depth-First Search traversal starting from a given location. */
    public List<String> dfs(String start) {
        List<String> visitedOrder = new ArrayList<>();
        if (!adjacencyList.containsKey(start)) return visitedOrder;
        Set<String> visited = new java.util.HashSet<>();
        dfsRec(start, visited, visitedOrder);
        return visitedOrder;
    }

    private void dfsRec(String current, Set<String> visited, List<String> visitedOrder) {
        visited.add(current);
        visitedOrder.add(current);
        for (String neighbour : adjacencyList.get(current)) {
            if (!visited.contains(neighbour)) {
                dfsRec(neighbour, visited, visitedOrder);
            }
        }
    }

    public Set<String> getAllLocations() {
        return adjacencyList.keySet();
    }
}
