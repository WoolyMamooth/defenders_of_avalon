package com.wooly.avalon.screens.other;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.maps.Coordinate;

public abstract class UIElement {
    public Coordinate position;
    public abstract void draw(SpriteBatch batch);
    public Coordinate getPosition() {
        return position;
    }
    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
