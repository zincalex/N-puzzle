// test

import java.lang.Math;

public class Board {

    private int[][] board;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        board = tiles;
        n = tiles.length;
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

    public int[] ZeroCordinate() {
        int row = 0;
        int col = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(board[i][j] == 0) {
                    row = i;
                    col = j;
                }
            } 
        }
        int[] cordinates = {row, col};
        return cordinates;
    }
}
