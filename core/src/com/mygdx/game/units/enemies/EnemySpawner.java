package com.mygdx.game.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;

public class EnemySpawner {
    Coordinate spawnLocation;
    public EnemySpawner(Coordinate spawnLocation) {
        this.spawnLocation=spawnLocation;
    }
    public Enemy spawnEnemy(int spawnID,String name){ // add new enemies here
        switch(name){
            case "test":
            default:
                Texture texture =new Texture("enemies/"+name+".jpg");
                return new Enemy(spawnID,texture,spawnLocation,100,0,0,200f,100);
        }
    }
}
