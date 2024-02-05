package com.wooly.avalon.units.towers.towers;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.towers.RangedTower;
import com.wooly.avalon.units.towers.TowerUpgrade;

public class ArcherTower extends RangedTower {
    public ArcherTower(Texture texture, Coordinate position, int towerSpawnID) {
        super(texture, position, towerSpawnID,200f, "arrow", 10, 0.5f,
                new TowerUpgrade[]{
                        new TowerUpgrade("range", 3, 10, 10, 1.25f),
                        new TowerUpgrade("atkSpeed", 3, 10, 20, 1.5f),
                        new TowerUpgrade("damage", 3, 25, 5, 2f)
                },
                "physical");
        setName("archer");
        setDescription("Basic tower that shoots arrows relatively fast.\nDamage: "+damage+" "+damageType);
    }
}
