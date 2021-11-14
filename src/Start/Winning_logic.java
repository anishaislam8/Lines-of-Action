package Start;

import java.util.ArrayList;

public class Winning_logic {

    public boolean winLose(int[][] board, int turn){
        int win = hasWon(turn,board);
        if(win == 1 || win== -1 ){
            return true;
        }
        return false;
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

    public int winnerInnerWorker(ArrayList<String> test, int turn,int[][] board) {
        ArrayList<String> done = new ArrayList<>();

        while (test.size() != 0) {
            int[] checker = stringToPosition(test.get(0));
            int m = checker[0];
            int n = checker[1];

            if (m - 1 >= 0 && board[m - 1][n] == turn) {
                if (!done.contains(positionToString(m - 1, n)) && !test.contains(positionToString(m - 1, n))) {
                    test.add(positionToString(m - 1, n));
                }
            }
            if (m + 1 <= 5 && board[m + 1][n] == turn) {
                if (!done.contains(positionToString(m + 1, n)) && !test.contains(positionToString(m + 1, n))) {
                    test.add(positionToString(m + 1, n));
                }
            }
            if (n - 1 >= 0 && board[m][n - 1] == turn) {
                if (!done.contains(positionToString(m, n - 1)) && !test.contains(positionToString(m, n - 1))) {
                    test.add(positionToString(m, n - 1));
                }
            }
            if (n + 1 <= 5 && board[m][n + 1] == turn) {
                if (!done.contains(positionToString(m, n + 1)) && !test.contains(positionToString(m, n + 1))) {
                    test.add(positionToString(m, n + 1));
                }
            }
            if (m - 1 >= 0 && n + 1 <= 5 && board[m - 1][n + 1] == turn) {
                if (!done.contains(positionToString(m - 1, n + 1)) && !test.contains(positionToString(m - 1, n + 1))) {
                    test.add(positionToString(m - 1, n + 1));
                }
            }
            if (m + 1 <= 5 && n - 1 >= 0 && board[m + 1][n - 1] == turn) {
                if (!done.contains(positionToString(m + 1, n - 1)) && !test.contains(positionToString(m + 1, n - 1))) {
                    test.add(positionToString(m + 1, n - 1));
                }
            }
            if (m - 1 >= 0 && n - 1 >= 0 && board[m - 1][n - 1] == turn) {
                if (!done.contains(positionToString(m - 1, n - 1)) && !test.contains(positionToString(m - 1, n - 1))) {
                    test.add(positionToString(m - 1, n - 1));
                }
            }
            if (m + 1 <= 5 && n + 1 <= 5 && board[m + 1][n + 1] == turn) {
                if (!done.contains(positionToString(m + 1, n + 1)) && !test.contains(positionToString(m + 1, n + 1))) {
                    test.add(positionToString(m + 1, n + 1));
                }
            }

            done.add(test.get(0));
            test.remove(0);

        }

        return done.size();
    }

    public int hasWon(int turn, int[][] board) {
        //check if turn has won
        int black = 0;
        int white =0;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if(board[i][j] == 1){
                    black+=1;
                }else if(board[i][j] == -1){
                    white +=1;
                }
            }
        }
        if (turn == 1) {
            if (black == 1) { //ektai black guti
                return 1;
            } else if (white == 1) {
                return -1;
            }
        } else {
            if (white == 1) { //ektai white guti
                return -1;
            } else if (black == 1) {
                return 1;
            }
        }
        //each color has more than 1 checkers


        //first check if I am connected
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

        int connected = winnerInnerWorker(test, turn,board);
        //System.out.println("Connected " + turn + "'s = " + connected);

        if (turn == 1) {//ami jitsi
            if (connected == black) {
                return 1;

            }
        } else {
            if (connected == white) {
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

        connected = winnerInnerWorker(test, other,board);
        //System.out.println("Connected " + other + "'s = " + connected);


        if (other == 1) {// other jitse
            if (connected == black) {
                return 1;

            }
        } else {
            if (connected == white) {
                return -1;

            }
        }

        //egula kisui na hole keu e jite nai :)
        return 0;
    }
}
