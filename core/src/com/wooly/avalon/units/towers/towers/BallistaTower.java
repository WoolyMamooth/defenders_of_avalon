package com.wooly.avalon.units.towers.towers;

import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.towers.RangedTower;
import com.wooly.avalon.units.towers.TowerUpgrade;

public class BallistaTower extends RangedTower {
    public BallistaTower(Coordinate position, int towerSpawnID) {
        super(TDGame.fetchTexture("towers/towerTextures/ballista"),
                position, towerSpawnID, 700, "ballista_arrow", 100, 10,
                new TowerUpgrade[]{
                        new TowerUpgrade("atkSpeed",3,100,100,1.5f)
                }, "physical");
        setName("ballista");
        setDescription("Extremely high range, very high damage, shoots slowly.");
    }
}
