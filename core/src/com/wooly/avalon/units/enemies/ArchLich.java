package com.wooly.avalon.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.TDMap;

public class ArchLich extends Necromancer{
    String[] possibleSummons={"giant_skeleton","necromancer","skeleton_dragon"};
    int summonCounter=0;
    public ArchLich(int spawnID, Texture texture, Coordinate position, int health, int armor, int magicResistance, float movementSpeed, int damageToPlayer, int damage, String damageType, int goldDropped, TDMap map) {
        super(spawnID, texture, position, health, armor, magicResistance, movementSpeed, damageToPlayer, damage, damageType, goldDropped, map);
        summonName=possibleSummons[summonCounter];
        summonMaxDelay=12f;
    }

    @Override
    protected void summonNext(float timeSinceLastFrame, Coordinate pos) {
        super.summonNext(timeSinceLastFrame, pos);
        summonCounter++;
        if(summonCounter>=possibleSummons.length) summonCounter=0;
        summonName=possibleSummons[summonCounter];
    }
}
