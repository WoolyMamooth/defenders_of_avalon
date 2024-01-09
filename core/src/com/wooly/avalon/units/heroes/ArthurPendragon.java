package com.wooly.avalon.units.heroes;

import static com.wooly.avalon.TDGame.fetchTexture;

import com.wooly.avalon.maps.Coordinate;

public class ArthurPendragon extends Hero{
    /**
     * @param position
     */
    public ArthurPendragon(Coordinate position) {
        super(fetchTexture("heroes/arthur"), position, 100, 100, 10, 10, 10, 1, 200, "physical");
    }
}
