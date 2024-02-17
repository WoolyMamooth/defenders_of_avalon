package com.wooly.avalon.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.MapLoader;
import com.wooly.avalon.units.Spawner;

public class EnemySpawner extends Spawner {
    //difficulty modifiers, the relevant stats will be multiplied by these
    float healthDifModifier=1f,armorDifModifier=1f,magResDifModifier=1f, movespeedDifModifier=1f,damageDifModifier=1f,goldDropModifier=1f;
    public EnemySpawner(Coordinate spawnLocation) {
        super(spawnLocation);
        int difficulty=MapLoader.GAME_DIFFICULTY;
        switch (difficulty){
            case 0: //Easy, we nerf everything
                healthDifModifier=0.75f;
                movespeedDifModifier=0.75f;
                damageDifModifier=0.75f;
                break;
            case 1: //Normal, everything is balanced around this so we change nothing
                break;
            case 2: //Hard, we buff things a little
                healthDifModifier=1.25f;
                damageDifModifier=1.25f;
                break;
            case 3: //Extreme, should be really hard
                healthDifModifier=2f;
                armorDifModifier=1.1f;
                magResDifModifier=1.1f;
                damageDifModifier=1.5f;
                break;
            case 4: //Nightmare, should only be barely possible
                healthDifModifier=2f;
                armorDifModifier=1.3f;
                magResDifModifier=1.3f;
                damageDifModifier=2f;
                goldDropModifier=0.75f;
                movespeedDifModifier=1.5f;
                break;
        }
    }

    /**
     * Spawns an enemy based on the given name. Add all new enemies here.
     * @param spawnID
     * @param name
     * @return
     */
    public Enemy spawnEnemy(int spawnID,String name){
        //base stats so i don't forget to set something at least
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

        return new Enemy(spawnID,texture,spawnLocation,
                (int)(health*healthDifModifier),
                (int)(armor*armorDifModifier),
                (int)(magicResistance*magResDifModifier),
                movementSpeed*movespeedDifModifier,
                damageToPlayer,
                (int) (damage*damageDifModifier),
                damageType,
                (int)(goldDropped*goldDropModifier)
        );
    }
}
