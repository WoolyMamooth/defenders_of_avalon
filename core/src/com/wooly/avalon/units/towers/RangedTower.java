package com.wooly.avalon.units.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.enemies.Enemy;
import com.wooly.avalon.units.projectiles.Projectile;
import com.wooly.avalon.units.projectiles.ProjectileSpawner;

import java.util.ArrayList;
import java.util.List;

public abstract class RangedTower extends Tower{
    ProjectileSpawner projectileSpawner;
    List<Projectile> projectiles=new ArrayList<>();
    String projectileName;
    Enemy target=null;
    protected int damage;
    protected String damageType;
    public RangedTower(Texture texture, Coordinate position, int towerSpawnID,float range, String projectileName, int damage, float attackDelay, TowerUpgrade[] upgrades, String damageType) {
        super(texture, position, towerSpawnID, attackDelay,range, upgrades);
        this.projectileName=projectileName;
        this.damage = damage;
        this.damageType=damageType;
        this.projectileSpawner=new ProjectileSpawner(textureCenterPosition());
    }
    /**
     * Sends a projectile to the target Enemy.
     */
    @Override
    public void attack(){
        //System.out.println(this + " attacked "+ target);
        projectiles.add(projectileSpawner.spawnProjectile(projectileName,target,damage,damageType));
    }
    public void update(List<Enemy> enemies,float timeSinceLastFrame){
        super.update(enemies,timeSinceLastFrame);
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
    protected void updateExistingProjectiles(){
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
    protected Enemy getTarget(List<Enemy> enemies) {
        if(enemies.isEmpty()) return null;
        for (Enemy enemy:enemies) {
            if(textureCenterPosition().distanceFrom(enemy.textureCenterPosition()) <= range){
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
    @Override
    protected void applyUpgrade(TowerUpgrade u){
        switch (u.stat){
            case "damage":
            case "gold given":
                this.damage+=u.getIncrease();
                break;
            default:
                super.applyUpgrade(u);
                break;
        }
    }
}
