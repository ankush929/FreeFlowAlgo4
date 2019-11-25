public class Main {

    static private int dir[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // right, left, up, down respectively
    static int num , flag;

    double MAXN = 1e6 + 5;
    int P = 99991;
    static int N = 5;

    long ll;
    static char[][] s = {{'R', 'G', 'B', 'W'}, {'W', 'W', 'W', 'W'}, {'R', 'G', 'B', 'W'}, {'Y', 'W', 'W', 'Y'}};

    static int vis[][] = new int[N][N];

    public static void main(String[] args) {


        int x = 0;
        int y = 0;
        int f = 0;

        char ch = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                if (s[i][j] != 'W') {
                    x = i;
                    y = j;
                    ch = s[i][j];
                    f = 1;
                    break;
                }
            if (f == 1)
                break;
        }
        //memset(vis, 0, sizeof(vis));

        num = 0;
        flag = 0;
        DFS(x, y, ch);
        //printf("%d %d\n",num,flag);

        if (flag == 1)
            System.out.println("solvable\n");
        else
            System.out.println("not solvable\n");
    }

    static int judge(int x, int y) {
        if (0 <= x && x < 4 && 0 <= y && y < 4)
            return 0;
        return 1;
    }

    static void DFS(int x, int y, char ch) {

        if (s[x][y] != 'W' && s[x][y] != ch)
            return ;
        if (judge(x, y) != 0 || vis[x][y] != 0) {
            System.out.println("Reached 2");
            return ;
        }

        num++;                    //使用该节点
        vis[x][y] = 1;
        //printf("%d\n",num);
        if (num >= 16) {
            flag = 1;
            return;
        }
        for (int i = 0; i < 4; i++) {

            int ax = x + dir[i][0];
            int ay = y + dir[i][1];
            if (judge(ax, ay) == 0 && vis[ax][ay] == 0) {
                if (s[ax][ay] == ch)
                {

                    num++;
                    vis[ax][ay] = 1;
                    if (num == 16) {
                        System.out.println("Reached 2");
                        flag = 1;
                        return;
                    }
                    for (int ii = 0; ii < 4; ii++)
                        for (int jj = 0; jj < 4; jj++)
                            if (s[ii][jj] != 'W' && vis[ii][jj] == 0 && s[ii][jj] != ch) {
                                int a = ii;
                                int b = jj;
                                char c = s[ii][jj];
                                DFS(a, b, c);
                            }
                    num--;
                    vis[ax][ay] = 0;
                } else {
                    if (s[ax][ay] != 'W')
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
