package com.wooly.avalon.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.Path;
import com.wooly.avalon.maps.TDMap;

public class Necromancer extends Enemy{
    TDMap map;
    float summonDelay,summonMaxDelay=6f;
    public Necromancer(int spawnID, Texture texture, Coordinate position, int health, int armor, int magicResistance, float movementSpeed, int damageToPlayer, int damage, String damageType, int goldDropped, TDMap map) {
        super(spawnID, texture, position, health, armor, magicResistance, movementSpeed, damageToPlayer, damage, damageType, goldDropped);
        this.map=map;
        summonDelay=summonMaxDelay;
    }
    private void summonSkeleton(float timeSinceLastFrame){
        summonDelay-=timeSinceLastFrame;
        if(summonDelay<=0){
            summonDelay=summonMaxDelay;
            map.addExtraEnemy("skeleton",position,previousPathCoordinateID);
        }
    }
    @Override
    public int update(Path path, float timeSinceLastFrame) {
        summonSkeleton(timeSinceLastFrame);
        return super.update(path, timeSinceLastFrame);
    }
}
