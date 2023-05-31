package display;

import java.awt.Graphics;

import display.Tile.Tile;
import utility.Utility;

public class Maze {
    private Handler handler;
    private static int width;
    private static int height;
    private static int [][] tiles;

    public int spawnX;
    public int spawnY;

    public Maze (Handler handler, String path) {
        loadMaze(path);
        this.handler = handler;
    }

    public void tick() {
    }

    public void render (Graphics g) {
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 2);
        int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
        int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 2);

        for (int y = yStart; y < yEnd; y++){
            for (int x = xStart; x < xEnd; x++){
                getTile(x, y).render(g, (int)(x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
            }
        }
    }

    public static Tile getTile (int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return Tile.pathTile;
        }

        Tile t = Tile.tiles[tiles[x][y]];

        if (t == null) {
            return Tile.pathTile;
        }

        return t;
    }

    private void loadMaze (String path) {
        String file = Utility.loadFilesAsString(path);
        String[] tokens = file.split("\\s+");

        width = Utility.parseInt(tokens[0]);
        height = Utility.parseInt(tokens[1]);
        spawnX = Utility.parseInt(tokens[2]);
        spawnY = Utility.parseInt(tokens[3]);

        tiles = new int[width][height];

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                tiles[x][y] = Utility.parseInt(tokens[(x + y * width) + 4]);
            }
        }
    }
}
