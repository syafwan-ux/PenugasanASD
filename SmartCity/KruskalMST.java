import java.util.*;

public class KruskalMST {
    private int vertices;
    private List<List<KruskalMST.Edge>> adjacencyList;

    static class Edge {
        int destination;
        int weight;

        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    public KruskalMST(int vertices) {
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

    static class DisjointSet {
        int[] parent, rank;

        public DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int u) {
            if (parent[u] != u) {
                parent[u] = find(parent[u]);
            }
            return parent[u];
        }

        public void union(int u, int v) {
            int rootU = find(u);
            int rootV = find(v);
            if (rootU != rootV) {
                if (rank[rootU] < rank[rootV]) {
                    parent[rootU] = rootV;
                } else if (rank[rootV] < rank[rootU]) {
                    parent[rootV] = rootU;
                } else {
                    parent[rootV] = rootU;
                    rank[rootU]++;
                }
            }
        }
    }

    static class EdgeKruskal implements Comparable<EdgeKruskal> {
        int source, destination, weight;

        EdgeKruskal(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public int compareTo(EdgeKruskal other) {
            return this.weight - other.weight;
        }
    }

    public void kruskalMST() {
        List<EdgeKruskal> edges = new ArrayList<>();
        for (int u = 0; u < vertices; u++) {
            for (Edge edge : adjacencyList.get(u)) {
                if (u < edge.destination) { // to avoid duplicate edges in undirected graph
                    edges.add(new EdgeKruskal(u, edge.destination, edge.weight));
                }
            }
        }

        Collections.sort(edges);
        DisjointSet ds = new DisjointSet(vertices);

        List<EdgeKruskal> mst = new ArrayList<>();
        int mstWeight = 0;

        for (EdgeKruskal edge : edges) {
            int uSet = ds.find(edge.source);
            int vSet = ds.find(edge.destination);
            if (uSet != vSet) {
                mst.add(edge);
                mstWeight += edge.weight;
                ds.union(uSet, vSet);
            }
        }

        System.out.println("Edge-edge dalam MST:");
        for (EdgeKruskal edge : mst) {
            System.out.println(edge.source + " - " + edge.destination + " : " + edge.weight);
        }
        System.out.println("Total bobot MST: " + mstWeight);
    }
}
