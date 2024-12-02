package com.wooly.avalon.units.towers.towers;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.towers.RangedTower;
import com.wooly.avalon.units.towers.TowerUpgrade;

public class ArcherTower extends RangedTower {
    public ArcherTower(Coordinate position, int towerSpawnID) {
        super(TDGame.fetchTexture("towers/towerTextures/archer"),
                position, towerSpawnID,200f, "arrow", 5, 0.5f,
                new TowerUpgrade[]{
                        new TowerUpgrade("range", 3, 20, 10, 1.25f),
                        new TowerUpgrade("atkSpeed", 5, 5, 20, 1.5f),
                        new TowerUpgrade("damage", 5, 2, 5, 2f)
                },
                "physical");
        setName("archer");
        setDescription("Basic tower that shoots arrows relatively fast.\nDamage: "+damage+" "+damageType);
    }
}
