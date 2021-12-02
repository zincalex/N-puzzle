// test

import java.lang.Math;

public class Board {
    private int[][] matrix;
    private int n;
    private int row0, col0;
    private int moves;
    private Board parent;
    private int priority;

    //costructor + manhattan distance
    public Board(int[][] tiles, int m, Board p) {
        n = tiles.length;
        moves = m;
        parent = p;
        int manh = 0;
        int row = 0;
        int col = 0;
        matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = tiles[i][j];
                if(tiles[i][j] == 0) {
                    row0 = i;
                    col0 = j;
                    continue;
                }
                row = (matrix[i][j] - 1) / n;
                col = (matrix[i][j] - 1) % n;
                manh += Math.abs(row - i) + Math.abs(col - j);
            } 
        }
        priority = manh + moves;
    }

    //looks if a matrix is equal to the inital board
    public boolean isInitial(String init) { return toString().equals(init); }

    // string representation of this board O(n)
    public String toString() {
        String rapresentation = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rapresentation += matrix[i][j] + " ";
            } 
        }
        return rapresentation;
    }

    // number of tiles out of place
    public int hamming() {
        int ham = 0;
        int pos = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(matrix[i][j] != pos) 
                    ham++;
                pos++;
            } 
        }
        return ham - 1; // -1 perchè quando arrivo alla fine della matrice c'è lo 0 e ovviamente dentro il for viene contato
    }

    // Swap element at (row, col) with the 0 cordinates
    public void swap0(int row, int col) {
        int temp = matrix[row][col];
        matrix[row][col] = 0;
        matrix[row0][col0] = temp;
        row0 = row;
        col0 = col;
    }
    
    public int[][] getBoard() { return matrix; }
    public int getMoves() { return moves; }
    public int getPriority() { return priority; }
    public Board getParent() { return parent; }
    public int get0Row() { return row0; }
    public int get0Col() { return col0; }
    public int getLength() { return n; } //TODO pensare di mettere come variabile static globale nel solver per avere accesso qua x tutte le board

    public Board[] generateSons() {
        byte count = 0;
        Board b1 = null;
        Board b2 = null;
        Board b3 = null;
        Board b4 = null;
        if(get0Row() - 1 >= 0) { 
            b1 = new Board(matrix, moves + 1, this);
            b1.swap0(get0Row() - 1, get0Col());
            if(!getParent().toString().equals(b1.toString())) { 
                count++;
            } 
            else b1 = null;
            
        }
        if(get0Row() + 1 < getLength()) {
            b2 = new Board(matrix, moves + 1, this);
            b2.swap0(get0Row() + 1, get0Col());
            if(!getParent().toString().equals(b2.toString())) {
               count++;
            }
            else b2 = null;
        }
        if(get0Col() + 1 < getLength()) {
            b3 = new Board(matrix, moves + 1, this);
            b3.swap0(get0Row(), get0Col() + 1);
            if(!getParent().toString().equals(b3.toString())) {
                count++;
            }
            else b3 = null;
        }
        if(get0Col() - 1 >= 0){
            b4 = new Board(matrix, moves + 1, this);
            b4.swap0(get0Row(), get0Col() - 1);
            if(!getParent().toString().equals(b4.toString())) {
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
