package com.wooly.avalon.screens.buttons;

import com.badlogic.gdx.graphics.Color;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.MapLoader;
import com.wooly.avalon.maps.TDMap;
import com.wooly.avalon.screens.GameScreen;

public class PickMapButton extends CustomButton {
    int mapID;
    TDGame game;

    /**
     * Sets the screen to a new GameScreen with the map with the given ID loaded in.
     */
    public PickMapButton(TDGame game,int mapID,Coordinate position, String text, int fontsize, Color textColor, Color backgroundColor, float width, float height) {
        super(position, text, fontsize, textColor, backgroundColor, width, height);
        this.game=game;
        this.mapID=mapID;
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
