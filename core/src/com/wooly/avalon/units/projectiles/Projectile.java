package com.wooly.avalon.units.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.MovableUnit;
import com.wooly.avalon.units.enemies.Enemy;

import java.util.List;

public class Projectile extends MovableUnit {
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
    /**
     * Moves towards the goal and returns true if reached.
     * @return
     */
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
                dealDamage(target);
                return true;
                //hit the target
            }
        }
        return false;
    }

    /**
     * This update makes the projectile deal AoE damage.
     * @param enemies
     * @return
     */
    public boolean update(List<Enemy> enemies, float aoeRange){
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
                for (Enemy enemy:enemies) {
                    if (enemy.position.distanceFrom(targetLocation)<=aoeRange) {
                        dealDamage(enemy);
                    }
                }
                return true;
            }else {
                for (Enemy enemy:enemies) {
                    if (enemy.position.distanceFrom(target.position)<=aoeRange) {
                        dealDamage(enemy);
                    }
                }
                return true;
                //hit the target
            }
        }
        return false;
    }
    protected void dealDamage(Enemy enemy){
        enemy.takeDamage(damage,damageType);
    }
}
