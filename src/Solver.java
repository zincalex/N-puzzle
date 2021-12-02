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
//TODO don’t enqueue a neighbor if its board is the same as the board of the previous search node in the game tree.
//TODO controllare se 2 nodi hanno la stessa prio, quale delle 2 devo guardare?
//TODO aggiungere un altra priorità
//TODO vedere e chiedere a Edo se conviene fare tutto in 1 solo oggetto Board

public class Solver {
    //This class allows the priority queue to know which node has the priority over another node
    private static class BoardComparator implements Comparator<Board> {
        public int compare(Board b1, Board b2) {
            if(b1.getPriority() == b2.getPriority()) return 0;
            else if(b1.getPriority() < b2.getPriority()) return -1;
            return 1;
        }
    }

    //Variabile globale per il goal
    public static String goal = "";
    public static String inizio = "";
    public static int n = 0;

    //Method to generate the goal node in string
    public static void generateGoal() {
        for(int i = 1; i < n*n; i++) {
            goal += i + " ";
        }
        goal += 0 + " ";  
    }

    //Generate the root in the game tree
    public static void generateRoot(Board root) { inizio = root.toString(); }

    //is the SearchNode the goal Node?
    public static boolean isGoal(Board gameNode) { 
        return gameNode.toString().equals(goal);
    }

    // test client (see below)
    public static void main(String[] args)  throws FileNotFoundException {
        long start = System.nanoTime();
        Scanner in = new Scanner(new FileReader(args[0]));
        n = in.nextInt();
        generateGoal();
        // TODO da modificare for sure
        int[] strra = new int[(n*n)];
        for(int i = 0; i < strra.length; i++) {
            strra[i] = in.nextInt();
        }
        in.close();
        int[][] m = new int[n][n];
        int[][] m_avoid_error = new int[n][n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = strra[k++];
                m_avoid_error[i][j] = 0;
            } 
        }

        //Resolution of the problem, TODO aggiungere banca dati, es hash qualcosa, per sapere se un nodo è già presente nel game tree
        Board avoid_error = new Board(m_avoid_error, -1, null);
        Board nodo = new Board(m, 0, avoid_error);
        generateRoot(nodo);
        PriorityQueue<Board> q = new PriorityQueue<Board>(new BoardComparator());
        while(!isGoal(nodo)) {
            Board[] sons = nodo.generateSons();
            for(int i = 0; i < sons.length; i++) {
                q.add(sons[i]);
            }
            nodo = q.poll(); 
        }

        //Printing the request, go look the proper PDF in the directory
        System.out.println(nodo.getMoves());
         
        //gestire la stampa
        long finish = System.nanoTime();
        System.out.println((double)(finish - start)/1000000000l);
    
    }
}
