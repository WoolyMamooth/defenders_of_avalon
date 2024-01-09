package com.wooly.avalon.screens.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.MapLoader;
import com.wooly.avalon.maps.TDMap;
import com.wooly.avalon.screens.GameScreen;

public class PickMapButton extends Button {
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
        game.setScreen(new GameScreen(game, getMap(this.mapID),mapID));
        dispose();
    }

    private TDMap getMap(int mapID){
        MapLoader mapLoader=new MapLoader(game);
        return mapLoader.getMap(mapID);
    }

}
