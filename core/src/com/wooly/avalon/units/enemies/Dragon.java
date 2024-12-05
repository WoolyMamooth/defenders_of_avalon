package com.wooly.avalon.units.enemies;

import static com.wooly.avalon.TDGame.fetchTexture;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.Path;
import com.wooly.avalon.units.AlliedUnit;
import com.wooly.avalon.units.UnitBuff;
import com.wooly.avalon.units.projectiles.ProjectileSpawner;

public class Dragon extends Enemy{
    float fireBreathMaxCooldown,fireBreathCooldown;
    Texture fireTexture;
    public Dragon(int spawnID, Texture texture, Coordinate position, int health, int armor, int magicResistance, float movementSpeed, int damageToPlayer, int damage, String damageType, int goldDropped) {
        super(spawnID, texture, position, health, armor, magicResistance, movementSpeed, damageToPlayer, damage, damageType, goldDropped);
        fireBreathMaxCooldown=6f;
        fireBreathCooldown=fireBreathMaxCooldown;
        fireTexture=fetchTexture("enemies/dragon_fire");
    }
    private void breatheFire(AlliedUnit target){
        target.addBuff(new UnitBuff("onFire",5,10,fireTexture));
    }
    @Override
    public int update(Path path, float timeSinceLastFrame) {
        fireBreathCooldown-=timeSinceLastFrame;
        if(inCombat() && inRange(target,attackRange) && fireBreathCooldown<=0){
            breatheFire(target);
            fireBreathCooldown=fireBreathMaxCooldown;
        }
        return super.update(path, timeSinceLastFrame);
    }
}
