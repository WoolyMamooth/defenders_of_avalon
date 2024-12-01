package com.wooly.avalon.units.towers.towers;

import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.towers.SummonerTower;
import com.wooly.avalon.units.towers.TowerUpgrade;

public class PaladinsTower extends SummonerTower {
    public PaladinsTower(Coordinate position, int towerSpawnID) {
        super(TDGame.fetchTexture("towers/towerTextures/paladins"),
                position, towerSpawnID, 170f, "paladin", 7, 1,
                new TowerUpgrade[]{
                        new TowerUpgrade("summons",3,1,100,1.1f),
                        new TowerUpgrade("healing",3,1,25,1.5f),
                        new TowerUpgrade("HP",3,25,30,1.25f)
                });
        setName("paladins");
        setDescription("Summons paladins, holy warriors\nwho use the power of light to heal\ntheir wounds and deal pure damage\nto their enemies.");
    }
}
