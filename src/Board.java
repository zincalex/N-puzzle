public class Board {
    private int[][] matrix;
    private int row0, col0;
    private int gCost;
    private Board parent;
    private int hCost;
    private String toString;

    public Board(int[][] tiles, int m, Board p) {
        gCost = m;
        parent = p;
        toString = "";
        hCost = 0;
        matrix = new int[Solver.n][Solver.n];

        for (int i = 0; i < Solver.n; i++) { 
            for (int j = 0; j < Solver.n; j++) {
                matrix[i][j] = tiles[i][j];
                toString += tiles[i][j] + " ";
                if(tiles[i][j] == 0) {
                    row0 = i;
                    col0 = j;
                    continue;
                }
            } 
        }
        toString = toString.trim();
        //ci deve essere il linear conflict
    }

    public Board(String tiles, Board avoid_error) {
        gCost = 0;
        toString = tiles;
        matrix = new int[Solver.n][Solver.n];
        parent = avoid_error;
        hCost = 0;
        
        int k = 0;
        String[] insertion = tiles.split(" ");
        for (int i = 0; i < Solver.n; i++) { 
            for (int j = 0; j < Solver.n; j++) {
                matrix[i][j] = Integer.parseInt(insertion[k++]);
                if(matrix[i][j] == 0) {
                    row0 = i;
                    col0 = j;
                    continue;
                }
                hCost += Math.abs(((matrix[i][j] - 1) / Solver.n) - i) + Math.abs(((matrix[i][j] - 1) % Solver.n) - j);
            } 
        }

        //ci deve essere il linear conflict

    }

    public void updateString(int[][] m) {
        toString = "";
        for (int i = 0; i < Solver.n; i++) { 
            for (int j = 0; j < Solver.n; j++) {
                toString += m[i][j] + " ";
            } 
        }
        toString = toString.trim();
    }

    public int priority() { return hCost + gCost; }

    public void swap0(int row, int col) {
        int temp = matrix[row][col];
        matrix[row][col] = 0;
        matrix[row0][col0] = temp;
        hCost = getParent().getHCost() 
                - (Math.abs(((temp - 1) / Solver.n) - row) + Math.abs(((temp - 1) % Solver.n) - col)) 
                + (Math.abs(((temp - 1) / Solver.n) - row0) + Math.abs(((temp - 1) % Solver.n) - col0)); 
        row0 = row;
        col0 = col;
        updateString(matrix);
    }
    
    public Board[] generateSons() {
        byte count = 0;
        Board b1 = null;
        Board b2 = null;
        Board b3 = null;
        Board b4 = null;
        if(row0 - 1 >= 0) { 
            b1 = new Board(matrix, gCost + 1, this);
            b1.swap0(row0 - 1, col0);
            if(!parent.getString().equals(b1.getString())) { 
                count++;
            } 
            else b1 = null;
        }
        if(row0 + 1 < Solver.n) {
            b2 = new Board(matrix, gCost + 1, this);
            b2.swap0(row0 + 1, col0);
            if(!parent.getString().equals(b2.getString())) {
               count++;
            }
            else b2 = null;
        }
        if(col0 + 1 < Solver.n) {
            b3 = new Board(matrix, gCost + 1, this);
            b3.swap0(row0, col0 + 1);
            if(!parent.getString().equals(b3.getString())) {
                count++;
            }
            else b3 = null;
        }
        if(col0 - 1 >= 0){
            b4 = new Board(matrix, gCost + 1, this);
            b4.swap0(row0, col0 - 1);
            if(!parent.getString().equals(b4.getString())) {
                count++;
            }
            else b4 = null;
        }
        Board[] figli = new Board[count];
        byte i = 0;
        if(b1 != null) {
            figli[i++] = b1;
        }
        if(b2 != null) {
            figli[i++] = b2;
        }
        if(b3 != null) {
            figli[i++] = b3;
        }
        if(b4 != null) {
            figli[i++] = b4;
        }
        return figli;
    }

    public int getMoves() { return gCost; }
    public int getHCost() { return hCost; }
    public Board getParent() { return parent; }
    public String getString() { return toString; }
}
