import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class JohnsonAlgorithm implements PathsFinder {

    @Override
    public int[][] solve(int[][] graph) {
        int n = graph.length;

        // Create a new graph with an additional vertex and zero-weight
        // edges to all other vertices
        int[][] newGraph = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newGraph[i][j] = graph[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            newGraph[i][n] = 0;
            newGraph[n][i] = Integer.MAX_VALUE;
        }
        newGraph[n][n] = 0;

        // running Bellman-Ford algorithm on the new graph to find the
        // shortest paths from the additional vertex to all other vertices
        int[] h = new int[n + 1];
        Arrays.fill(h, Integer.MAX_VALUE);
        h[n] = 0;
        for (int i = 0; i < n; i++) {
            for (int u = 0; u <= n; u++) {
                for (int v = 0; v <= n; v++) {
                    if (newGraph[u][v] != Integer.MAX_VALUE &&
                            h[u] != Integer.MAX_VALUE &&
                            h[u] + newGraph[u][v] < h[v]) {
                        h[v] = h[u] + newGraph[u][v];
                    }
                }
            }
        }

        // re-weight the edges in the original graph using the new vertex weights
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (graph[i][j] != Integer.MAX_VALUE) {
                    graph[i][j] += h[i] - h[j];
                }
            }
        }

        // running Dijkstra's algorithm on each vertex to find the shortest paths
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            dist[i] = dijkstra(graph, i);
        }

        // Revert the weights using the new vertex weights
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][j] != Integer.MAX_VALUE) {
                    dist[i][j] -= h[i] - h[j];
                }
            }
        }

        return dist;
    }

    private int[] dijkstra(int[][] graph, int source) {
        int n = graph.length;
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        PriorityQueue<Integer> queue =
                new PriorityQueue<>(Comparator.comparingInt(a -> dist[a]));
        queue.offer(source);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] != Integer.MAX_VALUE
                        && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    queue.offer(v);
                }
            }
        }

        return dist;
    }
}
