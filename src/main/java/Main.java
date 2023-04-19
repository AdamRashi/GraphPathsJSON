import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of vertices: ");
        int n = scanner.nextInt();

        int[][] matrix = new int[n][n];
        System.out.println("Enter the adjacency matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String val = scanner.next();
                if (val.equals("-")) {
                    matrix[i][j] = Integer.MAX_VALUE;
                } else {
                    matrix[i][j] = Integer.parseInt(val);
                }
            }
        }

        PathsFinder solver1 = new FloydAlgorithm();
        int[][] dist1 = solver1.solve(matrix);
        System.out.println("Shortest paths using Floyd's algorithm:");
        printDistMatrix(dist1);

        PathsFinder solver2 = new JohnsonAlgorithm();
        int[][] dist2 = solver2.solve(matrix);
        System.out.println("Shortest paths using Johnson's algorithm:");
        printDistMatrix(dist2);
    }

    private static void printDistMatrix(int[][] dist) {
        int n = dist.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][j] == Integer.MAX_VALUE) {
                    System.out.print("- ");
                } else {
                    System.out.print(dist[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
