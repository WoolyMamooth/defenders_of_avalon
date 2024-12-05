package com.wooly.avalon.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.MapLoader;
import com.wooly.avalon.maps.TDMap;
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
     * @param map a reference to the map, needed for units that summon other units
     * @return
     */
    public Enemy spawnEnemy(int spawnID, String name, TDMap map){
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

            case "skeleton":
                health=10;damageToPlayer=1;movementSpeed=50f;damage=10;goldDropped=1;
                break;
            case "giant_skeleton":
                health=70;armor=2;magicResistance=2;damageToPlayer=3;movementSpeed=30f;damage=15;goldDropped=15;
                break;
            case "necromancer": //miniboss
                return new Necromancer(spawnID,texture,spawnLocation,
                        (int)(40*healthDifModifier),
                        (int)(0*armorDifModifier),
                        (int)(10*magResDifModifier),
                        25f*movespeedDifModifier,
                        3,
                        (int) (10*damageDifModifier),
                        "magic",
                        (int)(30*goldDropModifier),
                        map
                );
            case "arch_lich": //boss
                return new ArchLich(spawnID,texture,spawnLocation,
                        (int)(3000*healthDifModifier),
                        (int)(0*armorDifModifier),
                        (int)(15*magResDifModifier),
                        10f*movespeedDifModifier,
                        1000,
                        (int) (20*damageDifModifier),
                        "magic",
                        (int)(200*goldDropModifier),
                        map
                );
            case "skeleton_dragon":
                return new Dragon(spawnID,texture,spawnLocation,
                        (int)(80*healthDifModifier),
                        (int)(5*armorDifModifier),
                        (int)(5*magResDifModifier),
                        30f*movespeedDifModifier,
                        1000,
                        (int) (30*damageDifModifier),
                        "physical",
                        (int)(75*goldDropModifier)
                );
            case "red_dragon":
            case "dragon": //boss
                return new Dragon(spawnID,texture,spawnLocation,
                        (int)(200*healthDifModifier),
                        (int)(10*armorDifModifier),
                        (int)(10*magResDifModifier),
                        10f*movespeedDifModifier,
                        1000,
                        (int) (30*damageDifModifier),
                        "physical",
                        (int)(100*goldDropModifier)
                );
            case "demon":
                health=30;armor=0;magicResistance=10;damageToPlayer=1;movementSpeed=60f;damage=25;goldDropped=5;
                break;
            case "arch_demon": //boss
                return new ArchDemon(spawnID,texture,spawnLocation,
                        (int)(3000*healthDifModifier),
                        (int)(3*armorDifModifier),
                        (int)(10*magResDifModifier),
                        10f*movespeedDifModifier,
                        1000,
                        (int) (30*damageDifModifier),
                        "magic",
                        (int)(100*goldDropModifier),
                        map
                );
            case "summon_circle":
                return new SummonCircle(spawnID,texture,spawnLocation);
            case "golem":
                return new Golem(spawnID,texture,spawnLocation,
                        (int)(100*healthDifModifier),
                        (int)(10*armorDifModifier),
                        (int)(0*magResDifModifier),
                        10f*movespeedDifModifier,
                        1000,
                        (int) (30*damageDifModifier),
                        "physical",
                        (int)(40*goldDropModifier),
                        map,
                        false
                );
            case "mini_golem":
                return new Golem(spawnID,texture,spawnLocation,
                        (int)(20*healthDifModifier),
                        (int)(10*armorDifModifier),
                        (int)(0*magResDifModifier),
                        20f*movespeedDifModifier,
                        1000,
                        (int) (10*damageDifModifier),
                        "physical",
                        (int)(10*goldDropModifier),
                        map,
                        true
                );

            case "ogre":
                health=70;armor=2;magicResistance=3;damageToPlayer=3;movementSpeed=25f;damage=20;goldDropped=20;
                break;
            case "troll":
                health=150;armor=5;magicResistance=3;damageToPlayer=5;movementSpeed=25f;damage=20;goldDropped=30;
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
