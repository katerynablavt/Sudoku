import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
class sudoku {
    public static boolean isSafe(int[][] board, int row, int col, int num)
    {
        // row has the unique (row-clash)
        for (int d = 0; d < board.length; d++) {
            if (board[row][d] == num) {
                return false;
            }
        }

        // column has the unique numbers (column-clash)
        for (int r = 0; r < board.length; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }
        // corresponding square has
        // unique number (box-clash)
        int sqrt = (int)Math.sqrt(board.length);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;
        for (int r = boxRowStart;
             r < boxRowStart + sqrt; r++) {
            for (int d = boxColStart;
                 d < boxColStart + sqrt; d++) {
                if (board[r][d] == num) {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean solveSudoku(int[][] board, int n)
    {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    // we still have some remaining
                    // missing values in Sudoku
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }
        // no empty space left
        if (isEmpty) {
            return true;
        }
        // else for each-row backtrack
        for (int num = 1; num <= n; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board, n)) {
                    // print(board, n);
                    return true;
                }
                else {
                    // replace it
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }
    public static void print(
            int[][] board, int N)
    {
        for (int r = 0; r < N; r++) {
            for (int d = 0; d < N; d++) {
                System.out.print(board[r][d]);
                System.out.print("");
            }
            System.out.print("\n");

            if ((r + 1) % (int)Math.sqrt(N) == 0) {
                System.out.print("");
            }
        }
    }
    // Driver Code
    public static void main(String args[]) throws IOException {
        //reading from a file and converting into string arraylist of rows
        File txt = new File("unsolved_stars.txt");
        Scanner scan = new Scanner(txt);
        ArrayList<String> data = new ArrayList<String>() ;
        while(scan.hasNextLine()){
            data.add(scan.nextLine());
        }
        System.out.println(data); //string arraylist of rows
        System.out.println("===========================================");
        String[] simpleArray = data.toArray(new String[]{});//creating string array from arraylist
        int[][] board=new int[9][9];
        int N = board.length;
        //each item of string array of rows convert into array of int spliting by "", all stars change to 0
        for (int i=0; i<board.length;i++) {//i=lines
            String test = simpleArray[i];
            String[] integerStrings = test.split("");
            int[] integers = new int[N];
            for (int j = 0; j < N; j++) {//j=colums
                if (integerStrings[j].equals("*")) {
                    integerStrings[j]="0";
                }
                integers[j] = Integer.parseInt(integerStrings[j]);
                board[i][j]=integers[j];
            }
        }
        System.out.println("Unsolved");
        print(board,N);
        System.out.println("===========================================");
        System.out.println("Solution");
        if (solveSudoku(board, N)) {
            // print solution
            print(board, N);
        }
        else {
            System.out.println("No solution");
        }
        //writing down into a file
        FileWriter writer = new FileWriter("solvedsudoku_stars.txt");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                writer.write(board[i][j]  + "");
            }
            writer.write("\n");
        }
        writer.close();
    }
}
