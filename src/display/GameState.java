package display;

import java.awt.Graphics;

import display.Tile.Tile;
import generator.Mazegen;

public class GameState extends State {
    private PlayerEntity player;
    private Maze maze;

    public GameState (Handler handler) {
        super(handler);

        maze = new Maze(handler, "src/assets/maze.txt");
        handler.setMaze(maze);
        player = new PlayerEntity(handler, Mazegen.startX * Tile.TILEWIDTH, Mazegen.startY * Tile.TILEHEIGHT, PlayerEntity.DEFAULT_PLAYER_WIDTH, PlayerEntity.DEFAULT_PLAYER_HEIGHT);
    }

    public void tick () {
        maze.tick();
        player.tick();
    }

    public void render (Graphics g) {
        maze.render(g);
        player.render(g);
    }
}
