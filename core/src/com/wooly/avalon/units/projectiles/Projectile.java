package com.wooly.avalon.units.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.MovableUnit;
import com.wooly.avalon.units.enemies.Enemy;

public class Projectile extends MovableUnit {
    boolean direction; //0 means left 1 means right
    int damage;
    String damageType;
    Enemy target;
    Coordinate targetLocation;

    public Projectile(Texture texture, Coordinate position, float movementSpeed, int damage,String damageType, Enemy target) {
        super(texture, position, movementSpeed);
        this.damage=damage;
        this.target=target;
        this.damageType=damageType;
    }

    //move towards the goal and return true if reached
    public boolean update(){
        Coordinate goal;
        boolean targetDead=false;
        if(target.getPosition()==null) {
            goal=targetLocation;
            targetDead=true;
            //the enemy is already dead so the projectile achieved its goal
        }else{
            goal=target.textureCenterPosition();
            targetLocation=goal;
        }
        move(goal);
        if(atCoordinate(goal)){
            if(targetDead){
                return true;
            }else {
                dealDamage();
                return true;
                //hit the target
            }
        }
        return false;
    }
    private void dealDamage(){
        target.takeDamage(damage,damageType);
    }
}
