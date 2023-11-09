package com.mygdx.game.screens.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.screens.MenuScreen;

public abstract class MenuButton extends Clickable{
    int positionInMenu;
    public MenuButton(Texture activeTexture, Texture inactiveTexture, int positionInMenu) {
        super(new Coordinate(0,0),activeTexture,inactiveTexture);
        this.positionInMenu = positionInMenu;
        this.position =new Coordinate (TDGame.SCREEN_WIDTH / 2f,TDGame.SCREEN_HEIGHT - ((TDGame.SCREEN_HEIGHT * MenuScreen.MENU_SPACING) * positionInMenu));
    }

    @Override
    public String toString() {
        return "MenuButton{" +
                "positionInMenu=" + positionInMenu +
                ", position=" + position +
                ", width=" + width +
                ", height=" + height +
                ", activeTexture=" + activeTexture +
                ", inactiveTexture=" + inactiveTexture +
                '}';
    }
}

