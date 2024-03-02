package com.wooly.avalon.units.towers.towers;

import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.towers.AoETower;
import com.wooly.avalon.units.towers.TowerUpgrade;

public class WizardTower extends AoETower {
    public WizardTower(Coordinate position,int towerSpawnID) {
        super(TDGame.fetchTexture("towers/towerTextures/wizard"),
                position, towerSpawnID, 150f, "wizard_fireball", 20, 1.2f,
                new TowerUpgrade[]{
                    new TowerUpgrade("damage",5,10,40,1.25f),
                    new TowerUpgrade("range", 5, 10, 30, 1.25f)
                }, "magic",50);
        setName("wizard");
        setDescription("Doesn't shoot very fast, but deals a good amount\n of magic damage in a small area.");
    }
}
