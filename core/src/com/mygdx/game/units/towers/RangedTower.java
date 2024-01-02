package com.mygdx.game.units.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.enemies.Enemy;
import com.mygdx.game.units.projectiles.Projectile;
import com.mygdx.game.units.projectiles.ProjectileSpawner;

import java.util.ArrayList;
import java.util.List;

public class RangedTower extends Tower{
    ProjectileSpawner projectileSpawner;
    List<Projectile> projectiles=new ArrayList<>();
    String projectileName;
    Enemy target=null;
    int damage;
    public RangedTower(Texture texture, Coordinate position, int towerSpawnID, String projectileName, int damage, float attackDelay, TowerUpgrade[] upgrades) {
        super(texture, position, towerSpawnID, attackDelay, upgrades);
        this.projectileName=projectileName;
        this.damage = damage;
        this.projectileSpawner=new ProjectileSpawner(textureCenterPosition());
    }
    /**
     * Sends a projectile to the target Enemy
     */
    @Override
    public void attack(){
        //System.out.println(this + " attacked "+ target);
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
                this.damage+=u.getIncrease();
                break;
            default:
                super.applyUpgrade(u);
                break;
        }
    }
}
