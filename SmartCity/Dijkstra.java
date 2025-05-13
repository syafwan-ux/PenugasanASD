import java.util.*;

/**
 * Kelas Dijkstra untuk mencari jarak terpendek dari satu simpul ke semua simpul lain
 * menggunakan algoritma Dijkstra pada graf tidak berarah.
 */
public class Dijkstra {
    private int vertices; // Jumlah simpul dalam graf
    private List<List<Dijkstra.Edge>> adjacencyList; // Daftar ketetanggaan untuk menyimpan graf

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
     * Konstruktor Dijkstra.
     * @param vertices jumlah simpul dalam graf
     * Inisialisasi adjacency list untuk menyimpan sisi-sisi graf.
     */
    public Dijkstra(int vertices) {
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
     * Mencari jarak terpendek dari simpul start ke semua simpul lain menggunakan algoritma Dijkstra.
     * @param start simpul awal
     */
    public void dijkstra(int start) {
        int[] dist = new int[vertices]; // Array jarak terpendek ke setiap simpul
        Arrays.fill(dist, Integer.MAX_VALUE); // Inisialisasi jarak dengan nilai maksimum
        dist[start] = 0; // Jarak ke simpul awal adalah 0

        // PriorityQueue untuk memilih edge dengan bobot terkecil
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        pq.add(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int u = current.destination;

            // Periksa semua tetangga dari simpul u
            for (Edge edge : adjacencyList.get(u)) {
                int v = edge.destination;
                int weight = edge.weight;
                // Jika jarak baru lebih pendek, update jarak dan masukkan ke queue
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.add(new Edge(v, dist[v]));
                }
            }
        }

        // Tampilkan jarak terpendek dari simpul start ke semua simpul lain
        System.out.println("Jarak terpendek dari node " + start + ":");
        for (int i = 0; i < vertices; i++) {
            System.out.println("Ke node " + i + " jarak: " + dist[i]);
        }
    }
}
