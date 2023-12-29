package com.mygdx.game.screens.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.maps.MapLoader;
import com.mygdx.game.maps.TDMap;
import com.mygdx.game.screens.GameScreen;

public class PickMapButton extends Clickable {
    int mapID;
    TDGame game;
    public PickMapButton(TDGame game, Texture activeTexture, Texture inactiveTexture, Coordinate position, int mapID) {
        super(position, activeTexture, inactiveTexture);
        this.game=game;
        this.mapID =mapID;
    }

    @Override
    public void onClick() {
        System.out.println("PICK_MAP BUTTON CLICKED");
        game.setScreen(new GameScreen(game, getMap(this.mapID)));
        dispose();
    }

    private TDMap getMap(int mapID){
        MapLoader mapLoader=new MapLoader(game);
        return mapLoader.getMap(mapID);
    }

}
