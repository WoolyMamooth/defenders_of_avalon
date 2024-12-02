package com.wooly.avalon.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.Path;
import com.wooly.avalon.maps.TDMap;
import com.wooly.avalon.units.UnitBuff;

public class Golem extends Enemy{
    /**
     * Heals itself, sometimes becomes immune to damage and summons smaller golems when it dies
     * @param spawnID
     * @param texture
     * @param position
     * @param health
     * @param armor
     * @param magicResistance
     * @param movementSpeed
     * @param damageToPlayer
     * @param damage
     * @param damageType
     * @param goldDropped
     * @param map
     */
    TDMap map;
    boolean canSplit;
    float immunityCooldownMax,immunityCooldown;
    float immunityDuration;
    public Golem(int spawnID, Texture texture, Coordinate position, int health, int armor, int magicResistance, float movementSpeed, int damageToPlayer, int damage, String damageType, int goldDropped, TDMap map, boolean isMiniGolem) {
        super(spawnID, texture, position, health, armor, magicResistance, movementSpeed, damageToPlayer, damage, damageType, goldDropped);
        this.map=map;
        this.canSplit=!isMiniGolem;
        healingAmount=3;
        immunityCooldownMax=15;
        immunityCooldown=2;
        immunityDuration=3f;
        if(isMiniGolem){
            healingAmount=0;
            immunityDuration=1f;
        }
    }
    private void split(){
        map.addExtraEnemy("mini_golem",position.subtract(new Coordinate(10,0)),previousPathCoordinateID);
        map.addExtraEnemy("mini_golem",position.add(new Coordinate(10,0)),previousPathCoordinateID);
        map.addExtraEnemy("mini_golem",position.subtract(new Coordinate(0,10)),previousPathCoordinateID);
        map.addExtraEnemy("mini_golem",position.add(new Coordinate(0,10)),previousPathCoordinateID);
    }

    /**
     * While immune it can't move or deal damage.
     * @param timeSinceLastFrame
     */
    private void becomeImmune(float timeSinceLastFrame){
        if(currentHp<maxHp){
            immunityCooldown-=timeSinceLastFrame;
            if(immunityCooldown<=0){
                this.addBuff(new UnitBuff("damageImmunity",1,immunityDuration));
                this.addBuff(new UnitBuff("stun",1,immunityDuration));
                immunityCooldown=immunityCooldownMax;
            }
        }
    }

    @Override
    public int update(Path path, float timeSinceLastFrame) {
        becomeImmune(timeSinceLastFrame);
        return super.update(path, timeSinceLastFrame);
    }

    @Override
    public void die() {
        if(canSplit) split();
        super.die();
    }
}
