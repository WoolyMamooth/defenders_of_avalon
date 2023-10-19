package com.mygdx.game.screens.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.TDMap;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;

public class PickMapButton extends MenuButton {
    TDMap map;
    public PickMapButton(MenuScreen screen, Texture activeTexture, Texture inactiveTexture, int position, TDMap map) {
        super(screen, activeTexture, inactiveTexture, position);
        this.map=map;
    }

    @Override
    public void onClick() {
        System.out.println("PICK_MAP BUTTON CLICKED");
        screen.dispose();
        screen.game.setScreen(new GameScreen(screen.game, map));
    }

}
