package com.mygdx.game.units.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.Spawner;
import com.mygdx.game.units.enemies.Enemy;

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
