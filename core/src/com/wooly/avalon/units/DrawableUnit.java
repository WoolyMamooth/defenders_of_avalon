package com.wooly.avalon.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.other.UIElement;

public class DrawableUnit extends UIElement {
    public Texture texture;
    protected float width;
    protected float height;
    protected boolean facingLeft=false; //false=facing right, true=facing left

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
        if (facingLeft){
            batch.draw(texture,position.x()+width,position.y(),-width,height);
        }else {
            batch.draw(texture, position.x(), position.y(), width, height);
        }
    }
    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * @return a coordinate which is visually in the middle of the texture.
     */
    public Coordinate textureCenterPosition(){
        return position.add(new Coordinate(texture.getWidth()/2f, texture.getHeight()/2f));
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
    protected void turnAround(){
        facingLeft =!facingLeft;
    }
    public void dispose() {
        texture.dispose();
        position=null;
        //position=new Coordinate(-100,-100); //just shove them offscreen for a frame to be sure, returning null here caused some nullpointer exceptions
    }
}
