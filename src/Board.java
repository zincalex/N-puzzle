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
                hCost += Math.abs(((matrix[i][j] - 1) / Solver.n) - i) + Math.abs(((matrix[i][j] - 1) % Solver.n) - j); //spostare sotto
            } 
        }
       
        for (int i = 0; i < Solver.n; i++) {
            for (int j = 0; j < Solver.n; j++) {
                int correctRow = (matrix[i][j] -1) / Solver.n;
                int correctCol = (matrix[i][j] -1) % Solver.n;
                if(((correctRow == i) && (correctCol != j)) || ((correctRow != i) && (correctCol == j))) {
                    int otherVal = matrix[correctRow][correctCol];
                    int otherRow = (otherVal -1) / Solver.n;
                    int otherCol = (otherVal -1) % Solver.n;
                    if(otherRow == i && otherCol == j) {
                        hCost++;
                    }
                }
            }
        }   
       /*
        for (int i = 0; i < Solver.n; i++) {
            for (int j = 0; j < Solver.n; j++) {
                int correctRow = (matrix[i][j] -1) / Solver.n;
                int correctCol = (matrix[i][j] -1) % Solver.n;
                //RIGA GIUSTA MA COL SBAGLIATA
                if((correctRow == i) && (correctCol != j)) {
                    for(int x = j+1; x <Solver.n; x++) {
			        int otherVal = matrix[i][x];
                    int otherRow = (otherVal -1) / Solver.n; //sua riga
                    int otherCol = (otherVal -1) % Solver.n; //sua col
			        if(otherRow == correctRow && correctCol > otherCol) {
				        hCost += 2;
		            }   
                    }
                }
		        else if((correctRow != i) && (correctCol == j)) { //COL GIUSTA MA RIGA SBAGLIATA
                    for(int x = i+1; x <Solver.n; x++) {
			            int otherVal = matrix[x][j]; 
                    	int otherRow = (otherVal -1) / Solver.n; //sua riga
                    	int otherCol = (otherVal -1) % Solver.n; //sua col
			            if(otherCol == correctCol && correctRow > otherRow){
				            hCost += 2;
                        }
		            }
                }
            }
        }
        */
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
        
        int correctRow = (temp -1) / Solver.n;
        int correctCol = (temp -1) % Solver.n;
        // controllo se prima c'era conflict, in caso -2
        if(((correctRow == row) && (correctCol != col)) || ((correctRow != row) && (correctCol == col))) {
            int otherVal = matrix[correctRow][correctCol];
            int otherRow = (otherVal -1) / Solver.n;
            int otherCol = (otherVal -1) % Solver.n;
            if(otherRow == row && otherCol == col) {
                hCost = hCost - 2;
            }
        }
        // controllo se dopo c'è conflict, in caso +2
        if(((correctRow == row0) && (correctCol != col0)) || ((correctRow != row0) && (correctCol == col0))) {
            int otherVal = matrix[correctRow][correctCol];
            int otherRow = (otherVal -1) / Solver.n;
            int otherCol = (otherVal -1) % Solver.n;
            if(otherRow == row0 && otherCol == col0) {
                hCost = hCost + 2;
            }
        }
        
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
