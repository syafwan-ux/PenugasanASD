import java.util.*;

/**
 * Kelas KruskalMST untuk mencari Minimum Spanning Tree (MST) menggunakan algoritma Kruskal
 * pada graf tidak berarah.
 */
public class KruskalMST {
    private int vertices; // Jumlah simpul dalam graf
    private List<List<KruskalMST.Edge>> adjacencyList; // Daftar ketetanggaan untuk menyimpan graf

    /**
     * Kelas Edge merepresentasikan sisi graf dengan tujuan dan bobot tertentu.
     */
    static class Edge {
        int destination; // Tujuan edge
        int weight;      // Bobot edge

        /**
         * Konstruktor Edge.
         * @param destination simpul tujuan
         * @param weight bobot sisi
         */
        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    /**
     * Konstruktor KruskalMST.
     * @param vertices jumlah simpul dalam graf
     * Inisialisasi adjacency list untuk menyimpan sisi-sisi graf.
     */
    public KruskalMST(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new LinkedList<>());
        }
    }

    /**
     * Menambahkan sisi (edge) antara dua simpul dengan bobot tertentu.
     * Karena graf tidak berarah, sisi ditambahkan dua arah.
     * @param source simpul sumber
     * @param destination simpul tujuan
     * @param weight bobot sisi
     */
    public void addEdge(int source, int destination, int weight) {
        adjacencyList.get(source).add(new Edge(destination, weight));
        adjacencyList.get(destination).add(new Edge(source, weight)); // graf tidak berarah
    }

    /**
     * Kelas DisjointSet untuk membantu dalam algoritma Kruskal
     * dengan operasi find dan union untuk mengelola himpunan terpisah.
     */
    static class DisjointSet {
        int[] parent, rank;

        /**
         * Konstruktor DisjointSet.
         * @param n jumlah elemen
         */
        public DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        /**
         * Mencari root dari elemen u dengan path compression.
         * @param u elemen
         * @return root dari elemen u
         */
        public int find(int u) {
            if (parent[u] != u) {
                parent[u] = find(parent[u]);
            }
            return parent[u];
        }

        /**
         * Menggabungkan dua himpunan yang berisi elemen u dan v.
         * @param u elemen pertama
         * @param v elemen kedua
         */
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

    /**
     * Kelas EdgeKruskal merepresentasikan sisi dengan sumber, tujuan, dan bobot,
     * serta mengimplementasikan Comparable untuk sorting berdasarkan bobot.
     */
    static class EdgeKruskal implements Comparable<EdgeKruskal> {
        int source, destination, weight;

        /**
         * Konstruktor EdgeKruskal.
         * @param source simpul sumber
         * @param destination simpul tujuan
         * @param weight bobot sisi
         */
        EdgeKruskal(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        /**
         * Membandingkan bobot sisi untuk sorting.
         * @param other sisi lain
         * @return perbedaan bobot
         */
        @Override
        public int compareTo(EdgeKruskal other) {
            return this.weight - other.weight;
        }
    }

    /**
     * Mencari Minimum Spanning Tree (MST) menggunakan algoritma Kruskal.
     * Menampilkan sisi-sisi yang termasuk dalam MST dan total bobot MST.
     */
    public void kruskalMST() {
        List<EdgeKruskal> edges = new ArrayList<>();
        // Mengumpulkan semua sisi unik dari graf
        for (int u = 0; u < vertices; u++) {
            for (Edge edge : adjacencyList.get(u)) {
                if (u < edge.destination) { // menghindari duplikat sisi pada graf tidak berarah
                    edges.add(new EdgeKruskal(u, edge.destination, edge.weight));
                }
            }
        }

        // Mengurutkan sisi berdasarkan bobot
        Collections.sort(edges);
        DisjointSet ds = new DisjointSet(vertices);

        List<EdgeKruskal> mst = new ArrayList<>();
        int mstWeight = 0;

        // Memilih sisi-sisi untuk MST menggunakan algoritma Kruskal
        for (EdgeKruskal edge : edges) {
            int uSet = ds.find(edge.source);
            int vSet = ds.find(edge.destination);
            if (uSet != vSet) {
                mst.add(edge);
                mstWeight += edge.weight;
                ds.union(uSet, vSet);
            }
        }

        // Menampilkan hasil MST
        System.out.println("Edge-edge dalam MST:");
        for (EdgeKruskal edge : mst) {
            System.out.println(edge.source + " - " + edge.destination + " : " + edge.weight);
        }
        System.out.println("Total bobot MST: " + mstWeight);
    }
}
