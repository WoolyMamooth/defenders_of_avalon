package com.wooly.avalon.screens.buttons;

import static com.wooly.avalon.TDGame.trueInput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.other.UIElement;

public abstract class Clickable extends UIElement {
    public float  width, height;

    public Clickable(Coordinate position){
        this.position = position;
    }
    public boolean isActive() {
        Coordinate input=trueInput();
        if (input.x() < position.x() + this.width && input.x()  > position.x() &&
                TDGame.SCREEN_HEIGHT - input.y()  < position.y() + this.height &&
                TDGame.SCREEN_HEIGHT - input.y() > position.y()) {
            return true;
        }
        return false;
    }

    /**
     * Draws the clickable and also checks if it has been clicked.
    */
    public void drawCheckClick(SpriteBatch batch) {
        draw(batch);
        if(isActive() && Gdx.input.justTouched()){
            onClick();
        }
    }
    public abstract void onClick();
    public abstract void dispose();
}
