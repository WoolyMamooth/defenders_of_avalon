package com.wooly.avalon.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.TDMap;

public class Golem extends Enemy{
    /**
     * Summons smaller golems when it dies
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
    public Golem(int spawnID, Texture texture, Coordinate position, int health, int armor, int magicResistance, float movementSpeed, int damageToPlayer, int damage, String damageType, int goldDropped, TDMap map) {
        super(spawnID, texture, position, health, armor, magicResistance, movementSpeed, damageToPlayer, damage, damageType, goldDropped);
    }
}
