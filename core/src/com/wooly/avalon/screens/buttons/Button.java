package com.wooly.avalon.screens.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.MenuScreen;

public abstract class Button extends Clickable{
    protected Texture activeTexture, inactiveTexture;

    /**
     * Extends on Clickable with 2 textures which get drawn based on if the player is clicking one or not.
     * @param position
     * @param activeTexture
     * @param inactiveTexture
     */
    public Button(Coordinate position, Texture activeTexture, Texture inactiveTexture) {
        super(position);
        this.activeTexture = activeTexture;
        this.inactiveTexture = inactiveTexture;
        this.width = activeTexture.getWidth() * MenuScreen.MENU_SCALE;
        this.height = activeTexture.getHeight() * MenuScreen.MENU_SCALE;
    }
    /**
     * Draws the button.
     * If you want to check if it has been clicked use drawCheckClick() instead;
     */
    public void draw(SpriteBatch batch) {
        batch.draw((isActive() ? activeTexture : inactiveTexture), position.x(), position.y(), this.width, this.height);
    }
    public Texture getTexture() {
        if (this.isActive()) {
            return this.activeTexture;
        }
        return this.inactiveTexture;
    }
    @Override
    public void dispose() {
        activeTexture.dispose();
        inactiveTexture.dispose();
    }
}
