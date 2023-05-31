package display.Tile;

import display.Assets;

public class FinishTile extends Tile {
    public FinishTile (int id){
        super(Assets.finish, id);
    }

    public boolean isSolid(){
        return false;
    }
}
