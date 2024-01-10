package com.wooly.avalon.units.towers;

import static com.wooly.avalon.TDGame.fetchTexture;
import static com.wooly.avalon.TDGame.random;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.Spawner;

public class SummonSpawner extends Spawner {
    private float searchRange;
    public int maxHpUpgrade=0;
    public int armorUpgrade=0;
    public int magicResistanceUpgrade=0;
    public int damageUpgrade=0;
    public int healingUpgrade=0;

    public SummonSpawner(Coordinate spawnLocation, float searchRange) {
        super(spawnLocation);
        this.searchRange =searchRange;
    }

    /**
     * Returns a Summon based on the given name.
     */
    public Summon spawnSummon(String summonName){
        return spawnSummon(summonName,spawnLocation);
    }

    public Summon spawnSummon(String summonName, Coordinate position) {
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
        float randomOffsetMax= searchRange/3f;
        float x=random.nextFloat()*(randomOffsetMax*2)-randomOffsetMax;
        float y=random.nextFloat()*(randomOffsetMax*2)-randomOffsetMax;
        /*
        float y=random.nextFloat(-randomOffsetMax,randomOffsetMax);
        Apparently the line above crashes the game, but only on android.
        A 12 year old StackOverflow post says "nextFloat doesn't take an argument", which I find highly unlikely,
        since it runs fine on desktop, but changing it did fix the problem so whatever.
        https://stackoverflow.com/questions/6078157/random-nextfloat-is-not-applicable-for-floats
        */

        Summon summon= new Summon(texture,position,new Coordinate(x,y),movementSpeed,maxHp,armor,magicResistance,damage,attackDelay, searchRange,damageType);
        summon.healingAmount+=healingUpgrade;
        return summon;
    }
}
