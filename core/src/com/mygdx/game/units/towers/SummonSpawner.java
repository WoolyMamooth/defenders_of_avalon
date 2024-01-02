package com.mygdx.game.units.towers;

import static com.mygdx.game.TDGame.fetchTexture;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.Spawner;

public class SummonSpawner extends Spawner {
    private float towersRange;
    public SummonSpawner(Coordinate spawnLocation, float searchRange) {
        super(spawnLocation);
        this.towersRange =searchRange;
    }

    /**
     * TODO
     * @param summonName
     * @return
     */
    public Summon spawnSummon(String summonName){
        Texture texture=fetchTexture("towers/summons/"+summonName);
        Summon summon;
        switch (summonName){
            case "guard":
                //TODO add offset to spawn location
                summon=new Summon(texture,spawnLocation,50f,30,2,0,5,0.75f, towersRange);
                break;
            case "None":
            default:
                summon=new Summon(fetchTexture("red_square"),spawnLocation,10,100,0,0,10,1f, towersRange);
                summon.setWidth(summon.getWidth()/2f);
                break;
        }
        return summon;
    }
}
