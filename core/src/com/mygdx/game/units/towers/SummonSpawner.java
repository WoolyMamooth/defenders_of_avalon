package com.mygdx.game.units.towers;

import static com.mygdx.game.TDGame.fetchTexture;
import static com.mygdx.game.TDGame.random;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.Spawner;

public class SummonSpawner extends Spawner {
    private float towersRange;
    public int maxHpUpgrade=0;
    public int armorUpgrade=0;
    public int magicResistanceUpgrade=0;
    public int damageUpgrade=0;

    public SummonSpawner(Coordinate spawnLocation, float searchRange) {
        super(spawnLocation);
        this.towersRange =searchRange;
    }

    /**
     * Returns a Summon based on the given name.
     */
    public Summon spawnSummon(String summonName){
        Texture texture=fetchTexture("towers/summons/"+summonName);
        //TODO add offset to spawn location

        //we apply upgrades like this
        float movementSpeed=0;
        int maxHp=maxHpUpgrade;
        int armor=armorUpgrade;
        int magicResistance=magicResistanceUpgrade;
        int damage=damageUpgrade;
        float attackDelay=1f;
        String damageType="physical";
        //and then add the base stats to them
        switch (summonName){
            case "guard":
                movementSpeed+=50f;
                maxHp+=30;
                armor+=2;
                magicResistance+=0;
                damage+=10;
                attackDelay=0.75f;
                break;
            case "None":
            default:
                return spawnSummon("guard");
        }
        float randomOffsetMax=towersRange/3f;
        float x=random.nextFloat(-randomOffsetMax,randomOffsetMax);
        float y=random.nextFloat(-randomOffsetMax,randomOffsetMax);

        return new Summon(texture,spawnLocation,new Coordinate(x,y),movementSpeed,maxHp,armor,magicResistance,damage,attackDelay, towersRange,damageType);
    }
}
