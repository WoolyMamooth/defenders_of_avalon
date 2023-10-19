package com.mygdx.game.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.maps.PathMarker;

public class Enemy {
    Coordinate position;
    Texture texture;
    int health;
    int armor;
    int magicResistance;
    float movementSpeed;
    int previousPathMarkerID=0;



    public Enemy(Coordinate position, Texture texture, int health, int armor, int magicResistance, float movementSpeed) {
        this.position = position;
        this.texture = texture;
        this.health = health;
        this.armor = armor;
        this.magicResistance = magicResistance;
        this.movementSpeed = movementSpeed;
    }

    //TODO
    public void attack(){
        //attack a summon or hero
    }
    public void move(PathMarker[] path){
        // move towards the next path marker by movementSpeed amount
    }

    public void die(){
        // die, duh
    }
}
