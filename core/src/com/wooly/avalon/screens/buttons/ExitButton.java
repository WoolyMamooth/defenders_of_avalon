package com.wooly.avalon.screens.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;

public class ExitButton extends Button {
    public ExitButton(Texture activeTexture, Texture inactiveTexture, Coordinate position) {
        super(position,activeTexture, inactiveTexture);
    }

    @Override
    public void onClick() {
        System.out.println("EXIT BUTTON CLICKED");
        Gdx.app.exit();
    }
}
