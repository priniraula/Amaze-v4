package generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
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
    public static final int MAZE_SIDE_LEN = 50;

    protected static Random random;
    protected static int [][] maze;
    protected static List<List<Tree>> sets;
    protected static Stack<Edge> edges;

    public static int [][] generateMaze (int startX, int startY, int finishX, int finishY){
        maze = new int [MAZE_SIDE_LEN][MAZE_SIDE_LEN];
        sets = new ArrayList<List<Tree>>();
        edges = new Stack<Edge>();
        random = new Random();

        for (int i = 0; i < MAZE_SIDE_LEN; i += 1) {
            List<Tree> current = new ArrayList<Tree>();

            for (int j = 0; j < MAZE_SIDE_LEN; j += 1) {
                current.add(new Tree());
                maze[i][j] = 0;

                if (i > 0) {
                    edges.add(new Edge(j, i, N));
                }
                if (j > 0) {
                    edges.add(new Edge(j, i, W));
                }
            }

            sets.add(current);
            shuffle(edges);
        }

        while (edges.size() > 0) {
            Edge temporary = edges.pop();
            int x = temporary.x;
            int y = temporary.y;
            int direction = temporary.direction;

            int dx = x + directionX(direction);
            int dy = y + directionY(direction);

            Tree set1 = (sets.get(y)).get(x);
            Tree set2 = (sets.get(dy)).get(dx);

            if (!set1.connected((set2))) {
                set1.connect(set2);
                maze[y][x] |= direction;
                maze[dy][dx] |= oppositeDirection(direction);
            }
        }

        return maze;
    }

    public static void run () {
        startX = 1;
        startY = 1;
        finishX = mSize - 2;
        finishY = mSize - 2;
        int [][] maze = generateMaze(startX, startY, finishX, finishY);

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

            for (int i = 0; i < MAZE_SIDE_LEN; i += 1) {
                printStream.print(1 + " ");
            }
            printStream.print("\n");

            for (int i = 0; i < MAZE_SIDE_LEN; i += 1) {
                for (int j = 0; j < MAZE_SIDE_LEN; j += 1) {
                    if (i == MAZE_SIDE_LEN - 1 && j == MAZE_SIDE_LEN - 1) {
                        printStream.print(2 + " ");
                    }
                    else {
                        // if (i == 0) {
                        //     printStream.print(1 + " ");
                        // }
                        if ((maze[i][j] & S) != 0) {
                            printStream.print(0 + " ");
                        }
                        else {
                            printStream.print(1 + " ");
                        }
    
                        if ((maze[i][j] & E) != 0) {
                            if (((maze[i][j] | maze[i][j + 1]) & S) != 0) {
                                printStream.print(0 + " ");
                            }
                            else {
                                printStream.print(1 + " ");
                            }
                        }
                        else {
                            // printStream.print(1 + " ");
                        }
                    }
                }
                printStream.print("\n");
            }

            for (int i = 0; i < MAZE_SIDE_LEN; i += 1) {
                printStream.print(1 + " ");
            }

            printStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void shuffle (List<Edge> edges) {
        for (int i = 0; i < edges.size(); i += 1) {
            int position = random.nextInt(edges.size());

            Edge current1 = edges.get(i);
            Edge current2 = edges.get(position);

            edges.set(i, current2);
            edges.set(position, current1);
        }
    }

    private static int directionX (int direction) {
        switch (direction) {
            case E:
                return +1;
            case W:
                return -1;
            case N:
            case S:
                return 0;
            default:
                return -1;
        }
    }

    private static int directionY (int direction) {
        switch (direction) {
            case E:
            case W:
                return 0;
            case N:
                return -1;
            case S:
                return 1;
            default:
                return -1;
        }
    }

    private static int oppositeDirection (int direction) {
        switch (direction) {
            case E:
                return W;
            case W:
                return E;
            case N:
                return S;
            case S:
                return N;
            default:
                return -1;
        }
    }
}
