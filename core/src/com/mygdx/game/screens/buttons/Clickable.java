package com.mygdx.game.screens.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.screens.MenuScreen;

public abstract class Clickable {
    //
    protected Coordinate position;
    public float  width, height;
    protected Texture activeTexture, inactiveTexture;

    public Clickable(Coordinate position, Texture activeTexture, Texture inactiveTexture) {
        this.position = position;
        this.width = activeTexture.getWidth() * MenuScreen.MENU_SCALE;
        this.height = activeTexture.getHeight() * MenuScreen.MENU_SCALE;
        this.activeTexture = activeTexture;
        this.inactiveTexture = inactiveTexture;
    }

    protected boolean isActive() {
        if (Gdx.input.getX() < position.x() + this.width && Gdx.input.getX() > position.x() &&
                TDGame.SCREEN_HEIGHT - Gdx.input.getY() < position.y() + this.height &&
                TDGame.SCREEN_HEIGHT - Gdx.input.getY() > position.y()) {
            return true;
        }
        return false;
    }

    /**
     * Draws the button.
     * If you want to check if it has been clicked use drawCheckClick() instead;
     */
    public void draw(SpriteBatch batch) {
        batch.draw((isActive() ? activeTexture : inactiveTexture), position.x(), position.y(), this.width, this.height);
    }
    /**
     * Draws the button and also checks if it has been clicked.
    */
    public void drawCheckClick(SpriteBatch batch) {
        draw(batch);
        if(isActive() && Gdx.input.justTouched()){
            onClick();
        }
    }
    public abstract void onClick();

    public Texture getTexture() {
        if (this.isActive()) {
            return this.activeTexture;
        }
        return this.inactiveTexture;
    }

    public void dispose(){
        activeTexture.dispose();
        inactiveTexture.dispose();
    }

    public Coordinate getPosition() {
        return position;
    }
}
