package com.wooly.avalon.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.Path;
import com.wooly.avalon.units.Attacker;
import com.wooly.avalon.units.DamagableUnit;
import com.wooly.avalon.units.AlliedUnit;

public class Enemy extends DamagableUnit implements Attacker {
    int spawnID; // keeps track of where in the enemies list this is
    protected int previousPathCoordinateID =1; //keeps track of the last position from map.path where this enemy was
    private Coordinate pathOffset;
    int damageToPlayer;
    int goldDropped;
    AlliedUnit target;
    float timeSinceLastAttack=0;
    float attackDelay=2f; //defines how much time should pass between attacks
    int damage;
    String damageType;
    float attackRange;
    public Enemy(int spawnID, Texture texture, Coordinate position, int health, int armor, int magicResistance, float movementSpeed, int damageToPlayer, int damage,String damageType,int goldDropped) {
        this(spawnID, texture, position, health, armor, magicResistance, movementSpeed, damageToPlayer, damage, damageType, goldDropped, texture.getWidth());
    }
    public Enemy(int spawnID, Texture texture, Coordinate position, int health, int armor, int magicResistance, float movementSpeed, int damageToPlayer, int damage,String damageType,int goldDropped,float attackRange){
        super(texture,position,movementSpeed, health, armor, magicResistance);
        this.spawnID=spawnID;
        this.damageToPlayer=damageToPlayer;
        this.damage=damage;
        this.damageType=damageType;
        this.attackRange=attackRange/2f;
        this.goldDropped=goldDropped;

        float offsetY= TDGame.CustomRandom.nextFloat(-height/2,height/2);
        pathOffset=new Coordinate(0,offsetY);

        this.position=this.position.add(pathOffset);
    }

    //TODO
    @Override
    public void attack(){
        //attack a summon or hero
        target.takeDamage(damage,damageType);
        if(target.getCurrentHp()<=0){
            target=null;
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
    * and check for if it reached the end of the path. Returns the damage dealt to the player if the end of path is reached, 0 otherwise.
    */
    public int update(Path path,float timeSinceLastFrame){
        if (atCoordinate(path.getCoordinate(path.getLength()-1).add(pathOffset))) { //if reached the end of path
            return damageToPlayer;
        }

        if(target==null) { //if it not is attacking a Summon, move
            Coordinate goal = path.getCoordinate(previousPathCoordinateID).add(pathOffset); //where the enemy will want to go next
            move(goal);
            if (atCoordinate(goal)) { //if reached goal, set a new goal
                previousPathCoordinateID++;
                //System.out.println("Enemy "+spawnID+" new goal: "+goal);
            }
            timeSinceLastAttack=0;
        }else {
            //if in range of target, attack
            if(inRange(target,attackRange)){
                //turn around if needed
                if(!facingLeft && target.textureCenterPosition().x()<textureCenterPosition().x()) turnAround();
                if(facingLeft && target.textureCenterPosition().x()>textureCenterPosition().x()) turnAround();
                tryAttack(timeSinceLastFrame);
            }
            else{
                //otherwise try and move towards target
                move(target.getPosition());
            }
        }
        return 0;
    }
    public void die(){
        if(target!=null) {
            target.setTarget(null);
        }
        super.die();
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "spawnID=" + spawnID +
                ", position=" + position +
                ", health=" + getCurrentHp() +
                '}';
    }

    public void setPathCoordinate(int pathCoordinate) {
        this.previousPathCoordinateID = pathCoordinate;
    }

    public int getSpawnID() {
        return spawnID;
    }
    public void setTarget(AlliedUnit target) {
        this.target = target;
    }
    public boolean inCombat(){
        return !(target==null);
    }
    public int getGoldDropped() {
        return goldDropped;
    }
}
