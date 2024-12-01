package com.wooly.avalon.units.towers.towers;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.towers.SummonerTower;
import com.wooly.avalon.units.towers.TowerUpgrade;

public class BarracksTower extends SummonerTower {
    public BarracksTower(Coordinate position, int towerSpawnID) {
        super(TDGame.fetchTexture("towers/towerTextures/barracks"),
                position, towerSpawnID, 200f,"guard",5f,1,
                new TowerUpgrade[]{
                    new TowerUpgrade("summons",3,1,100,1.1f),
                    new TowerUpgrade("armor",3,1,25,1.5f)
                }
            );
        setName("barracks");
        setDescription("Summons a "+summonName+" every "+getAttackDelay()+" seconds,\nwhich attacks the enemies.\n");
        //TODO add summons stats into description maybe
    }
}
