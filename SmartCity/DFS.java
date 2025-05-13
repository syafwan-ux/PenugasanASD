import java.util.*;

public class DFS {
    private int vertices;
    private List<List<DFS.Edge>> adjacencyList;

    static class Edge {
        int destination;
        int weight;

        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    public DFS(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new LinkedList<>());
        }
    }

    public void addEdge(int source, int destination, int weight) {
        adjacencyList.get(source).add(new Edge(destination, weight));
        adjacencyList.get(destination).add(new Edge(source, weight)); // undirected graph
    }

    // DFS to find one path between two nodes (first found)
    public void dfsOnePath(int start, int end) {
        boolean[] visited = new boolean[vertices];
        List<Integer> pathList = new ArrayList<>();
        pathList.add(start);
        System.out.println("Salah satu jalur dari node " + start + " ke node " + end + ":");
        if (!dfsUtilOnePath(start, end, visited, pathList)) {
            System.out.println("Tidak ada jalur yang ditemukan.");
        }
    }

    private boolean dfsUtilOnePath(int current, int end, boolean[] visited, List<Integer> pathList) {
        if (current == end) {
            System.out.println(pathList);
            return true;
        }
        visited[current] = true;
        for (Edge edge : adjacencyList.get(current)) {
            if (!visited[edge.destination]) {
                pathList.add(edge.destination);
                if (dfsUtilOnePath(edge.destination, end, visited, pathList)) {
                    return true;
                }
                pathList.remove(pathList.size() - 1);
            }
        }
        visited[current] = false;
        return false;
    }

    // DFS traversal from start node visiting all reachable nodes, calculating total weight
    public void dfsTraverseAll(int start) {
        boolean[] visited = new boolean[vertices];
        List<Integer> traversalOrder = new ArrayList<>();
        int[] totalWeight = new int[1]; // use array to allow modification in recursion
        dfsTraverseUtil(start, visited, traversalOrder, totalWeight);
        System.out.println("DFS traversal mulai dari node " + start + ":");
        System.out.println(traversalOrder);
        System.out.println("Total bobot traversal: " + totalWeight[0]);
    }

    private void dfsTraverseUtil(int current, boolean[] visited, List<Integer> traversalOrder, int[] totalWeight) {
        visited[current] = true;
        traversalOrder.add(current);
        for (Edge edge : adjacencyList.get(current)) {
            if (!visited[edge.destination]) {
                totalWeight[0] += edge.weight;
                dfsTraverseUtil(edge.destination, visited, traversalOrder, totalWeight);
            }
        }
    }
}
