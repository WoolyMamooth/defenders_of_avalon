package com.wooly.avalon.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.Path;
import com.wooly.avalon.units.AlliedUnit;
import com.wooly.avalon.units.UnitBuff;

public class Dragon extends Enemy{
    float fireBreathMaxCooldown,fireBreathCooldown;
    public Dragon(int spawnID, Texture texture, Coordinate position, int health, int armor, int magicResistance, float movementSpeed, int damageToPlayer, int damage, String damageType, int goldDropped) {
        super(spawnID, texture, position, health, armor, magicResistance, movementSpeed, damageToPlayer, damage, damageType, goldDropped);
        fireBreathMaxCooldown=10f;
        fireBreathCooldown=fireBreathMaxCooldown;
    }
    private void breathFire(AlliedUnit target){
        target.addBuff(new UnitBuff("onFire",5,10));
    }
    @Override
    public int update(Path path, float timeSinceLastFrame) {
        fireBreathCooldown-=timeSinceLastFrame;
        if(inCombat() && fireBreathCooldown<=0){
            breathFire(target);
            fireBreathCooldown=fireBreathMaxCooldown;
        }
        return super.update(path, timeSinceLastFrame);
    }
}
