package com.mygdx.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;

public class MovableUnit extends DrawableUnit{
    /**
     * Adds movement logic to DrawableUnit, used for anything that has a texture and moves.
     * @param texture
     * @param position
     */
    float movementSpeed;
    public MovableUnit(Texture texture, Coordinate position,float movementSpeed) {
        super(texture, position);
        this.movementSpeed=movementSpeed;
    }

    protected void move(Coordinate goal, float speed){
        Coordinate movementDirection=goal.subtract(position).normalize(); //get a unit vector pointing to goal
        position=position.add(movementDirection.multiplyByScalar(speed* Gdx.graphics.getDeltaTime())); //move
        //return position;
    }

    //checks if the unit has reached a given coordinate
    protected boolean atCoordinate(Coordinate coordinate){
        if(
                position.x()>coordinate.x()-texture.getWidth()/2f &&
                        position.y()>coordinate.y()-texture.getHeight()/2f &&
                        position.x()<coordinate.x()+texture.getWidth()/2f &&
                        position.y()<coordinate.y()+texture.getHeight()/2f
        ) return true;
        return false;
    }

    @Override
    public float getMovementSpeed() {
        return movementSpeed;
    }
}
