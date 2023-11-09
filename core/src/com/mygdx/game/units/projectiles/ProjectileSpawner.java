package com.mygdx.game.units.projectiles;

import static com.mygdx.game.TDGame.TEXTURE_EXTENSION;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.enemies.Enemy;

public class ProjectileSpawner {
    Coordinate spawnLocation;
    public ProjectileSpawner(Coordinate spawnLocation) {
        this.spawnLocation=spawnLocation;
    }
    public Projectile spawnProjectile(String name){
        float speed=50f;
        int damage=0;
        Texture texture =new Texture("enemies/"+name+TEXTURE_EXTENSION);

        //set stats here
        switch(name){
            case "test":
            default:
                break;
        }
        return new Projectile(texture,spawnLocation,speed,damage);
    }
}
