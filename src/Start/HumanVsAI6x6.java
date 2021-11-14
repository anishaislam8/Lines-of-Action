package Start;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import Moves.*;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class HumanVsAI6x6 {
    Main main;
    Matrix matrix;

    public static int black;
    public static int white;
    public static boolean[][] potential;
    public Paint[][] color;
    Move move = new Move();
    int turn; //black = 1, white  = -1(AI)
    int disX;
    int disY;
    boolean thinking;
    boolean isEnd;



    public void setMain(Main main) {
        this.main = main;
    }

    public void init() {
        matrix = new Matrix(6);
        potential = new boolean[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                potential[i][j] = false;
            }
        }
        black = 8;
        white = 8;
        color = new Paint[6][6];
        turn = 1;
        disX = -1;
        disY = -1;
        thinking = false;
        isEnd = false;
    }

    @FXML
    public Text whoseTurn, winner, winnerName;
    @FXML
    public Rectangle board00, board01, board02, board03, board04, board05;
    @FXML
    public Rectangle board10, board11, board12, board13, board14, board15;
    @FXML
    public Rectangle board20, board21, board22, board23, board24, board25;
    @FXML
    public Rectangle board30, board31, board32, board33, board34, board35;
    @FXML
    public Rectangle board40, board41, board42, board43, board44, board45;
    @FXML
    public Rectangle board50, board51, board52, board53, board54, board55;


    @FXML
    public Circle c00, c01, c02, c03, c04, c05;
    @FXML
    public Circle c10, c11, c12, c13, c14, c15;
    @FXML
    public Circle c20, c21, c22, c23, c24, c25;
    @FXML
    public Circle c30, c31, c32, c33, c34, c35;
    @FXML
    public Circle c40, c41, c42, c43, c44, c45;
    @FXML
    public Circle c50, c51, c52, c53, c54, c55;

    @FXML
    Button pass;


    public boolean hasMove(int turn) {
        int k = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (matrix.board[i][j] == turn) {
                    ArrayList<Integer> vertical = move.verticalMove(i, j, matrix.board);
                    ArrayList<Integer> horizontal = move.horizontalMove(i, j, matrix.board);
                    ArrayList<Integer> leftDiagonal = move.leftDiagonalMove(i, j, matrix.board);
                    ArrayList<Integer> rightDiagonal = move.rightDiagonalMove(i, j, matrix.board);

                    if (vertical.size() == 0 && horizontal.size() == 0 && leftDiagonal.size() == 0 && rightDiagonal.size() == 0) {
                        k += 1;
                    }
                }
            }
        }

        if (turn == 1) {
            if (k == black) {
                return false;
            }
        } else {
            if (k == white) {
                return false;
            }
        }
        return true;
    }

    public void onPassPressed() {
        if (turn == 1) {
            turn = -1;
            whoseTurn.setText("Turn : White");
        } else {
            turn = 1;
            whoseTurn.setText("Turn : Black");
        }
        pass.setVisible(false);
    }

    public void moveAndLightUp(int i, int j) {
        if (matrix.board[i][j] ==1) { //black chara guti chapa jabe na
            if (thinking) {
                turnOffPotentialBoard();
            } else {
                thinking = true;
            }
            ArrayList<Integer> vertical = move.verticalMove(i, j, matrix.board);
            if (vertical.size() != 0) {
                lightUpBoards(vertical, i, j, "vertical");
                disX = i;
                disY = j;
            }

            ArrayList<Integer> horizontal = move.horizontalMove(i, j, matrix.board);
            if (horizontal.size() != 0) {
                lightUpBoards(horizontal, i, j, "horizontal");
                disX = i;
                disY = j;
            }


            ArrayList<Integer> leftDiagonal = move.leftDiagonalMove(i, j, matrix.board);
            if (leftDiagonal.size() != 0) {
                lightUpBoards(leftDiagonal, i, j, "leftDiagonal");
                disX = i;
                disY = j;
            }

            ArrayList<Integer> rightDiagonal = move.rightDiagonalMove(i, j, matrix.board);
            if (rightDiagonal.size() != 0) {
                lightUpBoards(rightDiagonal, i, j, "rightDiagonal");
                disX = i;
                disY = j;
            }

        }
    }

    public int[] stringToPosition(String s) {
        String[] arr = s.split(" ");
        int result[] = new int[2];
        result[0] = Integer.parseInt(arr[0]);
        result[1] = Integer.parseInt(arr[1]);

        return result;
    }

    public String positionToString(int i, int j) {
        String s = i + " " + j;
        return s;
    }

    public int winnerInnerWorker(ArrayList<String> test, int turn) {
        ArrayList<String> done = new ArrayList<>();

        while (test.size() != 0) {
            int[] checker = stringToPosition(test.get(0));
            int m = checker[0];
            int n = checker[1];

            if (m - 1 >= 0 && matrix.board[m - 1][n] == turn) {
                if (!done.contains(positionToString(m - 1, n)) && !test.contains(positionToString(m - 1, n))) {
                    test.add(positionToString(m - 1, n));
                }
            }
            if (m + 1 <= 5 && matrix.board[m + 1][n] == turn) {
                if (!done.contains(positionToString(m + 1, n)) && !test.contains(positionToString(m + 1, n))) {
                    test.add(positionToString(m + 1, n));
                }
            }
            if (n - 1 >= 0 && matrix.board[m][n - 1] == turn) {
                if (!done.contains(positionToString(m, n - 1)) && !test.contains(positionToString(m, n - 1))) {
                    test.add(positionToString(m, n - 1));
                }
            }
            if (n + 1 <= 5 && matrix.board[m][n + 1] == turn) {
                if (!done.contains(positionToString(m, n + 1)) && !test.contains(positionToString(m, n + 1))) {
                    test.add(positionToString(m, n + 1));
                }
            }
            if (m - 1 >= 0 && n + 1 <= 5 && matrix.board[m - 1][n + 1] == turn) {
                if (!done.contains(positionToString(m - 1, n + 1)) && !test.contains(positionToString(m - 1, n + 1))) {
                    test.add(positionToString(m - 1, n + 1));
                }
            }
            if (m + 1 <= 5 && n - 1 >= 0 && matrix.board[m + 1][n - 1] == turn) {
                if (!done.contains(positionToString(m + 1, n - 1)) && !test.contains(positionToString(m + 1, n - 1))) {
                    test.add(positionToString(m + 1, n - 1));
                }
            }
            if (m - 1 >= 0 && n - 1 >= 0 && matrix.board[m - 1][n - 1] == turn) {
                if (!done.contains(positionToString(m - 1, n - 1)) && !test.contains(positionToString(m - 1, n - 1))) {
                    test.add(positionToString(m - 1, n - 1));
                }
            }
            if (m + 1 <= 5 && n + 1 <= 5 && matrix.board[m + 1][n + 1] == turn) {
                if (!done.contains(positionToString(m + 1, n + 1)) && !test.contains(positionToString(m + 1, n + 1))) {
                    test.add(positionToString(m + 1, n + 1));
                }
            }

            done.add(test.get(0));
            test.remove(0);

        }

        return done.size();
    }

    public int hasWon(int turn) {
        //check if turn has won
        if (turn == 1) {
            if (black == 1) { //ektai black guti
                winner.setVisible(true);
                winnerName.setText("Black");
                winnerName.setVisible(true);
                whoseTurn.setVisible(false);
                return 1;
            } else if (white == 1) {
                winner.setVisible(true);
                winnerName.setText("White");
                winnerName.setVisible(true);
                whoseTurn.setVisible(false);
                return -1;
            }
        } else {
            if (white == 1) { //ektai white guti
                winner.setVisible(true);
                winnerName.setText("White");
                winnerName.setVisible(true);
                whoseTurn.setVisible(false);
                return -1;
            } else if (black == 1) {
                winner.setVisible(true);
                winnerName.setText("Black");
                winnerName.setVisible(true);
                whoseTurn.setVisible(false);
                return 1;
            }
        }
        //each color has more than 1 checkers


        //first check if I am connected
        ArrayList<String> test = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (matrix.board[i][j] == turn) {
                    String s = i + " " + j;
                    test.add(s);
                    k = 1;
                    break;
                }
            }
            if (k == 1) {
                break;
            }
        }

        int connected = winnerInnerWorker(test, turn);
        //System.out.println("Connected " + turn + "'s = " + connected);

        if (turn == 1) {//ami jitsi
            if (connected == black) {
                winner.setVisible(true);
                winnerName.setText("Black");
                winnerName.setVisible(true);
                whoseTurn.setVisible(false);
                return 1;

            }
        } else {
            if (connected == white) {
                winner.setVisible(true);
                winnerName.setText("White");
                winnerName.setVisible(true);
                whoseTurn.setVisible(false);
                return -1;

            }
        }

        //eikhane asha mane ami jiti nai, let's check je
        //check amar move er karone onno ta connected hoye gelo ki na
        int other = (turn == 1 ? -1 : 1);

        test.clear();
        k = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (matrix.board[i][j] == other) {
                    String s = i + " " + j;
                    test.add(s);
                    k = 1;
                    break;
                }
            }
            if (k == 1) {
                break;
            }
        }

        connected = winnerInnerWorker(test, other);
        //System.out.println("Connected " + other + "'s = " + connected);


        if (other == 1) {// other jitse
            if (connected == black) {
                winner.setVisible(true);
                winnerName.setText("Black");
                winnerName.setVisible(true);
                whoseTurn.setVisible(false);
                return 1;

            }
        } else {
            if (connected == white) {
                winner.setVisible(true);
                winnerName.setText("White");
                winnerName.setVisible(true);
                whoseTurn.setVisible(false);
                return -1;

            }
        }

        //egula kisui na hole keu e jite nai :)
        return 0;
    }


    //circle a click kora mane route dekhao
    public void on00Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[0][0]) {
                    board00Clicked();
                } else {
                    moveAndLightUp(0, 0);
                    //turnOffNotValidBoardColor(0, 0);
                }
            }
        }
    }

    public void on01Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[0][1]) {
                    board01Clicked();
                } else {
                    moveAndLightUp(0, 1);
                    //turnOffNotValidBoardColor(0, 1);
                }
            }
        }
    }

    public void on02Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[0][2]) {

                    board02Clicked();
                } else {
                    moveAndLightUp(0, 2);
                    //turnOffNotValidBoardColor(0, 2);
                }
            }
        }
    }

    public void on03Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[0][3]) {

                    board03Clicked();
                } else {
                    moveAndLightUp(0, 3);
                    //turnOffNotValidBoardColor(0, 3);
                }
            }
        }

    }

    public void on04Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[0][4]) {

                    board04Clicked();
                } else {
                    moveAndLightUp(0, 4);
                    //turnOffNotValidBoardColor(0, 4);
                }
            }
        }
    }

    public void on05Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[0][5]) {

                    board05Clicked();
                } else {
                    moveAndLightUp(0, 5);
                    //turnOffNotValidBoardColor(0, 5);
                }
            }
        }
    }


    public void on10Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[1][0]) {

                    board10Clicked();
                } else {
                    moveAndLightUp(1, 0);
                    //turnOffNotValidBoardColor(1, 0);
                }
            }
        }
    }

    public void on11Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[1][1]) {

                    board11Clicked();
                } else {
                    moveAndLightUp(1, 1);
                    //turnOffNotValidBoardColor(1, 1);
                }
            }
        }
    }

    public void on12Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[1][2]) {

                    board12Clicked();
                } else {
                    moveAndLightUp(1, 2);
                    //turnOffNotValidBoardColor(1, 2);
                }
            }
        }
    }

    public void on13Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[1][3]) {

                    board13Clicked();
                } else {
                    moveAndLightUp(1, 3);
                    //turnOffNotValidBoardColor(1, 3);
                }
            }
        }
    }

    public void on14Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[1][4]) {

                    board14Clicked();
                } else {
                    moveAndLightUp(1, 4);
                    //turnOffNotValidBoardColor(1, 4);
                }
            }
        }
    }

    public void on15Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[1][5]) {

                    board15Clicked();
                } else {
                    moveAndLightUp(1, 5);
                    //turnOffNotValidBoardColor(1, 5);
                }
            }
        }
    }

    public void on20Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[2][0]) {

                    board20Clicked();
                } else {
                    moveAndLightUp(2, 0);
                    //turnOffNotValidBoardColor(2, 0);
                }
            }
        }
    }

    public void on21Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[2][1]) {

                    board21Clicked();
                } else {
                    moveAndLightUp(2, 1);
                    //turnOffNotValidBoardColor(2, 1);
                }
            }
        }
    }

    public void on22Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[2][2]) {

                    board22Clicked();
                } else {
                    moveAndLightUp(2, 2);
                    //turnOffNotValidBoardColor(2, 2);
                }
            }
        }
    }

    public void on23Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[2][3]) {

                    board23Clicked();
                } else {
                    moveAndLightUp(2, 3);
                    //turnOffNotValidBoardColor(2, 3);
                }
            }
        }
    }

    public void on24Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[2][4]) {

                    board24Clicked();
                } else {
                    moveAndLightUp(2, 4);
                    //turnOffNotValidBoardColor(2, 4);
                }
            }
        }
    }

    public void on25Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[2][5]) {

                    board25Clicked();
                } else {
                    moveAndLightUp(2, 5);
                    //turnOffNotValidBoardColor(2, 5);
                }
            }

        }
    }


    public void on30Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[3][0]) {

                    board30Clicked();
                } else {
                    moveAndLightUp(3, 0);
                    //turnOffNotValidBoardColor(3, 0);
                }
            }
        }
    }

    public void on31Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[3][1]) {

                    board31Clicked();
                } else {
                    moveAndLightUp(3, 1);
                    //turnOffNotValidBoardColor(3, 1);
                }
            }
        }
    }

    public void on32Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[3][2]) {

                    board32Clicked();
                } else {
                    moveAndLightUp(3, 2);
                    //turnOffNotValidBoardColor(3, 2);
                }
            }
        }
    }

    public void on33Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[3][3]) {

                    board33Clicked();
                } else {
                    moveAndLightUp(3, 3);
                    //turnOffNotValidBoardColor(3, 3);
                }
            }
        }
    }

    public void on34Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[3][4]) {

                    board34Clicked();
                } else {
                    moveAndLightUp(3, 4);
                    //turnOffNotValidBoardColor(3, 4);
                }
            }
        }
    }

    public void on35Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[3][5]) {

                    board35Clicked();
                } else {
                    moveAndLightUp(3, 5);
                    //turnOffNotValidBoardColor(3, 5);
                }
            }
        }
    }

    public void on40Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[4][0]) {

                    board40Clicked();
                } else {
                    moveAndLightUp(4, 0);
                    //turnOffNotValidBoardColor(4, 0);
                }
            }
        }
    }

    public void on41Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[4][1]) {

                    board41Clicked();
                } else {
                    moveAndLightUp(4, 1);
                    //turnOffNotValidBoardColor(4, 1);
                }
            }
        }
    }

    public void on42Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[4][2]) {

                    board42Clicked();
                } else {
                    moveAndLightUp(4, 2);
                    //turnOffNotValidBoardColor(4, 2);
                }
            }
        }
    }

    public void on43Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[4][3]) {

                    board43Clicked();
                } else {
                    moveAndLightUp(4, 3);
                    //turnOffNotValidBoardColor(4, 3);
                }
            }
        }
    }

    public void on44Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[4][4]) {

                    board44Clicked();
                } else {
                    moveAndLightUp(4, 4);
                    //turnOffNotValidBoardColor(4, 4);
                }
            }
        }
    }

    public void on45Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[4][5]) {

                    board45Clicked();
                } else {
                    moveAndLightUp(4, 5);
                    //turnOffNotValidBoardColor(4, 5);
                }
            }
        }
    }

    public void on50Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[5][0]) {

                    board50Clicked();
                } else {
                    moveAndLightUp(5, 0);
                    //turnOffNotValidBoardColor(5, 0);
                }
            }
        }
    }

    public void on51Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[5][1]) {

                    board51Clicked();
                } else {
                    moveAndLightUp(5, 1);
                    //turnOffNotValidBoardColor(5, 1);
                }
            }
        }
    }

    public void on52Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[5][2]) {

                    board52Clicked();
                } else {
                    moveAndLightUp(5, 2);
                    //turnOffNotValidBoardColor(5, 2);
                }
            }
        }
    }

    public void on53Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[5][3]) {

                    board53Clicked();
                } else {
                    moveAndLightUp(5, 3);
                    //turnOffNotValidBoardColor(5, 3);
                }
            }
        }
    }

    public void on54Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[5][4]) {

                    board54Clicked();
                } else {
                    moveAndLightUp(5, 4);
                    //turnOffNotValidBoardColor(5, 4);
                }
            }
        }
    }

    public void on55Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[5][5]) {

                    board55Clicked();
                } else {
                    moveAndLightUp(5, 5);
                    //turnOffNotValidBoardColor(5, 5);
                }
            }
        }
    }


    public void print() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(matrix.board[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }


    public void countBlack(){
        int count = 0;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if(matrix.board[i][j] == 1){
                    count+=1;
                }
            }
        }
        black = count;
        System.out.println("B: " + black);
    }

    public void countWhite(){
        int count = 0;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if(matrix.board[i][j] == -1){
                    count+=1;
                }
            }
        }
        white = count;
        System.out.println("W: " + white);
    }


    public void AITurn(){
        /* Tasks of AI
        1) At first check if AI has a move

        2) Then apply minimax algo to find a move
         */
        boolean a = hasMove(turn);//dekho amar move ase ki na
        if (!a) {
            //move nai
            turn = 1;
            whoseTurn.setText("No move for AI! Turn : Black");
        }else{
            //move ase, apply minimax algo
            int[][] result = new int[6][6];
            Minimax obj = new Minimax(5);
            int[][] copy = new int[6][6];
            for(int i = 0; i< 6; i++){
                for(int j = 0; j < 6; j++){
                    copy[i][j] = matrix.board[i][j];
                }
            }

            result = obj.bestMove(copy, 5, Double.MIN_VALUE, Double.MAX_VALUE, -1);

            System.out.println("Initial : ");
            for(int i = 0; i< 6; i++){
                for(int j = 0; j < 6; j++){
                    System.out.print(matrix.board[i][j] + " ");
                }
                System.out.println();
            }

            System.out.println("Result : ");
            for(int i = 0; i< 6; i++){
                for(int j = 0; j < 6; j++){
                    System.out.print(result[i][j] + " ");
                }
                System.out.println();
            }

            //find out disX, disY, and x, y
            int newX = 0;
            int newY = 0;
            for(int i = 0; i < 6; i++){
                for(int j = 0; j < 6; j++){
                    if(matrix.board[i][j] == -1 && result[i][j] == 0){
                        disX = i;
                        disY = j;
                    }
                    if(matrix.board[i][j] != -1 && result[i][j] == -1){
                        newX = i;
                        newY = j;
                    }
                }
            }

            AIMove(newX,newY);
        }
    }

    //potential board a click kora mane move koro okhane
    //ar ekbar potential board choose korar shathe shathe onno board off
    public void board00Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[0][0]) {
                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    c00.setFill(Color.BLACK);
                    matrix.board[0][0] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    disappear(disX, disY);

                    c00.setVisible(true);

                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    }
                    else{
                        AITurn();
                    }
                }
            }
        }
    }

    public void board01Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[0][1]) {
//
                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    c01.setFill(Color.BLACK);
                    matrix.board[0][1] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;

                    disappear(disX, disY);

                    c01.setVisible(true);

                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    }else{
                        AITurn();
                    }
                }
            }
        }
    }

    public void board02Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[0][2]) {
                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;
                    //if (turn == 1) {
                    c02.setFill(Color.BLACK);
                    matrix.board[0][2] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;


//                } else {
//                    c02.setFill(Color.WHITE);
//                    matrix.board[0][2] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                }
                    disappear(disX, disY);

                    c02.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    }else{
                        AITurn();
                    }
                }
            }
        }
    }

    public void board03Clicked() {
        if (!isEnd) {
            if(turn == 1) {
                if (potential[0][3]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    c03.setFill(Color.BLACK);
                    matrix.board[0][3] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;

                    disappear(disX, disY);

                    c03.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    }else{
                        AITurn();
                    }
                }
            }
        }
    }

    public void board04Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[0][4]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;
//                    if (turn == 1) {
                        c04.setFill(Color.BLACK);
                        matrix.board[0][4] = 1;
                        //countBlack();
                        countWhite();
                        whoseTurn.setText("Turn : White");
                        win = hasWon(turn);
                        turn = -1;
//                    } else {
//                        c04.setFill(Color.WHITE);
//                        matrix.board[0][4] = -1;
//                        countBlack();
//                        //countWhite();
//                        whoseTurn.setText("Turn : Black");
//                        win = hasWon(turn);
//                        turn = 1;
//                    }
                    disappear(disX, disY);

                    c04.setVisible(true);
//                    boolean a = hasMove(turn);
//                    if (!a) {
//                        pass.setVisible(true);
//                    }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    }else{
                        AITurn();
                    }
                }
            }
        }
    }

    public void board05Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[0][5]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

//                if (turn == 1) {
                    c05.setFill(Color.BLACK);
                    matrix.board[0][5] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c05.setFill(Color.WHITE);
//                    matrix.board[0][5] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c05.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    }else{
                        AITurn();
                    }
                }
            }
        }
    }


    public void board10Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[1][0]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

//                if (turn == 1) {
                    c10.setFill(Color.BLACK);
                    matrix.board[1][0] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    // whoseTurn.setText("Turn : White");
//                } else {
//                    c10.setFill(Color.WHITE);
//                    matrix.board[1][0] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c10.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board11Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[1][1]) {
                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

//                if (turn == 1) {
                    c11.setFill(Color.BLACK);
                    matrix.board[1][1] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c11.setFill(Color.WHITE);
//                    matrix.board[1][1] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c11.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }
                }
            }
        }
    }

    public void board12Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[1][2]) {
                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

//                if (turn == 1) {
                    c12.setFill(Color.BLACK);
                    matrix.board[1][2] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c12.setFill(Color.WHITE);
//                    matrix.board[1][2] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c12.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }
                }
            }
        }
    }

    public void board13Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[1][3]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c13.setFill(Color.BLACK);
                    matrix.board[1][3] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c13.setFill(Color.WHITE);
//                    matrix.board[1][3] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c13.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    }else{
                        AITurn();
                    }
                }
            }
        }
    }

    public void board14Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[1][4]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c14.setFill(Color.BLACK);
                    matrix.board[1][4] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c14.setFill(Color.WHITE);
//                    matrix.board[1][4] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c14.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }
                }
            }
        }
    }

    public void board15Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[1][5]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c15.setFill(Color.BLACK);
                    matrix.board[1][5] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c15.setFill(Color.WHITE);
//                    matrix.board[1][5] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c15.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }
                }
            }
        }
    }

    public void board20Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[2][0]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c20.setFill(Color.BLACK);
                    matrix.board[2][0] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c20.setFill(Color.WHITE);
//                    matrix.board[2][0] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c20.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }
                }
            }
        }
    }

    public void board21Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[2][1]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c21.setFill(Color.BLACK);
                    matrix.board[2][1] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c21.setFill(Color.WHITE);
//                    matrix.board[2][1] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c21.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }
                }
            }
        }
    }

    public void board22Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[2][2]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c22.setFill(Color.BLACK);
                    matrix.board[2][2] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c22.setFill(Color.WHITE);
//                    matrix.board[2][2] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c22.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }
                }
            }
        }
    }

    public void board23Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[2][3]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c23.setFill(Color.BLACK);
                    matrix.board[2][3] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c23.setFill(Color.WHITE);
//                    matrix.board[2][3] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c23.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }
                }
            }
        }
    }

    public void board24Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[2][4]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c24.setFill(Color.BLACK);
                    matrix.board[2][4] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c24.setFill(Color.WHITE);
//                    matrix.board[2][4] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c24.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }
                }
            }
        }
    }

    public void board25Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[2][5]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c25.setFill(Color.BLACK);
                    matrix.board[2][5] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c25.setFill(Color.WHITE);
//                    matrix.board[2][5] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c25.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }
                }
            }
        }
    }


    public void board30Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[3][0]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c30.setFill(Color.BLACK);
                    matrix.board[3][0] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c30.setFill(Color.WHITE);
//                    matrix.board[3][0] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c30.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }
                }
            }
        }
    }

    public void board31Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[3][1]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c31.setFill(Color.BLACK);
                    matrix.board[3][1] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c31.setFill(Color.WHITE);
//                    matrix.board[3][1] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c31.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board32Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[3][2]) {

                    int win = 0;

                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c32.setFill(Color.BLACK);
                    matrix.board[3][2] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c32.setFill(Color.WHITE);
//                    matrix.board[3][2] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c32.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board33Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[3][3]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c33.setFill(Color.BLACK);
                    matrix.board[3][3] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c33.setFill(Color.WHITE);
//                    matrix.board[3][3] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c33.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board34Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[3][4]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c34.setFill(Color.BLACK);
                    matrix.board[3][4] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c34.setFill(Color.WHITE);
//                    matrix.board[3][4] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c34.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }
                }
            }
        }
    }

    public void board35Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[3][5]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c35.setFill(Color.BLACK);
                    matrix.board[3][5] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c35.setFill(Color.WHITE);
//                    matrix.board[3][5] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c35.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();

                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board40Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[4][0]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c40.setFill(Color.BLACK);
                    matrix.board[4][0] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c40.setFill(Color.WHITE);
//                    matrix.board[4][0] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c40.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();

                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board41Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[4][1]) {
                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c41.setFill(Color.BLACK);
                    matrix.board[4][1] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c41.setFill(Color.WHITE);
//                    matrix.board[4][1] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c41.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();

                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board42Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[4][2]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c42.setFill(Color.BLACK);
                    matrix.board[4][2] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c42.setFill(Color.WHITE);
//                    matrix.board[4][2] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c42.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();

                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board43Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[4][3]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c43.setFill(Color.BLACK);
                    matrix.board[4][3] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c43.setFill(Color.WHITE);
//                    matrix.board[4][3] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c43.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();

                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board44Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[4][4]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    // if (turn == 1) {
                    c44.setFill(Color.BLACK);
                    matrix.board[4][4] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c44.setFill(Color.WHITE);
//                    matrix.board[4][4] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c44.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();

                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board45Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[4][5]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c45.setFill(Color.BLACK);
                    matrix.board[4][5] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c45.setFill(Color.WHITE);
//                    matrix.board[4][5] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c45.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();

                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board50Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[5][0]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c50.setFill(Color.BLACK);
                    matrix.board[5][0] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c50.setFill(Color.WHITE);
//                    matrix.board[5][0] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c50.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board51Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[5][1]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c51.setFill(Color.BLACK);
                    matrix.board[5][1] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;

                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c51.setFill(Color.WHITE);
//                    matrix.board[5][1] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c51.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board52Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[5][2]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c52.setFill(Color.BLACK);
                    matrix.board[5][2] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c52.setFill(Color.WHITE);
//                    matrix.board[5][2] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c52.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board53Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[5][3]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c53.setFill(Color.BLACK);
                    matrix.board[5][3] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c53.setFill(Color.WHITE);
//                    matrix.board[5][3] = -1;
//                    countBlack();
//                    // countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c53.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board54Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[5][4]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c54.setFill(Color.BLACK);
                    matrix.board[5][4] = 1;
                    // countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c54.setFill(Color.WHITE);
//                    matrix.board[5][4] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c54.setVisible(true);
//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }

                }
            }
        }
    }

    public void board55Clicked() {
        if (!isEnd) {
            if (turn == 1) {
                if (potential[5][5]) {

                    int win = 0;
                    turnOffPotentialBoard();

                    matrix.board[disX][disY] = 0;

                    //if (turn == 1) {
                    c55.setFill(Color.BLACK);
                    matrix.board[5][5] = 1;
                    //countBlack();
                    countWhite();
                    whoseTurn.setText("Turn : White");
                    win = hasWon(turn);
                    turn = -1;
                    //whoseTurn.setText("Turn : White");
//                } else {
//                    c55.setFill(Color.WHITE);
//                    matrix.board[5][5] = -1;
//                    countBlack();
//                    //countWhite();
//                    whoseTurn.setText("Turn : Black");
//                    win = hasWon(turn);
//                    turn = 1;
//                    //whoseTurn.setText("Turn : Black");
//                }
                    disappear(disX, disY);

                    c55.setVisible(true);

//                boolean a = hasMove(turn);
//                if (!a) {
//                    pass.setVisible(true);
//                }
                    print();
                    if (win == 1 || win == -1) {
                        isEnd = true;
                        pass.setVisible(false);
                    } else {
                        AITurn();
                    }
                }
            }
        }
    }


    public void drawBoard() {
        c01.setFill(BLACK);
        c02.setFill(BLACK);
        c03.setFill(BLACK);
        c04.setFill(BLACK);
        c51.setFill(BLACK);
        c52.setFill(BLACK);
        c53.setFill(BLACK);
        c54.setFill(BLACK);
        c10.setFill(WHITE);
        c20.setFill(WHITE);
        c30.setFill(WHITE);
        c40.setFill(WHITE);
        c15.setFill(WHITE);
        c25.setFill(WHITE);
        c35.setFill(WHITE);
        c45.setFill(WHITE);
        c01.setVisible(true);
        c02.setVisible(true);
        c03.setVisible(true);
        c04.setVisible(true);
        c51.setVisible(true);
        c52.setVisible(true);
        c53.setVisible(true);
        c54.setVisible(true);
        c10.setVisible(true);
        c20.setVisible(true);
        c30.setVisible(true);
        c40.setVisible(true);
        c15.setVisible(true);
        c25.setVisible(true);
        c35.setVisible(true);
        c45.setVisible(true);

        whoseTurn.setText("Turn : Black");
        setColor();

        System.out.println();
    }

    public void setColor() {
        color[0][0] = board00.getFill();
        color[0][1] = board01.getFill();
        color[0][2] = board02.getFill();
        color[0][3] = board03.getFill();
        color[0][4] = board04.getFill();
        color[0][5] = board05.getFill();

        color[1][0] = board10.getFill();
        color[1][1] = board11.getFill();
        color[1][2] = board12.getFill();
        color[1][3] = board13.getFill();
        color[1][4] = board14.getFill();
        color[1][5] = board15.getFill();

        color[2][0] = board20.getFill();
        color[2][1] = board21.getFill();
        color[2][2] = board22.getFill();
        color[2][3] = board23.getFill();
        color[2][4] = board24.getFill();
        color[2][5] = board25.getFill();

        color[3][0] = board30.getFill();
        color[3][1] = board31.getFill();
        color[3][2] = board32.getFill();
        color[3][3] = board33.getFill();
        color[3][4] = board34.getFill();
        color[3][5] = board35.getFill();

        color[4][0] = board40.getFill();
        color[4][1] = board41.getFill();
        color[4][2] = board42.getFill();
        color[4][3] = board43.getFill();
        color[4][4] = board44.getFill();
        color[4][5] = board45.getFill();

        color[5][0] = board50.getFill();
        color[5][1] = board51.getFill();
        color[5][2] = board52.getFill();
        color[5][3] = board53.getFill();
        color[5][4] = board54.getFill();
        color[5][5] = board55.getFill();
    }

    public void turnOffPotentialBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (potential[i][j]) {
                    potential[i][j] = false;
                    turnOff(i, j);
                }
            }
        }
    }


    public void lightUpBoards(ArrayList<Integer> array, int i, int j, String s) {
        if (s.equalsIgnoreCase("vertical")) {
            for (int k = 0; k < array.size(); k++) {
                light(array.get(k), j);
            }

        } else if (s.equalsIgnoreCase("horizontal")) {
            for (int k = 0; k < array.size(); k++) {
                light(i, array.get(k));
            }

        } else if (s.equalsIgnoreCase("leftDiagonal") || s.equalsIgnoreCase("rightDiagonal")) {
            for (int k = 0; k < array.size(); k = k + 2) {
                light(array.get(k), array.get(k + 1));
            }
        }

    }

    public void disappear(int i, int j) {
        if (i == 0) {
            switch (j) {
                case 0:
                    c00.setVisible(false);
                    break;
                case 1:
                    c01.setVisible(false);
                    break;
                case 2:
                    c02.setVisible(false);
                    break;
                case 3:
                    c03.setVisible(false);
                    break;
                case 4:
                    c04.setVisible(false);
                    break;
                case 5:
                    c05.setVisible(false);
                    break;
            }
        } else if (i == 1) {
            switch (j) {
                case 0:
                    c10.setVisible(false);
                    break;
                case 1:
                    c11.setVisible(false);
                    break;
                case 2:
                    c12.setVisible(false);
                    break;
                case 3:
                    c13.setVisible(false);
                    break;
                case 4:
                    c14.setVisible(false);
                    break;
                case 5:
                    c15.setVisible(false);
                    break;
            }
        } else if (i == 2) {
            switch (j) {
                case 0:
                    c20.setVisible(false);
                    break;
                case 1:
                    c21.setVisible(false);
                    break;
                case 2:
                    c22.setVisible(false);
                    break;
                case 3:
                    c23.setVisible(false);
                    break;
                case 4:
                    c24.setVisible(false);
                    break;
                case 5:
                    c25.setVisible(false);
                    break;
            }
        } else if (i == 3) {
            switch (j) {
                case 0:
                    c30.setVisible(false);
                    break;
                case 1:
                    c31.setVisible(false);
                    break;
                case 2:
                    c32.setVisible(false);
                    break;
                case 3:
                    c33.setVisible(false);
                    break;
                case 4:
                    c34.setVisible(false);
                    break;
                case 5:
                    c35.setVisible(false);
                    break;
            }
        } else if (i == 4) {
            switch (j) {
                case 0:
                    c40.setVisible(false);
                    break;
                case 1:
                    c41.setVisible(false);
                    break;
                case 2:
                    c42.setVisible(false);
                    break;
                case 3:
                    c43.setVisible(false);
                    break;
                case 4:
                    c44.setVisible(false);
                    break;
                case 5:
                    c45.setVisible(false);
                    break;
            }
        } else if (i == 5) {
            switch (j) {
                case 0:
                    c50.setVisible(false);
                    break;
                case 1:
                    c51.setVisible(false);
                    break;
                case 2:
                    c52.setVisible(false);
                    break;
                case 3:
                    c53.setVisible(false);
                    break;
                case 4:
                    c54.setVisible(false);
                    break;
                case 5:
                    c55.setVisible(false);
                    break;
            }
        }


    }


    public void light(int i, int j) {
        potential[i][j] = true;
        if (i == 0) {
            switch (j) {
                case 0:
                    board00.setFill(Color.GREEN);
                    break;
                case 1:
                    board01.setFill(Color.GREEN);
                    break;
                case 2:
                    board02.setFill(Color.GREEN);
                    break;
                case 3:
                    board03.setFill(Color.GREEN);
                    break;
                case 4:
                    board04.setFill(Color.GREEN);
                    break;
                case 5:
                    board05.setFill(Color.GREEN);
                    break;
            }
        } else if (i == 1) {
            switch (j) {
                case 0:
                    board10.setFill(Color.GREEN);
                    break;
                case 1:
                    board11.setFill(Color.GREEN);
                    break;
                case 2:
                    board12.setFill(Color.GREEN);
                    break;
                case 3:
                    board13.setFill(Color.GREEN);
                    break;
                case 4:
                    board14.setFill(Color.GREEN);
                    break;
                case 5:
                    board15.setFill(Color.GREEN);
                    break;
            }
        } else if (i == 2) {
            switch (j) {
                case 0:
                    board20.setFill(Color.GREEN);
                    break;
                case 1:
                    board21.setFill(Color.GREEN);
                    break;
                case 2:
                    board22.setFill(Color.GREEN);
                    break;
                case 3:
                    board23.setFill(Color.GREEN);
                    break;
                case 4:
                    board24.setFill(Color.GREEN);
                    break;
                case 5:
                    board25.setFill(Color.GREEN);
                    break;
            }
        } else if (i == 3) {
            switch (j) {
                case 0:
                    board30.setFill(Color.GREEN);
                    break;
                case 1:
                    board31.setFill(Color.GREEN);
                    break;
                case 2:
                    board32.setFill(Color.GREEN);
                    break;
                case 3:
                    board33.setFill(Color.GREEN);
                    break;
                case 4:
                    board34.setFill(Color.GREEN);
                    break;
                case 5:
                    board35.setFill(Color.GREEN);
                    break;
            }
        } else if (i == 4) {
            switch (j) {
                case 0:
                    board40.setFill(Color.GREEN);
                    break;
                case 1:
                    board41.setFill(Color.GREEN);
                    break;
                case 2:
                    board42.setFill(Color.GREEN);
                    break;
                case 3:
                    board43.setFill(Color.GREEN);
                    break;
                case 4:
                    board44.setFill(Color.GREEN);
                    break;
                case 5:
                    board45.setFill(Color.GREEN);
                    break;
            }
        } else if (i == 5) {
            switch (j) {
                case 0:
                    board50.setFill(Color.GREEN);
                    break;
                case 1:
                    board51.setFill(Color.GREEN);
                    break;
                case 2:
                    board52.setFill(Color.GREEN);
                    break;
                case 3:
                    board53.setFill(Color.GREEN);
                    break;
                case 4:
                    board54.setFill(Color.GREEN);
                    break;
                case 5:
                    board55.setFill(Color.GREEN);
                    break;
            }
        }


    }

    public void turnOff(int i, int j) {
        if (i == 0) {
            switch (j) {
                case 0:
                    board00.setFill(color[0][0]);
                    break;
                case 1:
                    board01.setFill(color[0][1]);
                    break;
                case 2:
                    board02.setFill(color[0][2]);
                    break;
                case 3:
                    board03.setFill(color[0][3]);
                    break;
                case 4:
                    board04.setFill(color[0][4]);
                    break;
                case 5:
                    board05.setFill(color[0][5]);
                    break;
            }
        } else if (i == 1) {
            switch (j) {
                case 0:
                    board10.setFill(color[1][0]);
                    break;
                case 1:
                    board11.setFill(color[1][1]);
                    break;
                case 2:
                    board12.setFill(color[1][2]);
                    break;
                case 3:
                    board13.setFill(color[1][3]);
                    break;
                case 4:
                    board14.setFill(color[1][4]);
                    break;
                case 5:
                    board15.setFill(color[1][5]);
                    break;
            }
        } else if (i == 2) {
            switch (j) {
                case 0:
                    board20.setFill(color[2][0]);
                    break;
                case 1:
                    board21.setFill(color[2][1]);
                    break;
                case 2:
                    board22.setFill(color[2][2]);
                    break;
                case 3:
                    board23.setFill(color[2][3]);
                    break;
                case 4:
                    board24.setFill(color[2][4]);
                    break;
                case 5:
                    board25.setFill(color[2][5]);
                    break;
            }
        } else if (i == 3) {
            switch (j) {
                case 0:
                    board30.setFill(color[3][0]);
                    break;
                case 1:
                    board31.setFill(color[3][1]);
                    break;
                case 2:
                    board32.setFill(color[3][2]);
                    break;
                case 3:
                    board33.setFill(color[3][3]);
                    break;
                case 4:
                    board34.setFill(color[3][4]);
                    break;
                case 5:
                    board35.setFill(color[3][5]);
                    break;
            }
        } else if (i == 4) {
            switch (j) {
                case 0:
                    board40.setFill(color[4][0]);
                    break;
                case 1:
                    board41.setFill(color[4][1]);
                    break;
                case 2:
                    board42.setFill(color[4][2]);
                    break;
                case 3:
                    board43.setFill(color[4][3]);
                    break;
                case 4:
                    board44.setFill(color[4][4]);
                    break;
                case 5:
                    board45.setFill(color[4][5]);
                    break;
            }
        } else if (i == 5) {
            switch (j) {
                case 0:
                    board50.setFill(color[5][0]);
                    break;
                case 1:
                    board51.setFill(color[5][1]);
                    break;
                case 2:
                    board52.setFill(color[5][2]);
                    break;
                case 3:
                    board53.setFill(color[5][3]);
                    break;
                case 4:
                    board54.setFill(color[5][4]);
                    break;
                case 5:
                    board55.setFill(color[5][5]);
                    break;
            }
        }


    }

    public void temp(int turn, int win){
        disappear(disX, disY);
        boolean a = hasMove(turn);
        if (!a) {
            pass.setVisible(true);
        }
        print();
        if (win == 1 || win == -1) {
            isEnd = true;
            pass.setVisible(false);
        }
    }

    public void AIMove(int i, int j){
        int win;
        if (i == 0) {
            switch (j) {
                case 0:

                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c00.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[0][0] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c00.setVisible(true);

                    break;
                case 1:

                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c01.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[0][1] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c01.setVisible(true);

                    break;
                case 2:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c02.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[0][2] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c02.setVisible(true);
                    break;
                case 3:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c03.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[0][3] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c03.setVisible(true);
                    break;
                case 4:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c04.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[0][4] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c04.setVisible(true);
                case 5:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c05.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[0][5] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c05.setVisible(true);
                    break;
            }
        } else if (i == 1) {
            switch (j) {
                case 0:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c10.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[1][0] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c10.setVisible(true);
                    break;
                case 1:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c11.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[1][1] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c11.setVisible(true);
                    break;
                case 2:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c12.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[1][2] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c12.setVisible(true);
                    break;
                case 3:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c13.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[1][3] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c13.setVisible(true);
                    break;
                case 4:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c14.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[1][4] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c14.setVisible(true);
                    break;
                case 5:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c15.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[1][5] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c15.setVisible(true);
                    break;
            }
        } else if (i == 2) {
            switch (j) {
                case 0:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c20.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[2][0] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c20.setVisible(true);
                    break;
                case 1:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c21.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[2][1] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c21.setVisible(true);
                    break;
                case 2:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c22.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[2][2] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c22.setVisible(true);
                    break;
                case 3:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c23.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[2][3] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c23.setVisible(true);
                    break;
                case 4:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c24.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[2][4] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c24.setVisible(true);
                    break;
                case 5:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c25.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[2][5] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c25.setVisible(true);
                    break;
            }
        } else if (i == 3) {
            switch (j) {
                case 0:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c30.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[3][0] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c30.setVisible(true);
                    break;
                case 1:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c31.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[3][1] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c31.setVisible(true);
                    break;
                case 2:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c32.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[3][2] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c32.setVisible(true);
                    break;
                case 3:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c33.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[3][3] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c33.setVisible(true);
                    break;
                case 4:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c34.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[3][4] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c34.setVisible(true);
                    break;
                case 5:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c35.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[3][5] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c35.setVisible(true);
                    break;
            }
        } else if (i == 4) {
            switch (j) {
                case 0:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c40.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[4][0] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c40.setVisible(true);
                    break;
                case 1:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c41.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[4][1] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c41.setVisible(true);
                    break;
                case 2:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c42.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[4][2] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c42.setVisible(true);
                    break;
                case 3:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c43.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[4][3] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c43.setVisible(true);
                    break;
                case 4:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c44.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[4][4] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c44.setVisible(true);
                    break;
                case 5:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c45.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[4][5] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c45.setVisible(true);
                    break;
            }
        } else if (i == 5) {
            switch (j) {
                case 0:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c50.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[5][0] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c50.setVisible(true);
                    break;
                case 1:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c51.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[5][1] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c51.setVisible(true);
                    break;
                case 2:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c52.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[5][2] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c52.setVisible(true);
                    break;
                case 3:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c53.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[5][3] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c53.setVisible(true);
                    break;
                case 4:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c54.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[5][4] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c54.setVisible(true);
                    break;
                case 5:
                    win =0;
                    matrix.board[disX][disY] = 0;//ekhon jekhane ase oita te 0 boshao
                    c55.setFill(Color.WHITE); //ei position shada koro
                    matrix.board[5][5] = -1; //update matrix
                    countBlack();
                    whoseTurn.setText("Turn : Black");
                    win = hasWon(turn);
                    turn = 1;
                    temp(turn, win);
                    c55.setVisible(true);
                    break;
            }
        }
    }
}

