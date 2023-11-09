package com.mygdx.game.units.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.MovableUnit;

public class Projectile extends MovableUnit {
    boolean direction; //0 means left 1 means right
    int damage;

    public Projectile(Texture texture, Coordinate position, float movementSpeed,int damage) {
        super(texture, position, movementSpeed);
        this.damage=damage;
    }

    public boolean update(Coordinate goal){
        move(goal);
        if(atCoordinate(goal)){
            return true;
            //hit the target
        }
        return false;
    }
}
