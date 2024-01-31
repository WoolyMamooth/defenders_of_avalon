package com.wooly.avalon.units.towers.towers;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.towers.SummonerTower;
import com.wooly.avalon.units.towers.TowerUpgrade;

public class BarracksTower extends SummonerTower {
    public BarracksTower(Texture texture, Coordinate position, int towerSpawnID) {
        super(texture, position, towerSpawnID, "guard",5f,1,
            new TowerUpgrade[]{
                new TowerUpgrade("summons",3,1,100,1.1f),
                new TowerUpgrade("armor",3,1,25,1.5f)
            }
        );
    }
}
