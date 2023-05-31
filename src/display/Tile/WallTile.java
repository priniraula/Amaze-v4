package display.Tile;

import display.Assets;

public class WallTile extends Tile {

    public WallTile (int id){
        super(Assets.wall, id);
    }
    public boolean isSolid(){
        return true;
    }
}
