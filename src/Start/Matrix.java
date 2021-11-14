package Start;


public class Matrix {
    public int[][] board;

    Matrix(int n){
        board = new int[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                board[i][j] = 0;
            }
        }
        if(n == 6) {

            //black 1
            board[0][1] = 1;
            board[0][2] = 1;
            board[0][3] = 1;
            board[0][4] = 1;
            board[5][1] = 1;
            board[5][2] = 1;
            board[5][3] = 1;
            board[5][4] = 1;

            //white -1
            board[1][0] = -1;
            board[2][0] = -1;
            board[3][0] = -1;
            board[4][0] = -1;
            board[1][5] = -1;
            board[2][5] = -1;
            board[3][5] = -1;
            board[4][5] = -1;

        }

    }

}
