/*
 *  Your test client should take the name of an input file as a command-line
 *  argument and print the minimum number of moves to solve the puzzle and the sequence of
 *  boards from the initial one to the solution
 *
 *  We define a search
 *  node of the game to consist of the following elements: a board, the number of moves made
 *  to reach the board, and a pointer to its parent in the game tree (defined below)
 *
 *
 *
 * 
 *
 * @author Alessandro Viespoli
 * @version
 *
 */
import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Solver {

    private class SearchNode {
        private Board b;
        private int moves;
        private Board parent;
        private int priority;

        public SearchNode(Board b, int m, Board p) {
            this.b = b;
            moves = m;
            parent = p;
            priority = m + b.manhattan();
        }
    }
    // test client (see below)
    public static void main(String[] args)  throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader(args[0]));
        int n = in.nextInt();
        int[] strra = new int[(n*n)];
        for(int i = 0; i < strra.length; i++) {
            strra[i] = in.nextInt();
        }
        in.close();
        int[][] matrix = new int[n][n];
        int k = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = strra[k++];
            } 
        }
        int moves = 0;
        boolean cond = true;
        Board init_board = new Board(matrix);
        SearchNode start = new SearchNode(init_board, moves, null);
        PriorityQueue q = new PriorityQueue<>();
        q.add(start);
        while(cond) {
            //Risolvere qua
            //Stampare 
            System.out.println(""); //Stampo la mossa eseguita

        }
        //cioapsdaf
        
    }

}
