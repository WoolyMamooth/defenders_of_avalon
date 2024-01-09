package com.wooly.avalon.units.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.Spawner;
import com.wooly.avalon.units.enemies.Enemy;

public class ProjectileSpawner extends Spawner {
    public ProjectileSpawner(Coordinate spawnLocation) {
        super(spawnLocation);
    }
    public Projectile spawnProjectile(String name, Enemy target, int damage,String damageType){
        float speed=200f;
        Texture texture = TDGame.fetchTexture("towers/projectiles/"+name);

        return new Projectile(texture,spawnLocation,speed,damage,damageType,target);
    }
}
