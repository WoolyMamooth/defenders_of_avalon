package com.wooly.avalon.units.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.Spawner;
import com.wooly.avalon.units.enemies.Enemy;

import java.util.ArrayList;
import java.util.List;

public class ProjectileSpawner extends Spawner {
    List<Projectile> projectiles=new ArrayList<>();
    public ProjectileSpawner(Coordinate spawnLocation) {
        super(spawnLocation);
    }
    public void spawnProjectile(String name, Enemy target, int damage, String damageType){
        spawnProjectile(name, target, damage, damageType,200f);
    }
    public void spawnProjectile(String name, Enemy target, int damage, String damageType,float speed){
        Texture texture = TDGame.fetchTexture("towers/projectiles/"+name);
        projectiles.add(new Projectile(texture,spawnLocation,speed,damage,damageType,target));
    }

    /**
     * Updates projectiles.
     */
    public void update(){
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

    /**
     * Updates projectiles that have AoE damage.
     * @param enemies
     * @param aoeRange
     */
    public void update(List<Enemy> enemies,float aoeRange){
        if(projectiles==null) return;
        List<Projectile> shouldBeDeleted=new ArrayList<>();

        for (Projectile projectile:projectiles) {
            //if it returns true we should delete the projectile
            if(projectile.update(enemies,aoeRange)){ //moves the projectiles towards the target
                shouldBeDeleted.add(projectile);
            }
        }
        //delete the ones that hit
        for (Projectile projectile:shouldBeDeleted) {
            projectiles.remove(projectile);
        }
    }
    public void draw(SpriteBatch batch){
        for (Projectile projectile:projectiles) {
            projectile.draw(batch);
        }
    }
}
