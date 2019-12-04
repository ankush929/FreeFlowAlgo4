import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Solver {

    private int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // directions : right, left, up, down respectively
    private int count;
    private int flag; //Final flag

    private int gridSize;

    private char[][] input;

    private char[][] visitor;

    private HashMap<Character, String> colorChar = new HashMap<>();

    private char emptyBlock = 'W';

    private String fileName;

    private boolean stop = false;

    private boolean multipleOutputs;

    private boolean animate;
    private long startTime;
    private int i = 0;


    /** Init method for the solver
     * @param fileName The name of the file
     * @param gridSize The size of the grid
     * @param animate Boolean for animation, true to animate else false
     * @param multipleOutputs Boolean to show multiple possible outputs
     * @return
     */
    public boolean init(String fileName, int gridSize, boolean animate, boolean multipleOutputs) {

        //********************************************************************************//

        this.fileName = fileName;
        this.gridSize = gridSize;
        this.animate = animate;
        this.multipleOutputs = multipleOutputs;

        visitor = new char[gridSize][gridSize];

        colorCharinit(colorChar); //define Color for Chars
        input = readFile(fileName); // read file into Input Matrix

        count = 0;
        flag = 0; //init flag to zero

        //********************************************************************************//


        initSolver();

        return flag == 1;
    }

    /**
     * Starts the Free Flow solver.
     * Initializes the x and y coordinates to the first block (0,0) and identifies the first non-empty character.
     * Initializes the DFS from the init character
     */
    private void initSolver() {

        int x = 0;
        int y = 0;
        boolean foundColor = false;

        startTime = System.nanoTime();
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
            if (foundColor)
                break;
        }

        DFS(x, y, ch);

        System.out.println("Number of Iterations = " + i);
    }

    /**Defines the color characters for console and provides coloured visual ouputs for each input colours
     * @param colorChar Hashmap containing input Characrer key and colorChar(console colour string) values
     */
    private void colorCharinit(HashMap<Character, String> colorChar) {

        colorChar.put('R', "\u001B[31m"); //Red
        colorChar.put('G', "\u001B[32m"); //Green
        colorChar.put('Y', "\u001B[33m"); //Yellow
        colorChar.put('B', "\u001B[34m"); //Blue
        colorChar.put('P', "\u001b[35;1m"); //Pink
        colorChar.put('O', "\u001b[33;1m"); //Orange
        colorChar.put('C', "\u001B[36m"); //Cyan
        colorChar.put('M', "\u001b[35m"); //Magenta
        colorChar.put('w', "\u001b[37m"); //White
        colorChar.put('Z', "\u001b[0m"); //RESET
    }


    /**Reads the file and stores the characters in a 2-Dimensional char input Matrix
     * @param fileName The name of the file from which input characters are read
     * @return Returns a 2-Dimensional NXN matrix of character inputs
     */
    private char[][] readFile(String fileName) { // read file and store in 2d matrix (input)

        char input[][] = new char[gridSize][gridSize];
        File file = new File(fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            int i = 0;
            while ((line = br.readLine()) != null) {
                char[] lineChar = line.toCharArray();
                System.arraycopy(lineChar, 0, input[i], 0, gridSize);
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();

        for (char[] anInput : input) {
            for (int j = 0; j < input[0].length; j++) {

                if (colorChar.containsKey(anInput[j])) {
                    System.out.print(colorChar.get(anInput[j]) + anInput[j] + " ");
                } else {
                    System.out.print(colorChar.get('Z') + "\u25A1" + " "); //Print Square
                }
            }
            System.out.println(colorChar.get('Z'));
            System.out.println();
        }

        return input;
    }

    /** Checks bounds for a point if it exists in the grid or not
     * @param x The x coordinate to check for bounds
     * @param y The y coordinate to check for bounds
     * @return true if out of bounds, else false
     */
    private boolean checkBounds(int x, int y) {  //check Bounds for x and y
        return 0 > x || x >= gridSize || 0 > y || y >= gridSize;
    }


    /** Applied DFS at the given point and check for constraints.
     * If final node is found, searches for next node and recursively applied DFS again.
     * @param x The x coordinate to start the DFS
     * @param y The x coordinate to start the DFS
     * @param ch The input character from input Matrix for which DFS is applied for
     */
    private void DFS(int x, int y, char ch) { //set flag = 0 if solvable, else set flag = 1

        i++;
        if (input[x][y] != emptyBlock && input[x][y] != ch)
            return;

        if (stop) { // to prevent multiple outputs.
            return;
        }

        if (checkBounds(x, y) || visitor[x][y] != 0) {
            return;
        }
        count++;
        visitor[x][y] = ch;
        clearScreen();

        for (int[] aDirection : direction) {  //traverse in all four directions

            int nextX = x + aDirection[0];
            int nextY = y + aDirection[1];

//                 if (!checkBounds(nextX - 1, nextY)) {
//
//                     if (visitor[nextX - 1][nextY] == ch) {
//                         if (i < 3) {
//                             i++;
//                             nextX = x + direction[i][0];
//                             nextY = y + direction[i][1];
//                         }
//                     }
//                 }

            if (!checkBounds(nextX, nextY) && visitor[nextX][nextY] == 0) {

                if (input[nextX][nextY] == ch) {  //found final point
                    count++;
                    visitor[nextX][nextY] = ch;
                    clearScreen();

                    if (count == gridSize * gridSize) {
                        flag = 1;
                        printOutputMatrix();
                        System.out.println("Total time = " + (System.nanoTime() - startTime));
                        if (!multipleOutputs)
                            stop = true;
                        return;
                    }

                    for (int ii = 0; ii < gridSize; ii++)  // look for another color and start DFS again
                        for (int jj = 0; jj < gridSize; jj++)
                            if (input[ii][jj] != emptyBlock && visitor[ii][jj] == 0 && input[ii][jj] != ch) {
                                char c = input[ii][jj];
                                DFS(ii, jj, c);
                            }

                    count--;
                    visitor[nextX][nextY] = 0;  // Backtrack
                    clearScreen();

                } else {
                    if (input[nextX][nextY] == emptyBlock) {  // Founds empty block, find next block from here and apply DFS again
                        DFS(nextX, nextY, ch);
                    }
                }
            }
        }
        count--;
        visitor[x][y] = 0;
        clearScreen();
    }


    /**
     * Prints the final visitor Matrix
     */
    private void printOutputMatrix() {
        System.out.println("Count = " + count + "\n");
        for (char[] aVisitor : visitor) {
            for (int j = 0; j < visitor[0].length; j++) {

                if (colorChar.containsKey(aVisitor[j])) {
                    System.out.print(colorChar.get(aVisitor[j]) + aVisitor[j] + " ");
                } else {
                    System.out.print(aVisitor[j] + " "); //Print Square
                }
            }
            System.out.println();
        }
    }


    /**
     * Clears the screen after a thread sleeps for <ANiMATION_SPEED> milliseconds to animate the matrix
     *
     */
    private void clearScreen() {

        if (animate) {
            printOutputMatrix();
            try {
                long ANIMATION_SPEED = 100;
                Thread.sleep(ANIMATION_SPEED);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
}
