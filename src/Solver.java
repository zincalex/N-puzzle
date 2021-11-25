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
import java.util.Vector;

public class Solver {

    private static class SearchNode {
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

        public Board getBoard() { return b; }
        public int getMoves() { return moves; }
        public int getPriority() { return priority; }
        public SearchNode getParent() { return parent; }

        //Problema con generateSons, è come se non li creasse
        public Vector<SearchNode> generateSons() {
            Vector<SearchNode> figli = new Vector<SearchNode>();
            if(b.get0Row() - 1 >= 0) {
                int nRow = b.get0Row() - 1; 
                Board b1 = new Board(b);
                b1.swap0(nRow, b.get0Col());
                SearchNode s1 = new SearchNode(b1, getMoves() + 1, this);
                figli.add(s1);
            }
            if(b.get0Row() + 1 < b.getLength()) {
                int nRow = b.get0Row() + 1;
                Board b2 = new Board(b);
                b2.swap0(nRow, b.get0Col());
                SearchNode s2 = new SearchNode(b2, getMoves() + 1, this);
                figli.add(s2);
            }
            if(b.get0Col() + 1 < b.getLength()) {
                int nCol = b.get0Col() + 1;
                Board b3 = new Board(b);
                b3.swap0(b.get0Row(), nCol);
                SearchNode s3 = new SearchNode(b3, getMoves() + 1, this);
                figli.add(s3);
            }
            if(b.get0Col() - 1 >= 0){
                int nCol = b.get0Col() - 1;
                Board b4 = new Board(b);
                b4.swap0(b.get0Row(), nCol);
                SearchNode s4 = new SearchNode(b4, getMoves() + 1, this);
                figli.add(s4);
            }
            return figli;
        }
    }

    //This class allows the priority queue to know which node has the priority over another node
    private static class BoardComparator implements Comparator<SearchNode> {
        public int compare(SearchNode b1, SearchNode b2) {
            if(b1.getPriority() == b2.getPriority()) return 0;
            else if(b1.getPriority() < b2.getPriority()) return -1;
            return 1;
        }
    }

    //Variabile globale per il goal
    public static String goal = "";

    //Method to generate the goal node in string
    public static void generateGoal(int n) {
        for(int i = 1; i < n*n; i++) {
            goal += i + " ";
        }
        goal += 0 + " ";  
    }

    //is the SearchNode the goal Node?
    public static boolean isGoal(SearchNode x) { 
        return x.getBoard().toString().equals(goal);
    }

    // test client (see below)
    public static void main(String[] args)  throws FileNotFoundException {
        long start = System.nanoTime();
        Scanner in = new Scanner(new FileReader(args[0]));
        int n = in.nextInt();
        generateGoal(n);
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
        Board init_board = new Board(matrix);
        SearchNode x = new SearchNode(init_board, moves, null);
        PriorityQueue<SearchNode> q = new PriorityQueue<SearchNode>(new BoardComparator());

        while(!isGoal(x)) {
            Vector<SearchNode> sons = x.generateSons();
            for(int i = 0; i < sons.size(); i++) {
                q.add(sons.elementAt(i));
            }
            x = q.poll(); 
        }
        System.out.println(x.getMoves() + 1);
        String[] stampa = new String[x.getMoves() + 1];
        int indx = x.getMoves(); 
        stampa[0] = init_board.toString();
        while(x.getParent() != null) {
            stampa[indx--] = x.getBoard().toString(); 
            x = x.getParent();
        }
        for(int i = 0; i < stampa.length; i++) {
            System.out.println(stampa[i]);
        }
        long finish = System.nanoTime();
        System.out.println((double)(finish - start)/1000000000l);
    }
}
