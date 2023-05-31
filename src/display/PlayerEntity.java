package display;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;

import display.Tile.Tile;
import generator.Mazegen;

public class PlayerEntity {
    public static final float DEFAULT_SPEED = 6.0f;
    public static final int DEFAULT_PLAYER_WIDTH = 64;
    public static final int DEFAULT_PLAYER_HEIGHT = 64;

    private float x, y;
    private float xMove, yMove;
    private int width, height;
    private Handler handler;
    
    protected Rectangle bounds;
    protected float speed;

    public PlayerEntity (Handler handler, float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.handler = handler;
        this.width = width;
        this .height = height;

        speed = DEFAULT_SPEED;
        bounds = new Rectangle(16, 16, 32, 32); // QUESTIONABLE????
    }

    private void getInput () {
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().up) {
            yMove = -speed;
        }

        if (handler.getKeyManager().down) {
            yMove = speed;
        }

        if (handler.getKeyManager().left) {
            xMove = -speed;
        }

        if (handler.getKeyManager().right) {
            xMove = speed;
        }
    }

    public void tick () {
        getInput();
        move();

        if (finish()) {
            Display.frame.setVisible(false);
            Display.frame.dispose();
            Display.frame.dispatchEvent(new WindowEvent(Display.frame, WindowEvent.WINDOW_DEACTIVATED));
            handler.getGame().running = false;
        }

        handler.getGameCamera().centerOnEntitiy(this);
    }

    public void move () {
        moveX();
        moveY();
    }

    public boolean finish () {
        int finalX = Mazegen.finishX * Tile.TILEWIDTH;
        int finalY = Mazegen.finishY * Tile.TILEHEIGHT;

        if (x > finalX && x < (finalX + Tile.TILEWIDTH) && y > finalY && x < (finalY + Tile.TILEWIDTH)) {
            return true;
        }
        return false;
    }

    public void moveX () {
        // moving right
        if (xMove > 0) {
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
            if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)){
                x += xMove;
            }
        }
        // moving left
        else if (xMove < 0) {
            int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;

            if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)){
                x += xMove;
            }
        }
    }

    public void moveY () {
        // moving up
        if (yMove < 0) {
            int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

            if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)){
                y += yMove;
            }
        }
        // moving down
        else if (yMove > 0) {
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;

            if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)){
                y += yMove;
            }
        }
    }

    protected boolean collisionWithTile(int x, int y){
        return Maze.getTile(x, y).isSolid();
    }

    public void render (Graphics g) {
        g.drawImage(Assets.player, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
