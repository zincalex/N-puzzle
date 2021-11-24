// test

import java.lang.Math;

public class Board {

    private int[][] board;
    private int n;
    private int row0, col0;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        board = tiles;
        n = tiles.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(board[i][j] == 0) {
                    row0 = i;
                    col0 = j;
                }
            } 
        }
    }

    // string representation of this board O(n)
    public String toString() {
        String representation = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if((board[i][j] + "") != " ") {
                    representation += board[i][j] + " ";
                }
                else representation += 0 + " ";
            } 
        }
        return representation;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manh = 0;
        int row = 0;
        int col = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(board[i][j] == 0) continue;
                row = (board[i][j] - 1) / n;
                col = (board[i][j] - 1) % n;
                manh += Math.abs(row - i) + Math.abs(col - j);
            } 
        }
        return manh;
    } 

    // Swap element at (row, col) with the 0 cordinates
    public void swap0(int row, int col) {
        int temp = board[row][col];
        board[row][col] = 0;
        board[row0][col0] = temp;
        row0 = row;
        col0 = col;
    }
    public int get0Row() { return row0; }
    public int get0Col() { return col0; }
    public int getLength() { return n; }
}
