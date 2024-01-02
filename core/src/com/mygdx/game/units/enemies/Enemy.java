package com.mygdx.game.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.maps.Path;
import com.mygdx.game.units.Attacker;
import com.mygdx.game.units.DamagableUnit;
import com.mygdx.game.units.towers.AlliedUnit;

public class Enemy extends DamagableUnit implements Attacker {
    int spawnID; // keeps track of where in the enemies list this is
    private int previousPathCoordinateID =1; //keeps track of the last position from map.path where this enemy was
    int damageToPlayer;
    AlliedUnit target;
    float timeSinceLastAttack=0;
    float attackDelay=2f; //defines how much time should pass between attacks
    protected boolean inCombat=false;
    public Enemy(int spawnID, Texture texture, Coordinate position, int health, int armor, int magicResistance, float movementSpeed, int damageToPlayer) {
        super(texture,position,movementSpeed, health, armor, magicResistance);
        this.spawnID=spawnID;
        this.damageToPlayer=damageToPlayer;
    }

    //TODO
    @Override
    public void attack(){
        //attack a summon or hero
        target.takeDamage(10);
        if(target.getCurrentHp()<=0){
            target=null;
            inCombat=false;
        }
    }
    protected void tryAttack(float timeSinceLastFrame){
        timeSinceLastAttack += timeSinceLastFrame;
        if (timeSinceLastAttack >= attackDelay) {
            attack();
            timeSinceLastAttack = 0;
        }
    }
    /**
    * Moves enemy towards the next coordinate on the path by movementSpeed amount
    * and check for if it reached the end of the path. Returns the damage dealt to the player if the end of path is reached, 0 otherwise;
    */
    public int update(Path path,float timeSinceLastFrame){
        if (atCoordinate(path.getCoordinate(path.length() - 1))) { //if reached the end of path
            return damageToPlayer;
        }

        if(!inCombat) {
            Coordinate goal = path.getCoordinate(previousPathCoordinateID); //where the enemy will want to go next
            move(goal);
            if (atCoordinate(goal)) { //if reached goal, set a new goal
                previousPathCoordinateID++;
                //System.out.println("Enemy "+spawnID+" new goal: "+goal);
            }
            timeSinceLastAttack=0;
        }else {
            tryAttack(timeSinceLastFrame);
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
    public void setTarget(AlliedUnit target) {
        this.target = target;
    }

    public boolean isInCombat() {
        return inCombat;
    }

    public void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
    }
}
