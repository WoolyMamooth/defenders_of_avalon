package com.wooly.avalon.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.Path;
import com.wooly.avalon.units.UnitBuff;

public class SummonCircle extends Enemy{
    float timeOnField, maxTimeOnField;

    /**
     * Special type of enemy used as a lingering effect that disappears almost instantly.
     * @param spawnID
     * @param texture
     * @param position
     */
    public SummonCircle(int spawnID, Texture texture, Coordinate position) {
        super(spawnID, texture, position, 1, 0, 0, 0, 0, 0, "magic", 0);
        this.addBuff(new UnitBuff("damageImmunity",1,10));
        this.addBuff(new UnitBuff("stun",1,10));
        maxTimeOnField=1f;
        timeOnField=0f;
    }
    @Override
    public int update(Path path, float timeSinceLastFrame){
        timeOnField+=timeSinceLastFrame;
        if(timeOnField>=maxTimeOnField){
            this.currentHp=0;
        }
        return 0;
    }
}
