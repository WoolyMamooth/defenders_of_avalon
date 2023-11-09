package com.mygdx.game.units.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.maps.Path;
import com.mygdx.game.units.DrawableUnit;

public class Enemy extends DrawableUnit {
    int spawnID; // keeps track of where in the enemies list this is
    int health; //amount of damage that can be taken before dying
    int armor; //reduces physical damage taken
    int magicResistance; //reduces magical damage taken
    float movementSpeed; //speed at which position is changed, used in move function
    private int previousPathCoordinateID =0; //keeps track of the last position from map.path where this enemy was

    int damageToPlayer;



    public Enemy(int spawnID, Texture texture, Coordinate position, int health, int armor, int magicResistance, float movementSpeed, int damageToPlayer) {
        super(texture,position);
        this.spawnID=spawnID;
        this.health = health;
        this.armor = armor;
        this.magicResistance = magicResistance;
        this.movementSpeed = movementSpeed;
        this.damageToPlayer=damageToPlayer;
    }

    //TODO
    public void attack(){
        //attack a summon or hero
    }

    // move enemy towards the next coordinate on the path by movementSpeed amount
    public int move(Path path){
        if(atCoordinate(path.getCoordinate(path.length()-1))){ //if reached the end of path
            return damageToPlayer;
        }
        Coordinate goal=path.getCoordinate(previousPathCoordinateID +1); //where the enemy will want to go next
        Coordinate movementDirection=goal.subtract(position).normalize(); //get a unit vector pointing to goal
        position=position.add(movementDirection.multiplyByScalar(movementSpeed*Gdx.graphics.getDeltaTime())); //actually move

        if(atCoordinate(goal)){ //if reached goal, set a new goal
            previousPathCoordinateID++;
            System.out.println("Enemy "+spawnID+" new goal: "+goal);
        }
        return 0;
        //System.out.println(this);
    }

    //triggered when this.health reaches zero
    public void die(){
        // die, duh
    }

    //checks if the enemy has reached a given coordinate
    private boolean atCoordinate(Coordinate coordinate){
        if(
                position.x()>coordinate.x()-texture.getWidth()/2f &&
                position.y()>coordinate.y()-texture.getHeight()/2f &&
                position.x()<coordinate.x()+texture.getWidth()/2f &&
                position.y()<coordinate.y()+texture.getHeight()/2f
        ) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "spawnID=" + spawnID +
                ", position=" + position +
                ", health=" + health +
                '}';
    }

    public int getSpawnID() {
        return spawnID;
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

    public float getMovementSpeed() {
        return movementSpeed;
    }

}
