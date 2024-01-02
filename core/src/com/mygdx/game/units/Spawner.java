package com.mygdx.game.units;

import com.mygdx.game.maps.Coordinate;

public abstract class Spawner {
    public Coordinate spawnLocation;

    public Spawner(Coordinate spawnLocation) {
        this.spawnLocation = spawnLocation;
    }
}
