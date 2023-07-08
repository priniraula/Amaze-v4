package generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import utility.Edge;
import utility.Tree;

public class Mazegen {
    public static final int N = 1;
	public static final int S = 2;
	public static final int E = 4;
	public static final int W = 8;

    public static int mSize = 20;
    public static int startX;
    public static int startY;
    public static int finishX;
    public static int finishY;
    public static final int MAZE_SIDE_LEN = 20 * 2 + 1;

    protected static Random random;
    protected static int [][] maze;
    protected static List<List<Tree>> sets;
    protected static Stack<Edge> edges;

    // Direction ENUM for backtracking
    private enum direction {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);

        private final int bit;
        private final int dx;
        private final int dy;
        private direction opposite;

        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        private direction(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    }

    private static boolean between (int x, int upper) {
        return (x >= 0 && x < upper);
    }

    // recursive backtracking
    public static void generateMaze (int x, int y){
        direction [] directions = direction.values();
        Collections.shuffle(Arrays.asList(directions));
        for (direction dir: directions) {
            int cx = x + dir.dx;
            int cy = y + dir.dy;
            
            if (between(cx, mSize) && between(cy, mSize) && maze[cx][cy] == 0) {
                maze[x][y] |= dir.bit;
                maze[cx][cy] |= dir.opposite.bit;
                generateMaze(cx, cy);
            }
        }
    }

    public static void run () {
        startX = 1;
        startY = 1;
        finishX = mSize - 2;
        finishY = mSize - 2;
        maze = new int [mSize][mSize];
        generateMaze(1, 1);

        try {
            File file = new File("src/assets/maze.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PrintStream printStream = new PrintStream(fileOutputStream);

            printStream.print(MAZE_SIDE_LEN);
            printStream.print(" ");
            printStream.println(MAZE_SIDE_LEN);

            printStream.print(startX);
            printStream.print(" ");
            printStream.println(startY);

            for (int i = 0; i < mSize; i += 1) {
                for (int j = 0; j < mSize; j += 1) {
                    printStream.print((maze[j][i] & 1) == 0 ? "1 1 " : "1 0 ");
                }
                printStream.println("1");
                for (int j = 0; j < mSize; j += 1) {
                    printStream.print((maze[j][i] & 8) == 0 ? "1 0 " : "0 0 ");
                }
                printStream.println("1");
            }
            for (int j = 0; j < mSize; j += 1) {
                printStream.print("1 1 ");
            }
            printStream.println("1");

            printStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
