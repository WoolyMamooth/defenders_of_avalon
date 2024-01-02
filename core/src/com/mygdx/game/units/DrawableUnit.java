package com.mygdx.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.maps.Coordinate;

public class DrawableUnit {
    public Texture texture;
    public Coordinate position;
    float width;
    float height;

    /**
     * This class represents all units that we want to draw on the screen
     * Towers, Enemies and Heroes all inherit from this.
     * @param texture
     * @param position
     */
    public DrawableUnit(Texture texture, Coordinate position) {
        this.texture = texture;
        this.position = position;
        this.width=texture.getWidth();
        this.height=texture.getHeight();
    }

    public DrawableUnit(Coordinate position){
        this.position=position;
    }

    /**
     * Use this to draw the unit at its position.
     * @param batch
     */
    public void draw(SpriteBatch batch){
        batch.draw(texture, position.x(), position.y(), width, height);
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

    /**
     * @return a coordinate which is visually in the middle of the texture.
     */
    public Coordinate textureCenterPosition(){
        return getPosition().add(new Coordinate(texture.getWidth()/2f, texture.getHeight()/2f));
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void dispose() {
        texture.dispose();
        position=null;
    }
}
