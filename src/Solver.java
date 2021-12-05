import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;

//TODO controllare se 2 nodi hanno la stessa prio, quale delle 2 devo guardare?
//TODO migliorare heuristic

public class Solver {
    
    private static class BoardComparator implements Comparator<Board> {
        public int compare(Board b1, Board b2) {
            if(b1.getPriority() == b2.getPriority()) {
                if
                return 0; 
            }
            else if(b1.getPriority() < b2.getPriority()) return -1;
            return 1;
        }
    }

    public static String goal = "";
    public static int n = 0;

    public static void generateGoal() {
        for(int i = 1; i < n*n; i++) {
            goal += i + " ";
        }
        goal += 0 + " "; 
    }

    public static boolean isGoal(Board gameNode) { 
        return gameNode.getString().equals(goal);
    }

    public static void main(String[] args)  throws FileNotFoundException {
        long start = System.nanoTime();
        Scanner in = new Scanner(new FileReader(args[0]));
        n = in.nextInt();
        int level = 0;
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
        Board avoid_error = new Board(m_avoid_error, -1, null);
        Board nodo = new Board(m, 0, avoid_error);
        PriorityQueue<Board> q = new PriorityQueue<Board>(new BoardComparator());
        HashMap<String, Integer> dataBank = new HashMap<String, Integer>();
        dataBank.put(nodo.getString(), level);

        while(!isGoal(nodo)) {
            Board[] sons = nodo.generateSons();
            for(int i = 0; i < sons.length; i++) {
                if(!dataBank.containsKey(sons[i].getString())) {
                    q.add(sons[i]);
                    dataBank.put(sons[i].getString(), level);
                }
            }
            level++;
            nodo = q.poll();
        }
        
        //Printing the request, go look the proper PDF in the directory
        System.out.println(nodo.getMoves());
         
        long finish = System.nanoTime();
        System.out.println((double)(finish - start)/1000000000l);
    }
}