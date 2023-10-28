package com.mygdx.game.screens.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.MapLoader;
import com.mygdx.game.maps.TDMap;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;

public class PickMapButton extends MenuButton {
    int mapID;
    MenuScreen screen;
    public PickMapButton(MenuScreen screen, Texture activeTexture, Texture inactiveTexture, int position, int mapID) {
        super(screen.game, activeTexture, inactiveTexture, position);
        this.screen=screen;
        this.mapID =mapID;
    }

    @Override
    public void onClick() {
        System.out.println("PICK_MAP BUTTON CLICKED");
        screen.game.setScreen(new GameScreen(screen.game, getMap(this.mapID)));
        screen.dispose();
    }

    private TDMap getMap(int mapID){
        MapLoader mapLoader=new MapLoader(game);
        return mapLoader.getMap(mapID);
    }

}
