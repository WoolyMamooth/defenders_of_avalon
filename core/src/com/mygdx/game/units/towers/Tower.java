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
    int cost;
    int upgradeCost;

    public Tower(Texture texture, Coordinate position,int towerSpawnID,String projectileName, int damage, int cost, int upgradeCost,float attackDelay) {
        super(texture, position);
        this.towerSpawnID=towerSpawnID;
        this.projectileName=projectileName;
        this.damage = damage;
        this.cost = cost;
        this.upgradeCost = upgradeCost;
        this.projectileSpawner=new ProjectileSpawner(new Coordinate(position.x()+this.getWidth()/2, position.y()+this.getHeight()/2));
        this.range=200;
        this.attackDelay=attackDelay;
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

    public int getCost() {
        return cost;
    }

    public int getUpgradeCost() {
        return upgradeCost;
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