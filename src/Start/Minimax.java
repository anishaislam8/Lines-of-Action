package Start;

import Moves.Move;

import java.util.ArrayList;
import java.util.Random;

public class Minimax {
    public static ArrayList<Double> cost;
    public static ArrayList<int[][]> boardStateTracker;
    public static Move moveObject;
    public static Heuristic heuristic;
    public static int[][] finalMove;
    public static int totalDepth;
    public Minimax(int depth) {
        cost = new ArrayList<>();
        boardStateTracker = new ArrayList<>();
        moveObject = new Move();
        finalMove = new int[6][6];
        totalDepth = depth;
        heuristic = new Heuristic();
    }

    public static void printTable(int[][] matrix){
        for(int i =0; i < 6; i++){
            for(int j =0; j < 6; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();
    }



    public  double minimax(int[][] board, int depth, double alpha, double beta, int turn){

        //int turnMobility = children.size();

        if(depth == 0 || new Winning_logic().winLose(board,turn)){
            double a = Heuristic.pieceSquareTable(board);
            double b = Heuristic.quad(board);
            double c = Heuristic.area(board);
            double d =  Heuristic.mobility(board);
            double e = Heuristic.centerOfMass(board);
            double f = Heuristic.connectedNess(board);
            //double val = (double)(5000*a + 10000*b + 10000*d + 3000*e + 1000 * f);
            double val = (double)(5000*a + 10000*c + 3000*e);
            Random random = new Random();
            val = val + random.nextInt(100);
            return val;
        }

        //find the children
        int[][] saved = new int[6][6];

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                saved[i][j] = board[i][j];
            }
        }

        //added all possible moves as children
        ArrayList<int[][]> children = new ArrayList<>();
        int[][] tempBoard2 = new int[6][6];
        for(int i = 0; i < 6; i++){
            for(int j =0 ; j < 6; j++){
                if(board[i][j] == turn){
                    ArrayList<Integer> v = moveObject.verticalMove(i,j,board);
                    ArrayList<Integer> h = moveObject.horizontalMove(i,j,board);
                    ArrayList<Integer> ld = moveObject.leftDiagonalMove(i,j,board);
                    ArrayList<Integer> rd = moveObject.rightDiagonalMove(i,j,board);




                    for (int k = 0; k < h.size(); k++){

                        for(int m = 0; m < 6; m++){
                            for(int n = 0; n < 6; n++){
                                tempBoard2[m][n] = saved[m][n];
                            }
                        }
                        //System.out.println("Horizontal child");
                        tempBoard2[i][j] = 0;
                        tempBoard2[i][h.get(k)] = turn;
                        //printTable(tempBoard2);
                        children.add(tempBoard2);
                    }

                    for (int k = 0; k < ld.size(); k+=2){

                        for(int m = 0; m < 6; m++){
                            for(int n = 0; n < 6; n++){
                                tempBoard2[m][n] = saved[m][n];
                            }
                        }
                        //System.out.println("LD child");
                        tempBoard2[i][j] = 0;
                        tempBoard2[ld.get(k)][ld.get(k+1)] = turn;
                        //printTable(tempBoard2);
                        children.add(tempBoard2);
                    }

                    for (int k = 0; k < v.size(); k++){

                        for(int m = 0; m < 6; m++){
                            for(int n = 0; n < 6; n++){
                                tempBoard2[m][n] = saved[m][n];
                            }
                        }
                        //System.out.println("Vertical child");
                        tempBoard2[i][j] = 0;
                        tempBoard2[v.get(k)][j] = turn;

                        //printTable(tempBoard2);
                        children.add(tempBoard2);
                    }

                    for (int k = 0; k < rd.size(); k+=2){

                        for(int m = 0; m < 6; m++){
                            for(int n = 0; n < 6; n++){
                                tempBoard2[m][n] = saved[m][n];
                            }
                        }
                        //System.out.println("RD child");
                        tempBoard2[i][j] = 0;
                        tempBoard2[rd.get(k)][rd.get(k+1)] = turn;
                        //printTable(tempBoard2);
                        children.add(tempBoard2);
                    }
                }
            }
        }

        if(turn == -1){
            double maxEval = Double.MIN_VALUE;
            for(int i = 0; i < children.size(); i++){
                double eval = minimax(children.get(i), depth - 1, alpha, beta, 1);
                if(depth == totalDepth) {
                    cost.add(eval);
                    boardStateTracker.add(children.get(i));
                }
                maxEval = Math.max(maxEval,eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha){
                    break;
                }
            }
            if(depth == totalDepth) {
                int prob = 0;
                for (int i = 0; i < cost.size(); i++) {
                    if (cost.get(i) == maxEval) {
                        finalMove = boardStateTracker.get(i);
                        prob=1;
                        break;
                    }
                }
                if(prob == 0){
                    System.out.println("Problem");
                }

                cost.clear();
                boardStateTracker.clear();

            }



            return maxEval;
        }else{
            double minEval = Double.MAX_VALUE;
            for(int i =0; i < children.size(); i++){
                double eval = minimax(children.get(i), depth - 1, alpha, beta, -1);
                minEval = Math.min(minEval,eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha){
                    break;
                }
            }

            return minEval;
        }

    }

    public int[][] bestMove(int[][] board, int depth, double alpha, double beta, int turn){
       double costOfMove = minimax(board,depth,alpha,beta, -1);
       return finalMove;

    }



}
