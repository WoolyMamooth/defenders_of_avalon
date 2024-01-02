package com.mygdx.game.units.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.MovableUnit;
import com.mygdx.game.units.enemies.Enemy;

public class Projectile extends MovableUnit {
    boolean direction; //0 means left 1 means right
    int damage;
    Enemy target;

    public Projectile(Texture texture, Coordinate position, float movementSpeed, int damage, Enemy target) {
        super(texture, position, movementSpeed);
        this.damage=damage;
        this.target=target;
    }

    //move towards the goal and return true if reached
    public boolean update(){
        if(target.getPosition()==null) return true; //the enemy is already dead so the projectile achieved its goal
        Coordinate goal=target.textureCenterPosition();
        move(goal);
        if(atCoordinate(goal)){
            dealDamage();
            return true;
            //hit the target
        }
        return false;
    }
    private void dealDamage(){
        target.takeDamage(damage);
    }
}
