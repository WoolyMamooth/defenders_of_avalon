package com.wooly.avalon.units;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.Attacker;
import com.wooly.avalon.units.DamagableUnit;
import com.wooly.avalon.units.enemies.Enemy;

import java.util.List;

public abstract class AlliedUnit extends DamagableUnit implements Attacker {
    protected Enemy target=null;
    protected Coordinate searchCenterPosition;
    float timeSinceLastAttack=0;
    float attackDelay; //defines how much time should pass between attacks
    float attackRange; //defines how far the unit will be able to hit
    protected float searchRange; //defines how far the unit will go for new targets
    int damage;
    String damageType;
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
    public AlliedUnit(Texture texture, Coordinate position, float movementSpeed, int maxHp, int armor, int magicResistance,int damage,float attackDelay, float searchRange,String damageType) {
        super(texture, position, movementSpeed, maxHp, armor, magicResistance,true);
        this.searchCenterPosition=position;
        this.damage=damage;
        this.attackDelay=attackDelay;
        this.attackRange=texture.getWidth()/2f;
        this.searchRange=searchRange;
        this.damageType=damageType;
    }
    @Override
    public void attack() {
        target.takeDamage(damage,damageType);
    }
    protected void tryAttack(float timeSinceLastFrame){
        timeSinceLastAttack += timeSinceLastFrame;
        if (timeSinceLastAttack >= attackDelay) {
            attack();
            timeSinceLastAttack = 0;
        }
    }
    /**
     * If there is an enemy in range, try to attack, else search for another and move towards it.
     * @param enemies
     * @param timeSinceLastFrame
     */
    public void update(List<Enemy> enemies, float timeSinceLastFrame){
        updateBuffs(timeSinceLastFrame);
        if(target!=null){//if we have a target
            try{
                //turn around if needed
                if (!facingLeft && target.textureCenterPosition().x() < textureCenterPosition().x())
                    turnAround();
                if (facingLeft && target.textureCenterPosition().x() > textureCenterPosition().x())
                    turnAround();
            }catch (NullPointerException n){
                //caused by target.position being set to null when they die, this doesn't actually cause
                //problems because it fixes itself in the next loop
            }

            if(inRange(target,attackRange)) { //if they are in range
                if (target.getCurrentHp() > 0) { //and aren't dead yet
                    tryAttack(timeSinceLastFrame); //attack if we can
                    //target.setTarget(this);
                }else{
                    target=null;
                }
            }else{ //if not in attack range
                move(target.getPosition()); //move towards them since they are far
            }

        }else{//if we dont have a target
            //search attack range for target
            target = getTarget(enemies, attackRange,position);
            if(target==null){ //if there is noone in attack range
                //search searchrange for target
                target = getTarget(enemies, searchRange, searchCenterPosition);
                if (target == null) return; //still nothing then just stand still
                move(target.getPosition()); //move towards them since they are far
            }
            if(!target.inCombat())target.setTarget(this); //if we found one, set their target to this
        }
    }
    protected Enemy getTarget(List<Enemy> enemies, float range,Coordinate searchCenter){
        if(enemies==null || enemies.isEmpty()) return null;
        for (Enemy enemy:enemies) {
            if(searchCenter.distanceFrom(enemy.getPosition()) <= range){
                return enemy;
            }
        }
        return null;
    }
    public void setTarget(Enemy target){
        this.target=target;
    }

    @Override
    protected void applyBuff(UnitBuff buff, boolean removeMode) {
        int modifier=buff.getModifier();
        if(removeMode) modifier*=-1;
        switch (buff.stat){
            case "damage":
                damage+=modifier;
                break;
            case "attackSpeed":
            case "attackDelay":
                attackDelay-=modifier;
                break;
            default:
                super.applyBuff(buff, removeMode);
        }
    }

    public void die(){
        if(target!=null) {
            target.setTarget(null);
        }
        super.die();
    }
    public void setSearchCenterPosition(Coordinate searchCenterPosition) {
        this.searchCenterPosition = searchCenterPosition;
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
