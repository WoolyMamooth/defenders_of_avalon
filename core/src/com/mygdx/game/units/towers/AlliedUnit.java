package com.mygdx.game.units.towers;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.Attacker;
import com.mygdx.game.units.DamagableUnit;
import com.mygdx.game.units.enemies.Enemy;

import java.util.List;

public abstract class AlliedUnit extends DamagableUnit implements Attacker {
    Enemy target=null;
    Coordinate spawnPosition;
    float timeSinceLastAttack=0;
    float attackDelay; //defines how much time should pass between attacks
    float attackRange; //defines how far the tower will target
    float searchRange;
    int damage;
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
     * @param searchRange
     */
    public AlliedUnit(Texture texture, Coordinate position, float movementSpeed, int maxHp, int armor, int magicResistance,int damage,float attackDelay, float searchRange) {
        super(texture, position, movementSpeed, maxHp, armor, magicResistance,true);
        this.spawnPosition=position;
        this.damage=damage;
        this.attackDelay=attackDelay;
        this.attackRange=texture.getWidth()/2f;
        this.searchRange=searchRange;
    }

    @Override
    public void attack() {
        target.takeDamage(damage);
    }

    /**
     * If there is an enemy in range, try to attack, else search for another and move towards it.
     * @param enemies
     * @param timeSinceLastFrame
     */
    public void update(List<Enemy> enemies, float timeSinceLastFrame){
        if(target==null){
            target = getTarget(enemies, attackRange);
            if(target==null){
                target = getTarget(enemies, searchRange, spawnPosition);
                if (target == null) return;
                target.setInCombat(true);
                move(target.getPosition());
                return;
            }
        }else if(target.getCurrentHp()>0) {
            tryAttack(timeSinceLastFrame);
            target.setInCombat(true);
            target.setTarget(this);
        }else{
            target=null;
        }
    }
    protected void tryAttack(float timeSinceLastFrame){
        timeSinceLastAttack += timeSinceLastFrame;
        if (timeSinceLastAttack >= attackDelay) {
            attack();
            timeSinceLastAttack = 0;
        }
    }
    protected Enemy getTarget(List<Enemy> enemies,float range){
        if(enemies==null || enemies.isEmpty()) return null;
        for (Enemy enemy:enemies) {
            if(position.distanceFrom(enemy.getPosition()) <= range){
                return enemy;
            }
        }
        return null;
    }
    private Enemy getTarget(List<Enemy> enemies, float range,Coordinate searchCenter){
        if(enemies==null || enemies.isEmpty()) return null;
        for (Enemy enemy:enemies) {
            if(searchCenter.distanceFrom(enemy.getPosition()) <= range){
                return enemy;
            }
        }
        return null;
    }
    public void die(){
        target.setTarget(null);
        target.setInCombat(false);
    }
    @Override
    public String toString() {
        return "AlliedUnit{" +
                "texture=" + texture +
                ", position=" + position +
                ", attackDelay=" + attackDelay +
                ", attackRange=" + attackRange +
                ", searchRange=" + searchRange +
                ", damage=" + damage +
                '}';
    }
}
