package com.mygdx.game.screens.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.screens.MenuScreen;

public class ExitButton extends MenuButton {
    MenuScreen screen;
    public ExitButton(Texture activeTexture, Texture inactiveTexture, int position) {
        super(activeTexture, inactiveTexture, position);
    }

    @Override
    public void onClick() {
        System.out.println("EXIT BUTTON CLICKED");
        Gdx.app.exit();
    }
}
