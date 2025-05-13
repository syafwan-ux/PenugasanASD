public class Main {
    public static void main(String[] args) {
        int nodes = 15;

        DFS dfsGraph = new DFS(nodes);
        Dijkstra dijkstraGraph = new Dijkstra(nodes);
        KruskalMST kruskalGraph = new KruskalMST(nodes);

        // Edges with weights between 1 and 100 (dalam km)
        int[][] edges = {
            {0, 1, 45},   {0, 2, 32},
            {1, 3, 21},   {1, 4, 78},
            {2, 5, 44},   {2, 6, 67},
            {3, 7, 15},   {4, 7, 38},
            {4, 8, 59},   {5, 9, 27},
            {5, 10, 33},  {6, 10, 12},
            {7, 11, 48},  {8, 11, 63},
            {8, 12, 29},  {9, 13, 71},
            {10, 13, 54}, {10, 14, 82},
            {11, 14, 36}, {12, 13, 41},
            {12, 14, 65}, {0, 13, 90},
            {1, 12, 83},  {3, 14, 74},
            {6, 9, 53}
        };

        for (int[] edge : edges) {
            int src = edge[0];
            int dest = edge[1];
            int weight = edge[2];
            dfsGraph.addEdge(src, dest, weight);
            dijkstraGraph.addEdge(src, dest, weight);
            kruskalGraph.addEdge(src, dest, weight);
        }

        // Demonstrate DFS traversal from node 0 visiting all nodes
        dfsGraph.dfsTraverseAll(0);

        // Demonstrate Dijkstra shortest path from node 0
        dijkstraGraph.dijkstra(0);

        // Demonstrate Kruskal MST
        kruskalGraph.kruskalMST();
    }
}
