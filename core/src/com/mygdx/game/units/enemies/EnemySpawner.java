package com.mygdx.game.units.enemies;

import static com.mygdx.game.TDGame.TEXTURE_EXTENSION;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.Coordinate;

public class EnemySpawner {
    Coordinate spawnLocation;
    public EnemySpawner(Coordinate spawnLocation) {
        this.spawnLocation=spawnLocation;
    }
    public Enemy spawnEnemy(int spawnID,String name){ // add new enemies here
        int health=100,armor=0,magicResistance=0,damageToPlayer=10;
        float movementSpeed=50f;
        Texture texture = TDGame.fetchTexture("enemies/"+name);

        //set stats here
        switch(name){
            case "goblin":
                health=30;armor=0;magicResistance=0;damageToPlayer=1;movementSpeed=75f;
                break;
            case "ogre":
                health=80;armor=30;magicResistance=30;damageToPlayer=5;movementSpeed=50f;
                break;
            case "test":
            default:
                break;
        }
        return new Enemy(spawnID,texture,spawnLocation,health,armor,magicResistance,movementSpeed,damageToPlayer);
    }
}
