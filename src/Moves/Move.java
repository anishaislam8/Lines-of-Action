package Moves;

import Start.*;

import java.util.ArrayList;

public class Move {
     public ArrayList<Integer> verticalMove(int i, int j, int[][] board){
         ArrayList<Integer> result = new ArrayList<>();
         int checkers = 0;
         int color = board[i][j];
         int otherColor = (color == 1? -1 : 1);
         for(int row = 0; row < 6; row++){
             if(board[row][j] != 0 ){
                 checkers+=1;
             }
         }
         //check in the upper direction
         if(i - checkers >= 0){
             int noMove = 0;
             int limit = i-checkers;
             //first check if there is any checker of other color between i and position
             for(int k = i-1; k > limit; k--){
                 if(board[k][j] != 0 && board[k][j] != color){
                     noMove = 1;
                     break;
                 }
             }
             //if nomove == 0 then majhe kono onno color guti nai.
             if(noMove == 0) {
                 //ekhon check koro je nijer tar upor portesi na onner tar upor
                 boolean finalAnswer = checkFinalPosition(limit,j,color,otherColor,board);
                 if(finalAnswer){
                     result.add(i-checkers);
                 }
             }
         }

         //check in the down direction
         if(i + checkers <=5){
             int noMove = 0;
             int limit = i+checkers;
             //first check if there is any checker of other color between i and position
             for(int k = i+1; k < limit; k++){
                 if(board[k][j] != 0 && board[k][j] != color){
                     noMove = 1;
                     break;
                 }
             }
             //if nomove == 0 then majhe kono onno color guti nai.
             if(noMove == 0) {
                 //ekhon check koro je nijer tar upor portesi na onner tar upor
                 boolean finalAnswer = checkFinalPosition(limit,j,color,otherColor,board);
                 if(finalAnswer){
                     result.add(i+checkers);
                 }
             }

         }


         return result;

     }
    public ArrayList<Integer> horizontalMove(int i, int j, int[][] board){
        ArrayList<Integer> result = new ArrayList<>();
        int checkers = 0;
        int color = board[i][j];
        int otherColor = (color == 1? -1 : 1);
        for(int column = 0; column < 6; column++){
            if(board[i][column] != 0 ){
                checkers+=1;
            }
        }
        //check in the left direction
        if(j - checkers >= 0){
            int noMove = 0;
            int limit = j-checkers;
            //first check if there is any checker of other color between i and position
            for(int k = j-1; k > limit; k--){
                if(board[i][k] != 0 && board[i][k] != color){
                    noMove = 1;
                    break;
                }
            }
            //if nomove == 0 then majhe kono onno color guti nai.
            if(noMove == 0) {
                //ekhon check koro je nijer tar upor portesi na onner tar upor
                boolean finalAnswer = checkFinalPositionHorizontal(limit,i,color,otherColor,board);
                if(finalAnswer){
                    result.add(j-checkers);
                }
            }
        }

        //check in the right direction
        if(j + checkers <=5){
            int noMove = 0;
            int limit = j+checkers;
            //first check if there is any checker of other color between i and position
            for(int k = j+1; k < limit; k++){
                if(board[i][k] != 0 && board[i][k] != color){
                    noMove = 1;
                    break;
                }
            }
            //if nomove == 0 then majhe kono onno color guti nai.
            if(noMove == 0) {
                //ekhon check koro je nijer tar upor portesi na onner tar upor
                boolean finalAnswer = checkFinalPositionHorizontal(limit,i,color,otherColor,board);
                if(finalAnswer){
                    result.add(j+checkers);
                }
            }

        }


        return result;

    }

    public ArrayList<Integer> leftDiagonalMove(int i, int j, int[][] board){
        ArrayList<Integer> result = new ArrayList<>();
        int color = board[i][j];
        int otherColor = (color == 1? -1 : 1);
        int checkers = 1;
        int row = i;
        int column = j;
        //downwards
        while(row+1 <=5 && column+1 <=5){
            if(board[row + 1][column + 1] != 0){
                checkers +=1;
            }
            row += 1;
            column += 1;
        }

        //upwards
        row = i;
        column = j;
        //downwards
        while(row-1 >=0 && column-1 >=0){
            if(board[row - 1][column - 1] != 0){
                checkers +=1;
            }
            row -= 1;
            column -= 1;
        }


        //we found out how many checkers, now find moves

        //********************************//
        //downwards

        //check if it goes out of board
        row = i+1;
        column = j+1;
        for(int k =0 ; k < checkers; k++){
            if(row <= 5 && column <= 5){
                boolean inner = innerWorker(k,checkers,row,column,board,color,otherColor,result);
                if(!inner){
                    break;
                }
            }else{
                break;
            }
            row += 1;
            column += 1;

        }



        //********************************//
        //upwards

        //check if it goes out of board
        row = i-1;
        column = j-1;
        for(int k =0 ; k < checkers; k++){
            if(row >= 0 && column >= 0){
                boolean inner = innerWorker(k,checkers,row,column,board,color,otherColor,result);
                if(!inner){
                    break;
                }
            }else{
                break;
            }
            row -= 1;
            column -= 1;
        }

        return result;

    }

    public ArrayList<Integer> rightDiagonalMove(int i, int j, int[][] board){
        ArrayList<Integer> result = new ArrayList<>();
        int color = board[i][j];
        int otherColor = (color == 1? -1 : 1);
        int checkers = 1;
        int row = i;
        int column = j;
        //upwards
        while(row-1 >=0 && column+1 <=5){
            if(board[row - 1][column + 1] != 0){
                checkers +=1;
            }
            row -= 1;
            column += 1;
        }

        //downwards
        row = i;
        column = j;
        //downwards
        while(row+1 <=5 && column-1 >=0){
            if(board[row + 1][column - 1] != 0){
                checkers +=1;
            }
            row += 1;
            column -= 1;
        }


        //we found out how many checkers, now find moves

        //********************************//
        //upwards

        //check if it goes out of board
        row = i-1;
        column = j+1;
        for(int k =0 ; k < checkers; k++){
            if(row >= 0 && column <= 5){
                boolean inner = innerWorker(k,checkers,row,column,board,color,otherColor,result);
                if(!inner){
                    break;
                }
            }else{
                break;
            }
            row -= 1;
            column += 1;
        }



        //********************************//
        //downwards

        //check if it goes out of board
        //noMove = 0;
        row = i+1;
        column = j-1;
        for(int k =0 ; k < checkers; k++){
            if(row <= 5 && column >= 0){
                boolean inner = innerWorker(k,checkers,row,column,board,color,otherColor,result);
                if(!inner){
                    break;
                }

            }else{
                break;
            }
            row += 1;
            column -= 1;
        }



        return result;

    }

    public boolean checkFinalPosition(int limit, int j, int color, int otherColor, int[][] board){
        int k  = 0;
        if (board[limit][j] == color) {
            //invalid move, nijer tar upor portesi
            k = 1;
        } else if (board[limit][j] == otherColor) { //onno color ki na
            if (color == 1) {//ami black, white ta out
                //HumanVsHuman6x6.white -= 1;
                //System.out.println("White : " + HumanVsHuman6x6.white);
            } else {
                //HumanVsHuman6x6.black -= 1;
                //System.out.println("Black : " + HumanVsHuman6x6.black);
            }
        }
        if(k == 1){
            return false;
        }
        return  true;
    }

    public boolean checkFinalPositionHorizontal(int limit, int i, int color, int otherColor, int[][] board){
        int k  = 0;
        if (board[i][limit] == color) {
            //invalid move, nijer tar upor portesi
            k = 1;
        } else if (board[i][limit] == otherColor) { //onno color ki na
            if (color == 1) {//ami black, white ta out
                //HumanVsHuman6x6.white -= 1;
                //System.out.println("White : " + HumanVsHuman6x6.white);
            } else {
                //HumanVsHuman6x6.black -= 1;
                //System.out.println("Black : " + HumanVsHuman6x6.black);
            }
        }
        if(k == 1){
            return false;
        }
        return  true;
    }

    public boolean innerWorker(int k, int checkers, int row, int column, int[][] board, int color, int otherColor, ArrayList<Integer> result){
        int m = 0;
        if(k != checkers - 1 ) {
            if (board[row][column] != 0 && board[row][column] != color) {//majhe gap o nai, amar color er kisu o nai
//                noMove = 1;//invalid ,onno color cross kore jete parbe na
//                break;
                m = 1;
            }
        }
        else if(k == checkers -1 && m == 0){//last position ta deya jabe?
            if(board[row][column] == color){
//                noMove = 1;
//                break;
                m = 1;
            }

            if(m != 1){
                result.add(row);
                result.add(column);
            }
        }

        if(m == 1){
            return  false;
        }
        return  true;

    }

}
