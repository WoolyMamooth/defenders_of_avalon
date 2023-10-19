package com.mygdx.game.screens.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.TDMap;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;

public class PickMapButton extends MenuButton {
    int map;
    public PickMapButton(MenuScreen screen, Texture activeTexture, Texture inactiveTexture, int position, int mapID) {
        super(screen, activeTexture, inactiveTexture, position);
        this.map=mapID;
    }

    @Override
    public void onClick() {
        System.out.println("PICK_MAP BUTTON CLICKED");
        screen.dispose();
        screen.game.setScreen(new GameScreen(screen.game, getMap(this.map)));
    }

    private TDMap getMap(int mapID){
        return new TDMap(mapID);
    }

}
