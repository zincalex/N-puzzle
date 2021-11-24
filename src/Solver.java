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
import java.util.Comparator;

public class Solver {

    private class SearchNode {
        private Board b;
        private int moves;
        private SearchNode parent;
        private int priority;

        public SearchNode(Board b, int m, SearchNode p) {
            this.b = b;
            moves = m;
            parent = p;
            priority = m + b.manhattan();
        }

        public int getMoves() { return moves; }
        public int getPriority() { return priority; }
        public Board getParent() { return parent; }
        public boolean isGoal(String goal) { return b.toString().compareTo(goal) == 0; }
        public SearchNode[] generateSons(SearchNode parent) {
            SearchNode[] figli = new SearchNode[4];
            if(parent.b.get0Row() - 1 > 0) {
                int nRow = parent.b.get0Row() - 1;
                Board b1 = b;
                b1.swap0(nRow, parent.b.get0Col());
                SearchNode s1 = new SearchNode(b1, parent.moves++, parent);
                figli[0] = s1;
            }
            if(parent.b.get0Row() + 1 < parent.b.getLength()) {
                int nRow = parent.b.get0Row() + 1;
                Board b2 = b;
                b2.swap0(nRow, parent.b.get0Col());
                SearchNode s2 = new SearchNode(b2, parent.moves++, parent);
                figli[2] = s2;
            }
            if(parent.b.get0Col() + 1 < parent.b.getLength()) {
                int nCol = parent.b.get0Col() + 1;
                Board b3 = b;
                b3.swap0(parent.b.get0Row(), nCol);
                SearchNode s3 = new SearchNode(b3, parent.moves++, parent);
                figli[1] = s3;
            }
            if(parent.b.get0Col() - 1 > 0){
                int nCol = parent.b.get0Col() - 1;
                Board b4 = b;
                b4.swap0(parent.b.get0Row(), nCol);
                SearchNode s4 = new SearchNode(b4, parent.moves++, parent);
                figli[3] = s4;
            }
            return figli;
        }
    }

    //This class allows the priority queue to know which node has the priority over another node
    private class BoardComparator implements Comparator<SearchNode> {
        public int compare(SearchNode b1, SearchNode b2) {
            if(b1.getPriority() == b2.getPriority()) return 0;
            else if(b1.getPriority() < b2.getPriority()) return -1;
            return 1;
        }
    }
    
    //Method to generate the goal node in string
    public static String generateGoal(int n) {
        String goal = "";
        for(int i = 1; i < n*n; i++) {
            goal += i + " ";
        }
        goal += 0 + " ";
        return goal;   
    }

    // test client (see below)
    public static void main(String[] args)  throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader(args[0]));
        int n = in.nextInt();
        final String goal = generateGoal(n);
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
        PriorityQueue<SearchNode> q = new PriorityQueue<>(new BoardComparator());
        q.add(start);
        while(!x.isGoal()) {
            //Risolvere qua

            //usare metodo poll() per rimuovere la testa della coda 
            System.out.println(""); //Stampo la mossa eseguita

        }
        
    }

}
