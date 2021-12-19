import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;

//TODO migliorare heuristic

public class Solver {

    public static String goal = "";
    public static int n = 0;
    
    private static class BoardComparator implements Comparator<Board> {
        public int compare(Board b1, Board b2) { return b1.priority() - b2.priority(); }
    }

    public static void generateGoal() {
        for(int i = 1; i < n*n; i++) {
            goal += i + " ";
        }
        goal += 0 + " "; 
    }

    public static boolean isGoal(Board gameNode) { 
        return gameNode.getString().equals(goal);
    }

    public static void main(String[] args)  throws IOException {

        if(args.length < 1) {
            System.out.println("Missing Input File.");
            System.exit(1);
        }

        long start = System.nanoTime();
        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        n = Integer.parseInt(in.readLine());
        String first = in.readLine();
        in.close();
        generateGoal();
        int level = 0;

        int[][] null_matrix = new int[n][n];
        Board avoid_error = new Board(null_matrix, -1, null);
        Board root = new Board(first, avoid_error);



        PriorityQueue<Board> q = new PriorityQueue<Board>(new BoardComparator());
        HashMap<String, Integer> visited = new HashMap<String, Integer>();
        visited.put(root.getString(), level);

        while(!isGoal(root)) {
            Board[] sons = root.generateSons();
            for(int i = 0; i < sons.length; i++) {
                if(!visited.containsKey(sons[i].getString())) {
                    q.add(sons[i]);
                    visited.put(sons[i].getString(), level);
                }
            }
            level++;
            root = q.poll();
        }
        
        //Printing the request, go look the proper PDF in the directory
        int mosse = root.getMoves();
        System.out.println(mosse);
        
        String[] moves = new String[mosse + 1];
        for(int i = mosse - 1; i >= 0; i-- ) {
            moves[i] = root.getString();
            root = root.getParent();
        }
        for(int i = 0; i < mosse; i++) {
            System.out.println(moves[i]);
        }
           
        long finish = System.nanoTime();
        System.out.println((double)(finish - start)/1000000000l);
    }
}