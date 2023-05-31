package display;

public class Handler {
    private Game game;
    public Maze maze;

    public Handler (Game game) {
        this.game = game;
    }

    public KeyManager getKeyManager () {
        return game.getKeyManager();
    }

    public GameCamera getGameCamera () {
        return game.getGameCamera();
    }

    public int getWidth () {
        return game.getWidth();
    }

    public int getHeight () {
        return game.getHeight();
    }

    public Game getGame () {
        return game;
    }

    public void setGame (Game game) {
        this.game = game;
    }

    public Maze getMaze () {
        return maze;
    }

    public void setMaze (Maze maze) {
        this.maze = maze;
    }
}
