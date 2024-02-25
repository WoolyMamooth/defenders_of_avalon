package com.wooly.avalon.units.towers.towers;

import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.TDMap;
import com.wooly.avalon.units.enemies.Enemy;
import com.wooly.avalon.units.towers.RangedTower;
import com.wooly.avalon.units.towers.TowerUpgrade;

import java.util.List;

public class FarmTower extends RangedTower {
    public FarmTower(Coordinate position,int towerSpawnID) {
        super(TDGame.fetchTexture("towers/towerTextures/farm"), position, towerSpawnID, 64, "none",10,20,
                new TowerUpgrade[]{
                    new TowerUpgrade("damage",10,5,25,1f)
                },"physical");
        setName("farm");
        setDescription("Doesn't attack, instead makes you money.\nGives "+damage+" gold every "+attackDelay+" seconds.");
    }

    @Override
    public void attack() {
        TDMap.givePlayerGold(damage);
    }

    @Override
    public void update(List<Enemy> enemies, float timeSinceLastFrame) {
        timeSinceLastAttack+=timeSinceLastFrame;
        if(timeSinceLastAttack>=attackDelay){
            attack();
            timeSinceLastAttack=0;
        }
    }
}
