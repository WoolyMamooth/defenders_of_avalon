package com.mygdx.game.units.towers;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.enemies.Enemy;

import java.util.List;

public class Summon extends AlliedUnit{
    /**
     * Allied units are units which are on the players side such as Summons and Heroes.
     *
     * @param texture
     * @param position
     * @param movementSpeed
     * @param maxHp
     * @param armor
     * @param magicResistance
     * @param damage
     * @param attackDelay
     */
    public Summon(Texture texture, Coordinate position, float movementSpeed, int maxHp, int armor, int magicResistance, int damage, float attackDelay, float searchRange) {
        super(texture, position, movementSpeed, maxHp, armor, magicResistance, damage, attackDelay, searchRange);
    }
    /**
     * @param enemies should be the list of enemies in the towers range.
     * @param timeSinceLastFrame
     */
    public void update(List<Enemy> enemies, float timeSinceLastFrame){
        target=getTarget(enemies,attackRange);
        if(target!=null) {
            //if we found a target attack it if possible
            timeSinceLastAttack += timeSinceLastFrame;
            if (timeSinceLastAttack >= attackDelay) {
                attack();
                timeSinceLastAttack = 0;
            }
        }else {
            target=getTarget(enemies,searchRange,spawnPosition); //find an enemy in the towers range to move towards
            if(target==null) return;
            move(target.textureCenterPosition());
        }
    }
    private Enemy getTarget(List<Enemy> enemies,float range){
        if(enemies==null || enemies.isEmpty()) return null;
        for (Enemy enemy:enemies) {
            if(textureCenterPosition().distanceFrom(enemy.textureCenterPosition()) <= range){
                return enemy;
            }
        }
        return null;
    }
    private Enemy getTarget(List<Enemy> enemies, float range,Coordinate searchCenter){
        if(enemies==null || enemies.isEmpty()) return null;
        for (Enemy enemy:enemies) {
            if(searchCenter.distanceFrom(enemy.textureCenterPosition()) <= range){
                return enemy;
            }
        }
        return null;
    }
}
