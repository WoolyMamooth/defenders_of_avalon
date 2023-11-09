package com.mygdx.game.units.towers;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.DrawableUnit;
import com.mygdx.game.units.projectiles.Projectile;
import com.mygdx.game.units.projectiles.ProjectileSpawner;

import java.util.ArrayList;
import java.util.List;

public class Tower extends DrawableUnit {
    ProjectileSpawner projectileSpawner;
    List<Projectile> projectiles;
    String projectileName;

    int towerSpawnID; //keeps track of the order towers were built in
    int damage;
    int cost;
    int upgradeCost;

    public Tower(Texture texture, Coordinate position,int towerSpawnID, int damage, int cost, int upgradeCost) {
        super(texture, position);
        this.towerSpawnID=towerSpawnID;
        this.damage = damage;
        this.cost = cost;
        this.upgradeCost = upgradeCost;
        this.projectileSpawner=new ProjectileSpawner(position.subtract(position.multiplyByScalar(0.5f)));
    }
    public void attack(){
        projectiles.add(projectileSpawner.spawnProjectile(projectileName));
    }
    public void updateProjectiles(){
        List<Projectile> shouldBeDeleted=new ArrayList<>();

        for (Projectile projectile:projectiles) {
            //if it returns true we should delete the projectile
            if(projectile.update(new Coordinate(0f,0f))){ //FIXME moves the projectiles, but we still need to find a target
                shouldBeDeleted.add(projectile);
            }
        }
        //delete the ones that hit
        for (Projectile projectile:shouldBeDeleted) {
            projectiles.remove(projectile);
        }
    }
}