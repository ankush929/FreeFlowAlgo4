import java.io.*;
import java.util.HashMap;

public class Main {

    static private int direction[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // directions : right, left, up, down respectively
    private static int count , flag; //Final flag

    private static int N = 7;

    private static char[][] input = new char[N][N];

    private static int visitor[][] = new int[N][N];

    private static HashMap<Character,String> colorChar = new HashMap<>();

    private static char emptyBlock = 'W';

    private static String fileName = "input1.txt";

    public static void main(String[] args) {

        colorCharinit(colorChar); //define Color for Chars
        input = readFile(fileName); // read file into Input Matrix

        count = 0;
        flag = 0; //init flag to zero

        int x = 0;
        int y = 0;
        boolean foundColor = false;

        char ch = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++)
                if (input[i][j] != emptyBlock) {
                    x = i;
                    y = j;
                    ch = input[i][j]; // Found Color Def Char in Matrix
                    foundColor = true;
                    break;
                }
            if(foundColor)
                break;
        }

        DFS(x, y, ch);

        if (flag == 1)
            System.out.println("The given puzzle is solvable!\n");
        else
            System.out.println("The given puzzle is not solvable!\n");
    }

    private static void colorCharinit(HashMap<Character, String> colorChar) {

        colorChar.put('R', "\u001B[31m" ); //Red
        colorChar.put('G', "\u001B[32m" ); //Green
        colorChar.put('Y', "\u001B[33m" ); //Yellow
        colorChar.put('B', "\u001B[34m" ); //Blue
        colorChar.put('P', "\u001b[35;1m" ); //Pink
        colorChar.put('O', "\u001b[33;1m" ); //Orange
        colorChar.put('C', "\u001B[36m" ); //Cyan
        colorChar.put('M', "\u001b[35m" ); //Magenta
        colorChar.put('w', "\u001b[37m" ); //White
        colorChar.put('Z', "\u001b[0m" ); //RESET
    }

    private static char[][] readFile(String fileName) { // read file and store in 2d matrix (input)

        char input[][] = new char [N][N];
        File file = new File(fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            int i =0;
            while((line = br.readLine())!=null)
            {
                char[] lineChar = line.toCharArray();
                for (int j = 0; j < N; j++) {
                    input[i][j] = lineChar[j];
                }
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {

                if(colorChar.containsKey(input[i][j]))
                {
                    System.out.print(colorChar.get(input[i][j]) + input[i][j] + " ");
                }
                else
                {
                    System.out.print(colorChar.get('Z') + "\u25A1" + " "); //Print Square
                }
            }
            System.out.println(colorChar.get('Z'));
            System.out.println();
        }

        return input;
    }

    static boolean checkBounds(int x, int y) {  //check Bounds for x and y
        if (0 <= x && x < N && 0 <= y && y < N)
            return false;
        return true;
    }

    static void DFS(int x, int y, char ch) { //set flag = 0 if solvable, else set flag = 1

        if (checkBounds(x, y) || visitor[x][y] != 0) {
            return;
        }

        count++;
        visitor[x][y] = 1;

        if (count >= N*N) {
            flag = 1;
            return;
        }
        for (int i = 0; i < direction.length; i++) {  //traverse in all four directions

            int nextX = x + direction[i][0];
            int nextY = y + direction[i][1];

            if (!checkBounds(nextX, nextY) && visitor[nextX][nextY] == 0) {
                if (input[nextX][nextY] == ch)
                {
                    count++;
                    visitor[nextX][nextY] = 1;
                    if (count == N*N) {
                        flag = 1;
                        return;
                    }
                    for (int ii = 0; ii < N; ii++)
                        for (int jj = 0; jj < N; jj++)
                            if (input[ii][jj] != emptyBlock && visitor[ii][jj] == 0 && input[ii][jj] != ch) {
                                char c = input[ii][jj];
                                DFS(ii, jj, c);
                            }
                    count--;
                    visitor[nextX][nextY] = 0;
                } else {
                    if (input[nextX][nextY] == 'W') {
                        DFS(nextX, nextY, ch);
                    }
                }
            }
        }
        count--;
        visitor[x][y] = 0;
    }
}
