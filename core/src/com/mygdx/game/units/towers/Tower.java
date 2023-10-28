package com.mygdx.game.units.towers;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.DrawableUnit;

public class Tower extends DrawableUnit {
    int towerSpawnID; //keeps track of the order towers were built in
    int damage;
    int cost;
    int upgradeCost;

    public Tower(Texture texture, Coordinate position,int towerSpawnID, int damage, int cost, int upgradeCost) {
        super(texture, position);
        this.towerSpawnID=towerSpawnID;
        this.damage = damage;
        this.cost = cost;
        this.upgradeCost = upgradeCost;
    }
}