package com.mygdx.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;

public class DrawableUnit {
    /* This class represents all units that we want to draw on the screen
        Towers, Enemies and Heroes all inherit from this
    */
    public Texture texture;
    public Coordinate position;

    public DrawableUnit(Texture texture, Coordinate position) {
        this.texture = texture;
        this.position = position;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public void dispose() {
        texture.dispose();
        position=null;
    }
}
