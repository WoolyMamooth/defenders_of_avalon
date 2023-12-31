package com.mygdx.game.units.towers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.DrawableUnit;
import com.mygdx.game.units.enemies.Enemy;
import com.mygdx.game.units.projectiles.Projectile;
import com.mygdx.game.units.projectiles.ProjectileSpawner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tower extends DrawableUnit {
    ProjectileSpawner projectileSpawner;
    List<Projectile> projectiles=new ArrayList<>();
    String projectileName;
    Enemy target=null;
    float timeSinceLastAttack=0;
    int towerSpawnID; //keeps track of the order towers were built in
    float attackDelay; //defines how much time should pass between attacks
    float range; //defines how far the tower will target
    int damage;
    public TowerUpgrade[] upgrades;

    public Tower(Texture texture, Coordinate position,int towerSpawnID,String projectileName, int damage,float attackDelay,TowerUpgrade[] upgrades) {
        super(texture, position);
        this.towerSpawnID=towerSpawnID;
        this.projectileName=projectileName;
        this.damage = damage;
        this.projectileSpawner=new ProjectileSpawner(new Coordinate(position.x()+this.getWidth()/2f, position.y()+this.getHeight()/2f));
        this.range=200;
        this.attackDelay=attackDelay;
        this.upgrades=upgrades;
    }
    /**
     * Sends a projectile to the target Enemy
     */
    public void attack(){
        System.out.println(this + " attacked "+ target);
        projectiles.add(projectileSpawner.spawnProjectile(projectileName,target,damage));
    }
    public void update(List<Enemy> enemies,float timeSinceLastFrame){
        updateExistingProjectiles();

        //attack if possible
        timeSinceLastAttack+=timeSinceLastFrame;
        if(timeSinceLastAttack>=attackDelay){
            //update target
            target=getTarget(enemies);
            if(target==null) return; //don't attack if there isn't anyone in range
            else attack();
            timeSinceLastAttack=0;
        }
    }
    private void updateExistingProjectiles(){
        if(projectiles==null) return;
        List<Projectile> shouldBeDeleted=new ArrayList<>();

        for (Projectile projectile:projectiles) {
            //if it returns true we should delete the projectile
            if(projectile.update()){ //moves the projectiles towards the target
                shouldBeDeleted.add(projectile);
            }
        }
        //delete the ones that hit
        for (Projectile projectile:shouldBeDeleted) {
            projectiles.remove(projectile);
        }
    }
    private Enemy getTarget(List<Enemy> enemies){
        if(enemies.isEmpty()) return null;
        for (Enemy enemy:enemies) {
            if(position.distanceFrom(enemy.getPosition()) <= range){
                return enemy;
            }
        }
        return null;
    }
    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        if(projectiles==null) return;
        for (Projectile projectile:projectiles) {
            projectile.draw(batch);
        }
    }

    /**
     * Increases the level of the specified stat by one.
     */
    public void upgrade(String stat){
        for(TowerUpgrade u:this.upgrades){
            if(Objects.equals(u.stat, stat)){
                u.levelUp();
                applyUpgrade(u);
            }
        }
    }

    /**
     * Returns the cost of the upgrade if it can be upgraded, 0 otherwise.
     * @param stat
     * @return
     */
    public int costOfUpgrade(String stat){
        //find the upgrade we need to level up
        for(TowerUpgrade u:this.upgrades){
            if(Objects.equals(u.stat, stat)){
                if(!u.isMaxed()){ //if it wasn't maxed yet
                    return u.getCost();
                }
                break;
            }
        }
        return 0;
    }

    /**
     * This increases the stat of the tower, all possible upgrades have to be defined here!
     */
    private void applyUpgrade(TowerUpgrade u){
        switch (u.stat){
            case "range":
                this.range+=u.getIncrease();
                break;
            case "damage":
                this.damage+=u.getIncrease();
                break;
            case "atkSpeed":
                //10 attackspeed = -0.1f delay between attacks
                this.attackDelay-=u.getIncrease()/100f;
                break;

            default:
                System.out.println("Upgrade "+u.stat+" doesn't exist for "+toString());
                break;
        }
    }
    public int getTowerSpawnID() {
        return towerSpawnID;
    }
    public float getAttackDelay() {
        return attackDelay;
    }
    public float getRange() {
        return range;
    }
    public int getDamage() {
        return damage;
    }
    @Override
    public String toString() {
        return "Tower{" +
                "towerSpawnID=" + towerSpawnID +
                ", attackDelay=" + attackDelay +
                ", range=" + range +
                ", damage=" + damage +
                ", position=" + position +
                '}';
    }
}