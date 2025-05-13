import java.util.*;

/**
 * Kelas DFS untuk melakukan pencarian jalur dan traversal pada graf tidak berarah.
 */
public class DFS {
    private int vertices; // Jumlah simpul dalam graf
    private List<List<DFS.Edge>> adjacencyList; // Daftar ketetanggaan untuk menyimpan graf

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
     * Konstruktor DFS.
     * @param vertices jumlah simpul dalam graf
     * Inisialisasi adjacency list untuk menyimpan sisi-sisi graf.
     */
    public DFS(int vertices) {
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
     * Melakukan DFS untuk menemukan satu jalur dari simpul start ke simpul end.
     * Menampilkan jalur pertama yang ditemukan.
     * @param start simpul awal
     * @param end simpul tujuan
     */
    public void dfsOnePath(int start, int end) {
        boolean[] visited = new boolean[vertices]; // Menandai simpul yang sudah dikunjungi
        List<Integer> pathList = new ArrayList<>(); // Menyimpan jalur saat ini
        pathList.add(start);
        System.out.println("Salah satu jalur dari node " + start + " ke node " + end + ":");
        if (!dfsUtilOnePath(start, end, visited, pathList)) {
            System.out.println("Tidak ada jalur yang ditemukan.");
        }
    }

    /**
     * Fungsi rekursif untuk DFS mencari satu jalur dari current ke end.
     * @param current simpul saat ini
     * @param end simpul tujuan
     * @param visited array penanda simpul yang sudah dikunjungi
     * @param pathList jalur yang sedang dibangun
     * @return true jika jalur ditemukan, false jika tidak
     */
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

    /**
     * Melakukan DFS traversal mulai dari simpul start,
     * mengunjungi semua simpul yang dapat dijangkau dan menghitung total bobot sisi yang dilalui.
     * @param start simpul awal traversal
     */
    public void dfsTraverseAll(int start) {
        boolean[] visited = new boolean[vertices]; // Penanda simpul yang sudah dikunjungi
        List<Integer> traversalOrder = new ArrayList<>(); // Urutan simpul yang dikunjungi
        int[] totalWeight = new int[1]; // Total bobot sisi yang dilalui, menggunakan array agar bisa diubah di rekursi
        dfsTraverseUtil(start, visited, traversalOrder, totalWeight);
        System.out.println("DFS traversal mulai dari node " + start + ":");
        System.out.println(traversalOrder);
        System.out.println("Total bobot traversal: " + totalWeight[0]);
    }

    /**
     * Fungsi rekursif untuk DFS traversal.
     * @param current simpul saat ini
     * @param visited penanda simpul yang sudah dikunjungi
     * @param traversalOrder urutan simpul yang dikunjungi
     * @param totalWeight total bobot sisi yang dilalui
     */
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
