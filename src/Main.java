import java.io.*;

public class Main {

    static private int dir[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // directions : right, left, up, down respectively
    static int num , flag;

    static int N = 7;

    static char[][] input = new char[N][N];

    static int vis[][] = new int[N][N];

    public static void main(String[] args) {


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
                System.out.print(input[i][j]);
            }
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
            System.out.println("Reached 2");
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
                        System.out.println("Reached 2");
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
