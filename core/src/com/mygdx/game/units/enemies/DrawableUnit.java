package com.mygdx.game.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;

public class DrawableUnit {
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
