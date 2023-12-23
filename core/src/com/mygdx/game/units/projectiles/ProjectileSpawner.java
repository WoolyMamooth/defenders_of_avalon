package com.mygdx.game.units.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.enemies.Enemy;

public class ProjectileSpawner {
    Coordinate spawnLocation;
    public ProjectileSpawner(Coordinate spawnLocation) {
        this.spawnLocation=spawnLocation;
    }
    public Projectile spawnProjectile(String name,Enemy target, int damage){
        float speed=200f;
        Texture texture = TDGame.fetchTexture("towers/projectiles/"+name);

        return new Projectile(texture,spawnLocation,speed,damage,target);
    }
}
