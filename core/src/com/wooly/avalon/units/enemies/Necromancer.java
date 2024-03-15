package com.wooly.avalon.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.Path;
import com.wooly.avalon.maps.TDMap;

public class Necromancer extends Enemy{
    TDMap map;
    String summonName;
    float summonDelay,summonMaxDelay;
    public Necromancer(int spawnID, Texture texture, Coordinate position, int health, int armor, int magicResistance, float movementSpeed, int damageToPlayer, int damage, String damageType, int goldDropped, TDMap map) {
        super(spawnID, texture, position, health, armor, magicResistance, movementSpeed, damageToPlayer, damage, damageType, goldDropped);
        this.map=map;
        summonName="skeleton";
        summonMaxDelay=6f;
        summonDelay=summonMaxDelay;
    }
    protected void summonNext(float timeSinceLastFrame, Coordinate pos){
        summonDelay-=timeSinceLastFrame;
        if(summonDelay<=0){
            summonDelay=summonMaxDelay;
            map.addExtraEnemy(summonName,pos,previousPathCoordinateID);
        }
    }
    @Override
    public int update(Path path, float timeSinceLastFrame) {
        summonNext(timeSinceLastFrame, position);
        return super.update(path, timeSinceLastFrame);
    }
}
