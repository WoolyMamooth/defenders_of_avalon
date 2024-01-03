package com.mygdx.game.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.Spawner;

public class EnemySpawner extends Spawner {
    public EnemySpawner(Coordinate spawnLocation) {
        super(spawnLocation);
    }
    public Enemy spawnEnemy(int spawnID,String name){ // add new enemies here
        int health=100,armor=0,magicResistance=0,damageToPlayer=10,damage=0,goldDropped=0;
        String damageType="ph";
        float movementSpeed=50f;

        Texture texture = TDGame.fetchTexture("enemies/"+name);

        //set stats here
        switch(name){
            case "goblin":
                health=20;armor=0;magicResistance=0;damageToPlayer=1;movementSpeed=50f;damage=10;goldDropped=5;
                break;

            case "skeleton": //TODO
            case "necromancer":
            case "dragon":
            case "giant":
            case "soldier":

            case "ogre":
                health=70;armor=2;magicResistance=3;damageToPlayer=3;movementSpeed=25f;damage=20;goldDropped=20;
                break;
            case "troll":
                health=100;armor=5;magicResistance=3;damageToPlayer=5;movementSpeed=25f;damage=20;goldDropped=30;
                break;
            case "test":
            default:
                break;
        }
        return new Enemy(spawnID,texture,spawnLocation,health,armor,magicResistance,movementSpeed,damageToPlayer,damage,damageType,goldDropped);
    }
}
