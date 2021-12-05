// test
public class Board {
    private int[][] matrix;
    private int row0, col0;
    private int moves;
    private Board parent;
    private int priority;
    private String toString;

    public Board(int[][] tiles, int m, Board p) {
        moves = m;
        parent = p;
        int manh = 0;
        toString = "";
        matrix = new int[Solver.n][Solver.n];
        for (int i = 0; i < Solver.n; i++) { 
            for (int j = 0; j < Solver.n; j++) {
                matrix[i][j] = tiles[i][j];
                if(tiles[i][j] == 0) {
                    row0 = i;
                    col0 = j;
                    continue;
                }
                manh += Math.abs(((matrix[i][j] - 1) / Solver.n) - i) + Math.abs(((matrix[i][j] - 1) % Solver.n) - j);
            } 
        }
        generateString(matrix);
        priority = manh + moves;
    }


    public void generateString(int[][] m) {
        toString = "";
        for (int i = 0; i < Solver.n; i++) { 
            for (int j = 0; j < Solver.n; j++) {
                toString += m[i][j] + " ";
            } 
        }
    }
    //row e col sono gli indici dove lo zero del chiamante viene spostato
    public void swap0(int row, int col) {
        int temp = matrix[row][col];
        matrix[row][col] = 0;
        matrix[row0][col0] = temp;
        row0 = row;
        col0 = col;
        generateString(matrix);
    }
    
    public int[][] getBoard() { return matrix; }
    public int getMoves() { return moves; }
    public int getPriority() { return priority; }
    public Board getParent() { return parent; }
    public String getString() { return toString; }
    public int get0Row() { return row0; }
    public int get0Col() { return col0; }

    public Board[] generateSons() {
        byte count = 0;
        Board b1 = null;
        Board b2 = null;
        Board b3 = null;
        Board b4 = null;
        if(row0 - 1 >= 0) { 
            b1 = new Board(matrix, moves + 1, this);
            b1.swap0(row0 - 1, col0);
            if(!parent.getString().equals(b1.getString())) { 
                count++;
            } 
            else b1 = null;
        }
        if(row0 + 1 < Solver.n) {
            b2 = new Board(matrix, moves + 1, this);
            b2.swap0(row0 + 1, col0);
            if(!parent.getString().equals(b2.getString())) {
               count++;
            }
            else b2 = null;
        }
        if(col0 + 1 < Solver.n) {
            b3 = new Board(matrix, moves + 1, this);
            b3.swap0(row0, col0 + 1);
            if(!parent.getString().equals(b3.getString())) {
                count++;
            }
            else b3 = null;
        }
        if(col0 - 1 >= 0){
            b4 = new Board(matrix, moves + 1, this);
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
}

 /*
    // number of tiles out of place
    public int hamming() {
        int ham = 0;
        int pos = 1;
        for (int i = 0; i < Solver.n; i++) {
            for (int j = 0; j < Solver.n; j++) {
                if(matrix[i][j] != pos) 
                    ham++;
                pos++;
            } 
        }
        return ham - 1; // -1 perchè quando arrivo alla fine della matrice c'è lo 0 e ovviamente dentro il for viene contato
    }
    */
