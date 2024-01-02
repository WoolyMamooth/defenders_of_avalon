package com.mygdx.game.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.maps.Path;
import com.mygdx.game.units.Attacker;
import com.mygdx.game.units.DamagableUnit;

public class Enemy extends DamagableUnit implements Attacker {
    int spawnID; // keeps track of where in the enemies list this is
    private int previousPathCoordinateID =0; //keeps track of the last position from map.path where this enemy was
    int damageToPlayer;
    public Enemy(int spawnID, Texture texture, Coordinate position, int health, int armor, int magicResistance, float movementSpeed, int damageToPlayer) {
        super(texture,position,movementSpeed, health, armor, magicResistance);
        this.spawnID=spawnID;
        this.damageToPlayer=damageToPlayer;
    }

    //TODO
    @Override
    public void attack(){
        //attack a summon or hero
    }

    /**
    * move enemy towards the next coordinate on the path by movementSpeed amount
     * and check for if it reached the end of the path
    */
    public int update(Path path){
        if(atCoordinate(path.getCoordinate(path.length()-1))){ //if reached the end of path
            return damageToPlayer;
        }
        Coordinate goal=path.getCoordinate(previousPathCoordinateID +1); //where the enemy will want to go next
        move(goal);
        if(atCoordinate(goal)){ //if reached goal, set a new goal
            previousPathCoordinateID++;
            //System.out.println("Enemy "+spawnID+" new goal: "+goal);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "spawnID=" + spawnID +
                ", position=" + position +
                ", health=" + getCurrentHp() +
                '}';
    }

    public int getSpawnID() {
        return spawnID;
    }

}
