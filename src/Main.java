import java.io.*;
import java.util.HashMap;

public class Main {

    static private int dir[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // directions : right, left, up, down respectively
    static int num , flag;

    static int N = 7;

    static char[][] input = new char[N][N];

    static int vis[][] = new int[N][N];

    static HashMap<Character,String> colorChar = new HashMap<Character, String>();

    public static void main(String[] args) {

        colorCharinit(colorChar);
        input = readFile("input1.txt");
        int x = 0;
        int y = 0;
        int f = 0;

        char ch = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++)
                if (input[i][j] != 'W') {
                    x = i;
                    y = j;
                    ch = input[i][j];
                    f = 1;
                    break;
                }
            if (f == 1)
                break;
        }

        num = 0;
        flag = 0;
        DFS(x, y, ch);

        if (flag == 1)
            System.out.println("solvable\n");
        else
            System.out.println("not solvable\n");
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
        colorChar.put('Z', "\u001b[0m" ); //Magenta
    }

    private static char[][] readFile(String fileName) {

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

    static int judge(int x, int y) {
        if (0 <= x && x < N && 0 <= y && y < N)
            return 0;
        return 1;
    }

    static void DFS(int x, int y, char ch) {

        if (input[x][y] != 'W' && input[x][y] != ch)
            return ;
        if (judge(x, y) != 0 || vis[x][y] != 0) {
            return ;
        }

        num++;
        vis[x][y] = 1;

        if (num >= N*N) {
            flag = 1;
            return;
        }
        for (int i = 0; i < dir.length; i++) {

            int ax = x + dir[i][0];
            int ay = y + dir[i][1];
            if (judge(ax, ay) == 0 && vis[ax][ay] == 0) {
                if (input[ax][ay] == ch)
                {
                    num++;
                    vis[ax][ay] = 1;
                    if (num == N*N) {
                        flag = 1;
                        return;
                    }
                    for (int ii = 0; ii < N; ii++)
                        for (int jj = 0; jj < N; jj++)
                            if (input[ii][jj] != 'W' && vis[ii][jj] == 0 && input[ii][jj] != ch) {
                                int a = ii;
                                int b = jj;
                                char c = input[ii][jj];
                                DFS(a, b, c);
                            }
                    num--;
                    vis[ax][ay] = 0;
                } else {
                    if (input[ax][ay] != 'W')
                       continue;
                    else
                        DFS(ax,ay,ch);
                }
            }
        }
        num--;
        vis[x][y] = 0;
    }
}
