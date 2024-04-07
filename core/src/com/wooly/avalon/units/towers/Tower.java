package com.wooly.avalon.units.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.Attacker;
import com.wooly.avalon.units.DrawableUnit;
import com.wooly.avalon.units.UnitBuff;
import com.wooly.avalon.units.enemies.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Tower extends DrawableUnit implements Attacker {
    protected float timeSinceLastAttack=0;
    int towerSpawnID; //keeps track of the order towers were built in
    protected float attackDelay; //defines how much time should pass between attacks
    protected float range; //defines how far the tower will target
    public TowerUpgrade[] upgrades;
    public String name; //only needed in shop
    public String description; //only needed in shop
    private List<UnitBuff> buffs;

    public Tower(Texture texture, Coordinate position,int towerSpawnID,float attackDelay,float range,TowerUpgrade[] upgrades) {
        super(texture, position);
        this.towerSpawnID=towerSpawnID;
        this.range=range;
        this.attackDelay=attackDelay;
        this.upgrades=upgrades;
        this.buffs=new ArrayList<>();
        name="name";
        description="description";
    }
    public abstract void attack();
    public void update(List<Enemy> enemies,float timeSinceLastFrame){
        updateBuffs(timeSinceLastFrame);
    }
    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
    }
    /**
     * Increases the level of the specified stat by one.
     */
    public void upgrade(String stat){
        for(TowerUpgrade u:this.upgrades){
            if(Objects.equals(u.stat, stat)){
                u.levelUp();
                applyUpgrade(u);
            }
        }
    }
    /**
     * Returns the cost of the upgrade if it can be upgraded, 0 otherwise.
     * @param stat
     */
    public int costOfUpgrade(String stat){
        //find the upgrade we need to level up
        for(TowerUpgrade u:this.upgrades){
            if(Objects.equals(u.stat, stat)){
                if(!u.isMaxed()){ //if it wasn't maxed yet
                    return u.getCost();
                }
                break;
            }
        }
        return 0;
    }

    /**
     * This increases the stat of the tower, all possible upgrades have to be defined here!
     */
    protected void applyUpgrade(TowerUpgrade u){
        switch (u.stat){
            case "range":
                this.range+=u.getIncrease();
                break;
            case "atkSpeed":
            case "attackSpeed":
            case "summonSpeed":
                //10 attackspeed = -0.1f delay between attacks
                this.attackDelay-=u.getIncrease()/100f;
                break;
            default:
                System.out.println("No such upgrade");
                break;
        }
    }
    public void addBuff(UnitBuff buff){
        this.buffs.add(buff);
        applyBuff(buff,false);
    }
    public void removeBuff(UnitBuff buff){
        applyBuff(buff,true);
        this.buffs.remove(buff);
    }
    protected void applyBuff(UnitBuff buff,boolean removeMode) {
        float modifier = buff.getModifier();
        if (removeMode) modifier = -modifier;
        switch (buff.stat) {
            case "atkSpeed":
            case "attackSpeed":
            case "attackDelay":
                attackDelay -= modifier/100f;
                System.out.println(attackDelay);
                break;
            default:
                System.out.println("No such buff for towers: "+buff.stat);
        }
    }
    public void updateBuffs(float timeSinceLastFrame){
        //update the buffs and remove the ones that have expired
        List<UnitBuff> shouldBeRemoved=new ArrayList<>();
        for (UnitBuff buff:buffs) {
            if (buff.update(timeSinceLastFrame)){
                shouldBeRemoved.add(buff);
            }
        }
        for (UnitBuff buff:shouldBeRemoved) {
            removeBuff(buff);
        }
    }
    public int getTowerSpawnID() {
        return towerSpawnID;
    }
    public float getAttackDelay() {
        return attackDelay;
    }
    public float getRange() {
        return range;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Tower{" +
                "towerSpawnID=" + towerSpawnID +
                ", attackDelay=" + attackDelay +
                ", range=" + range +
                ", position=" + position +
                '}';
    }
}