package Start;

import Moves.Move;

import java.util.ArrayList;
import java.util.Collections;

public class Heuristic {
    public static int pieceSquareTable(int[][] board){
        int turn = -1;
        int other = 1;
        int turnScore = 0;
        int otherScore = 0;

        int[][] valueTable =
                {{-80,-25,-20,-20,-25,-80},
                {-25,10,10,10,10,-25},
                {-25,10,25,25,10,-25},
                {-25,10,25,25,10,-25},
                {-25,10,10,10,10,-25},
                {-80,-25,-20,-20,-25,-80}};

        for(int i = 0; i < 6; i++){
            for(int j =0;j < 6; j++){
                if(board[i][j] == turn){
                    turnScore += valueTable[i][j];
                }else if(board[i][j] == other){
                    otherScore += valueTable[i][j];
                }
            }
        }


        return turnScore - otherScore;
    }

    public static int area(int[][] board){
        int turn = -1;
        int other = 1;
        ArrayList<Integer> turnX = new ArrayList<>();
        ArrayList<Integer> turnY = new ArrayList<>();
        ArrayList<Integer> otherX = new ArrayList<>();
        ArrayList<Integer> otherY = new ArrayList<>();

        for(int i = 0; i < 6; i++){
            for(int j =0;j < 6; j++){
                if(board[i][j] == turn){
                    if(!turnX.contains(i)){
                        turnX.add(i);
                    }
                    if(!turnY.contains(j)){
                        turnY.add(j);
                    }
                }else if(board[i][j] == other){
                    if(!otherX.contains(i)){
                        otherX.add(i);
                    }
                    if(!otherY.contains(j)){
                        otherY.add(j);
                    }
                }
            }
        }


        Collections.sort(turnX);
        Collections.sort(turnY);
        Collections.sort(otherX);
        Collections.sort(otherY);


        int areaOfTurn = (turnX.get(turnX.size() - 1) - turnX.get(0)) * (turnY.get(turnY.size() - 1) - turnY.get(0));
        int areaOfOther = (otherX.get(otherX.size() - 1) - otherX.get(0)) * (otherY.get(otherY.size() - 1) - otherY.get(0));

        return areaOfOther - areaOfTurn;
    }

    public static int mobility(int[][] board){
        int turn = -1;
        int other = 1;
        int otherMobility = 0;
        int turnMobility = 0;
        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] != 0) {
                    ArrayList<Integer> v = new Move().verticalMove(i, j, board);
                    ArrayList<Integer> h = new Move().horizontalMove(i, j, board);
                    ArrayList<Integer> ld = new Move().leftDiagonalMove(i, j, board);
                    ArrayList<Integer> rd = new Move().rightDiagonalMove(i, j, board);
                    if (board[i][j] == other) {
                        otherMobility += v.size();
                        otherMobility += h.size();
                        otherMobility += (ld.size() / 2);
                        otherMobility += (rd.size() / 2);
                    } else if (board[i][j] == turn) {
                        turnMobility += v.size();
                        turnMobility += h.size();
                        turnMobility += (ld.size() / 2);
                        turnMobility += (rd.size() / 2);
                    }
                }
            }
        }

        return turnMobility - otherMobility;
    }

    public static double centerOfMass(int[][] board){
        int turn = -1;
        int other = 1;
        double otherCoMX = 0;
        double turnCoMX = 0;
        double otherCoMY = 0;
        double turnCoMY = 0;
        int countTurn = 0;
        int countOther = 0;
        int xPosTurn = 0;
        int yPosTurn = 0;
        int xPosOther = 0;
        int yPosOther = 0;

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if(board[i][j] == turn){
                    xPosTurn+= i;
                    yPosTurn+=j;
                    countTurn+=1;
                }else if(board[i][j] == other){
                    xPosOther+= i;
                    yPosOther += j;
                    countOther += 1;
                }
            }
        }

        turnCoMX = xPosTurn/countTurn;
        turnCoMY = yPosTurn/countTurn;
        otherCoMX = xPosOther/countOther;
        otherCoMY = yPosOther/countOther;


        double turnX = 0;
        double turnY = 0;
        double otherX = 0;
        double otherY = 0;

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if(board[i][j] == turn){
                   turnX += Math.abs(turnCoMX - i);
                   turnY += Math.abs(turnCoMY - j);
                }else if(board[i][j] == other){
                    otherX += Math.abs(otherCoMX - i);
                    otherY += Math.abs(otherCoMY - j);
                }
            }
        }
        return (otherX + otherY) - (turnX + turnY);


    }

    public static int quad(int[][] board){
        int turn = -1;
        int other = 1;


        ArrayList<int[]> quadList = new ArrayList<>();
        for(int i = 1; i < 4; i++){
            for(int j = 1; j < 4; j++){
                int[] temp = {i,j};
                quadList.add(temp);
            }

        }
        int turnQuad = 0;
        int otherQuad = 0;

        for(int k = 0; k < quadList.size(); k++) {
            int[] pos = quadList.get(k);
            int cTurn = 0;
            int cOther = 0;

            if(board[pos[0]][pos[1]] == turn){
                cTurn += 1;
            }else if(board[pos[0]][pos[1]] == other){
                cOther += 1;
            }

            if(board[pos[0]][pos[1] + 1] == turn){
                cTurn += 1;
            }
            else if(board[pos[0]][pos[1] + 1] == other){
                cOther +=1;
            }

            if(board[pos[0] + 1][pos[1]] == turn){
                cTurn +=1;
            }
            else if(board[pos[0] + 1][pos[1]] == other){
                cOther +=1;
            }

            if(board[pos[0] + 1][pos[1] + 1] == turn){
                cTurn +=1;
            }
            else if(board[pos[0] + 1][pos[1] + 1] == other){
                cOther +=1;
            }

            if(cTurn == 3 || cTurn ==4){
                turnQuad += 1;
            }else if(cOther == 3 || cOther == 4){
                otherQuad += 1;
            }
        }

        return turnQuad - otherQuad;
    }

    public static int connectedNess(int[][] board){
        int turn = -1;
        int other = 1;
        ArrayList<String> test = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == turn) {
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
        Winning_logic win = new Winning_logic();
        int turnConnected = win.winnerInnerWorker(test,turn,board);

        test.clear();
        k = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == other) {
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
        int otherConnected =  win.winnerInnerWorker(test,other,board);

        return turnConnected - otherConnected;
    }




}
