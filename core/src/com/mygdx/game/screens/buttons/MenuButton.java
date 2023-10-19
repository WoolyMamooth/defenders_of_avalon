package com.mygdx.game.screens.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.TDGame;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.screens.MenuScreen;

import java.util.concurrent.TimeUnit;

public abstract class MenuButton {
    protected final MenuScreen screen;
    public float x, y, width, height;
    Texture activeTexture, inactiveTexture;
    int position;

    public MenuButton(MenuScreen screen, Texture activeTexture, Texture inactiveTexture, int position) {
        this.screen = screen;
        this.activeTexture = activeTexture;
        this.inactiveTexture = inactiveTexture;
        this.position = position;
        this.width = activeTexture.getWidth() * screen.MENU_SCALE;
        this.height = activeTexture.getHeight() * screen.MENU_SCALE;
        this.x = TDGame.SCREEN_WIDTH / 2 + width;
        this.y = TDGame.SCREEN_HEIGHT - ((TDGame.SCREEN_HEIGHT * screen.MENU_SPACING) * position);
    }

    public Texture getTexture() {
        if (this.isActive()) {
            return this.activeTexture;
        }
        return this.inactiveTexture;
    }

    public boolean isActive() {
        if (Gdx.input.getX() < this.x + this.width && Gdx.input.getX() > this.x &&
                TDGame.SCREEN_HEIGHT - Gdx.input.getY() < this.y - this.height && TDGame.SCREEN_HEIGHT - Gdx.input.getY() > this.y - this.height * 2) {
            return true;
        }
        return false;
    }

    public void draw() {
        screen.game.batch.draw((this.isActive() ? this.activeTexture : this.inactiveTexture), this.x, this.y, this.width, this.height);
    }

    public abstract void onClick(); // TODO EXTREMELY IMPORTANT
    // currently every button gets clicked when the user clicks one for some reason
    // sleep didn't solve it and game.batch.end() gave a huge error

    @Override
    public String toString() {
        return "MenuButton{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", activeTexture=" + activeTexture +
                ", inactiveTexture=" + inactiveTexture +
                ", position=" + position +
                ", isActive=" + this.isActive() +
                '}';
    }
}

