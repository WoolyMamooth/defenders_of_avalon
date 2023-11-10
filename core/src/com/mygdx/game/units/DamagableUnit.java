package com.mygdx.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;

public class DamagableUnit extends MovableUnit{
    /**
     * Extends on Movable units with health and resistances
     * Anything that can be hit should inherit from this
     */
    int health; //amount of damage that can be taken before dying
    int armor; //reduces physical damage taken
    int magicResistance; //reduces magical damage taken

    public DamagableUnit(Texture texture, Coordinate position, float movementSpeed, int health, int armor, int magicResistance) {
        super(texture, position, movementSpeed);
        this.health = health;
        this.armor = armor;
        this.magicResistance = magicResistance;
    }

    public void takeDamage(int damage) {
        //TODO
        this.health-=damage;
    }

    //triggered when health reaches zero
    public void die(){
        // die, duh
        //TODO might need some more work
        movementSpeed=0;
        super.dispose();
    }

    public int getHealth() {
        return health;
    }

    public int getArmor() {
        return armor;
    }

    public int getMagicResistance() {
        return magicResistance;
    }

}
