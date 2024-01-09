package com.wooly.avalon.units;

import com.wooly.avalon.maps.Coordinate;

public abstract class Spawner {
    public Coordinate spawnLocation;

    public Spawner(Coordinate spawnLocation) {
        this.spawnLocation = spawnLocation;
    }
}
