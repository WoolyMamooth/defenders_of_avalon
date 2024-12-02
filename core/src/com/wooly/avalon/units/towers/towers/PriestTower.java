package com.wooly.avalon.units.towers.towers;

import static com.wooly.avalon.TDGame.fetchTexture;

import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.towers.AoETower;
import com.wooly.avalon.units.towers.TowerUpgrade;

public class PriestTower extends AoETower {
    public PriestTower(Coordinate position, int towerSpawnID) {
        super(fetchTexture("towers/towerTextures/priest"),
                position, towerSpawnID, 150f, "priest_light", 8, 0.8f,
                new TowerUpgrade[]{
                        new TowerUpgrade("range", 5, 15, 30, 1.25f),
                        new TowerUpgrade("damage",3,5,30,1.5f)
                },
                "pure", 70);
        setName("priest");
        setDescription("Priest channel the power of light to deal\npure damage to enemies in a large area.");
    }
}
