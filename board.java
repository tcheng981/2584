/**
 * Created by tonycheng on 5/14/17.
 */
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;

public class board {
    private int score = 0;
    // The board will always be 4x4
    private int[][] board = new int[4][4];
    public int numberEmpty = 16;
    private boolean alreadyWon = false;
    private ArrayList<Integer> fib = new ArrayList<>();

    // 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584
    public board() {
//        addRandomly();
//        addRandomly();
        addRandomly();
        addRandomly();
        fib.add(0);
        fib.add(1);
        fib.add(2);
        fib.add(3);
        fib.add(5);
        fib.add(8);
        fib.add(13);
        fib.add(21);
        fib.add(34);
        fib.add(55);
        fib.add(89);
        fib.add(144);
        fib.add(233);
        fib.add(377);
        fib.add(610);
        fib.add(987);
        fib.add(1597);
        fib.add(2584);
//        System.out.println(numberEmpty);
    }

    // Handles all of the keyboard commands
    public void transact(String query) {

        if (query.equals("w")) {
            this.up();
        } else if (query.equals("a")) {
            this.left();
        } else if (query.equals("s")) {
            this.down();
        } else if (query.equals("d")) {
            this.right();
        } else {
            System.out.println("Invalid command. Enter 'help' for a list of valid commands");
        }
        if (!alreadyWon) {
            if (this.checkWin()) {
                System.out.println("You won! You can keep playing or stop while you're ahead");
                alreadyWon = true;
            }
        }
        if (this.checkGameOver()) {
            System.out.println("Game Over! Your Score is: " + this.score);
        }
//        System.out.println("number empty: " + numberEmpty);
        this.printBoard();
    }

    public boolean checkWin() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 2584) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkGameOver() {
        if (numberEmpty != 0) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int value = fib.indexOf(this.board[i][j]);
                int left = -2;
                int right = -2;
                int down = -2;
                int up = -2;
                if (j != 0) {
                    left = fib.indexOf(this.board[i][j - 1]);
                }
                if (j != 3) {
                    right = fib.indexOf(this.board[i][j + 1]);
                }
                if (i != 0) {
                    up = fib.indexOf(this.board[i - 1][j]);
                }
                if (i != 3) {
                    down = fib.indexOf(this.board[i + 1][j]);
                }
                if (board[i][j] == 1) {
                    if (left == 1 || right == 1 || up == 1 || down == 1){
                        return false;
                    }
                }
                Boolean vleft = Math.min(value, left) == Math.max(value, left) - 1;
                Boolean vright = Math.min(value, right) == Math.max(value, right) - 1;
                Boolean vup = Math.min(value, up) == Math.max(value, up) - 1;
                Boolean vdown = Math.min(value, down) == Math.max(value, down) - 1;
                if (vleft || vright || vup || vdown) {
//                    System.out.println(value);
//                    System.out.println(left);
//                    System.out.println(right);
//                    System.out.println(up);
//                    System.out.println(down);
                    return false;
                }
            }
        }
        return true;
    }

    // Randomly adds a 2 in a non-zero square on the board
    public void addRandomly() {
        int square;
        if (numberEmpty == 2) {
            square = ThreadLocalRandom.current().nextInt(0, numberEmpty - 1);
        } else if (numberEmpty == 1) {
            square = 0;
        } else {
            square = ThreadLocalRandom.current().nextInt(0, numberEmpty - 2);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.board[i][j] == 0 && square != 0) {
                    square -= 1;
                } else if (board[i][j] == 0 && square == 0) {
                    this.board[i][j] = 1;
                    square -= 1;
                }
            }
        }
        this.numberEmpty -= 1;
    }



    //takes an ArrayList with all non-zero elements first, then padded with zeros until it's length is 4
    public ArrayList<Integer> condense(ArrayList<Integer> list) {
        ArrayList<Integer> result = new ArrayList<>();
        int first = list.get(0);
        int second = list.get(1);
        int third  = list.get(2);
        int fourth = list.get(3);
        int firIndex = fib.indexOf(first);
        int secIndex = fib.indexOf(second);
        int thiIndex = fib.indexOf(third);
        int fouIndex = fib.indexOf(fourth);
        if (first == 1 && second == 1 || Math.min(firIndex, secIndex) == Math.max(firIndex, secIndex) - 1 && second != 0) {
            if (first != 0) {
                result.add(first + second);
                score += first + second;
                if (third == 1 && fourth == 1 || Math.min(thiIndex, fouIndex) == Math.max(thiIndex, fouIndex) - 1 && fourth != 0) {
                    if (third != 0) {
                        result.add(third + fourth);
                        result.add(0);
                        result.add(0);
                        this.numberEmpty += 2;
                        score += third + fourth;
                    }
                } else {
                    result.add(third);
                    result.add(fourth);
                    result.add(0);
                    this.numberEmpty += 1;
                }
            }
        } else if (second == 1 && third == 1 || Math.min(secIndex, thiIndex) == Math.max(secIndex, thiIndex) - 1 && third != 0) {
            if (second != 0) {
                result.add(first);
                result.add(second + third);
                result.add(fourth);
                result.add(0);
                score += second + third;
                this.numberEmpty += 1;
            }
        } else if (third == 1 && fourth == 1 || Math.min(thiIndex, fouIndex) == Math.max(thiIndex, fouIndex) - 1 && fourth != 0) {
            if (third != 0) {
                result.add(first);
                result.add(second);
                result.add(third + fourth);
                result.add(0);
                score += third + fourth;
                this.numberEmpty += 1;
            }
        } else {
            result = list;
        }
        return result;
    }

/*
    public ArrayList<Integer> condensed(ArrayList<Integer> list) {
        ArrayList<Integer> result = new ArrayList<>();
        if (list.get(0).equals(list.get(1)) && list.get(0) != 0) {
            result.add(list.get(0) + list.get(1));
            score += list.get(0) + list.get(1);
            if (list.get(2).equals(list.get(3)) && list.get(2) != 0) {
                result.add(list.get(2) + list.get(3));
                result.add(0);
                result.add(0);
                this.numberEmpty += 2;
                score += list.get(2) + list.get(3);
            } else {
                result.add(list.get(2));
                result.add(list.get(3));
                result.add(0);
                this.numberEmpty += 1;
            }
        } else if (list.get(1).equals(list.get(2)) && list.get(1) != 0) {
            result.add(list.get(0));
            result.add(list.get(1) + list.get(2));
            result.add(list.get(3));
            result.add(0);
            score += list.get(1) + list.get(2);
            this.numberEmpty += 1;
        } else if (list.get(2).equals(list.get(3)) && list.get(2) != 0) {
            result.add(list.get(0));
            result.add(list.get(1));
            result.add(list.get(2) + list.get(3));
            result.add(0);
            score += list.get(2) + list.get(3);
            this.numberEmpty += 1;
        } else {
            result = list;
        }
        return result;
    }
*/

    public void up() {
        int[][] arrayCheck = new int[4][4];
        for (int q = 0; q < 4; q++) {
            System.arraycopy(board[q], 0, arrayCheck[q], 0, 4);
        }
        for (int i = 0; i < 4; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                if (board[j][i] != 0) {
                    temp.add(board[j][i]);
                }
            }
            while (temp.size() != 4) {
                temp.add(0);
            }
            temp = this.condense(temp);
            for (int l = 0; l < 4; l++) {
                board[l][i] = temp.get(l);
            }
        }
        A: for (int p = 0; p < 4; p++) {
            B: for (int m = 0; m < 4; m++) {
                if (board[p][m] != arrayCheck[p][m]) {
                    this.addRandomly();
                    break A;
                }
            }
        }
    }

    public void left() {
        int[][] arrayCheck = new int[4][4];
        for (int q = 0; q < 4; q++) {
            System.arraycopy(board[q], 0, arrayCheck[q], 0, 4);
        }
        for (int i = 0; i < 4; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0) {
                    temp.add(board[i][j]);
                }
            }
            while (temp.size() != 4) {
                temp.add(0);
            }
            temp = this.condense(temp);
            for (int l = 0; l < 4; l++) {
                board[i][l] = temp.get(l);
            }
        }
        A: for (int p = 0; p < 4; p++) {
            B: for (int m = 0; m < 4; m++) {
                if (board[p][m] != arrayCheck[p][m]) {
                    this.addRandomly();
                    break A;
                }
            }
        }
    }

    public void right() {
        int[][] arrayCheck = new int[4][4];
        for (int q = 0; q < 4; q++) {
            System.arraycopy(board[q], 0, arrayCheck[q], 0, 4);
        }
        for (int i = 0; i < 4; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 3; j >= 0; j -= 1) {
                if (board[i][j] != 0) {
                    temp.add(board[i][j]);
                }
            }
            while (temp.size() != 4) {
                temp.add(0);
            }
            temp = this.condense(temp);
            int count = 0;
            for (int l = 3; l >= 0; l--) {
                board[i][l] = temp.get(count);
                count += 1;
            }
        }
        A: for (int p = 0; p < 4; p++) {
            B: for (int m = 0; m < 4; m++) {
                if (board[p][m] != arrayCheck[p][m]) {
                    this.addRandomly();
                    break A;
                }
            }
        }
    }

    public void down() {
        int[][] arrayCheck = new int[4][4];
        for (int q = 0; q < 4; q++) {
            System.arraycopy(board[q], 0, arrayCheck[q], 0, 4);
        }
        for (int i = 0; i < 4; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 3; j >= 0; j -= 1) {
                if (board[j][i] != 0) {
                    temp.add(board[j][i]);
                }

            }
            while (temp.size() != 4) {
                temp.add(0);
            }
            temp = this.condense(temp);
            int count = 0;
            for (int l = 3; l >= 0; l--) {
                board[l][i] = temp.get(count);
                count += 1;
            }
        }
        A: for (int p = 0; p < 4; p++) {
            B: for (int m = 0; m < 4; m++) {
                if (board[p][m] != arrayCheck[p][m]) {
                    this.addRandomly();
                    break A;
                }
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board.length; k++) {
                if (k == board.length - 1 || i == board.length - 1 && k == board.length - 1) {
                    System.out.println(board[i][k]);
                } else {
                    System.out.print(board[i][k] + this.manageSpaces(board[i][k]));
                }
            }
        }
        System.out.println("");
    }

    //Neatly arranges the number in the board
    public String manageSpaces(int x) {
        if (x < 10) {
            return "    ";
        } else if (x < 100) {
            return "   ";
        } else if (x < 1000) {
            return "  ";
        }
        return " ";
    }
    public int getScore() {
        return this.score;
    }
    public int[][] accessBoard() {
        return board;
    }
}
