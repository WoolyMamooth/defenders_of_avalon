package com.wooly.avalon.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.maps.Coordinate;

public class UnitBuff {
    public String stat;
    private float modifier;
    private float duration; //amount of time this should be applied for
    private float timeSinceActivation=0; //time that has passed since activation
    private Texture indicator;

    /**
     * Provides a way to temporarily increase or decrease the stats of units.
     * TODO add visual indicators
     * @param stat what stat should be affected
     * @param modifier how much should it be changed
     * @param duration for how long
     */
    public UnitBuff(String stat, float modifier, float duration) {
        this.stat = stat;
        this.modifier = modifier;
        this.duration = duration;
    }
    public UnitBuff(String stat, float modifier, float duration, Texture indicatorTexture){
        this(stat,modifier,duration);
        this.indicator=indicatorTexture;
    }

    /**
     * Increases the internal timer, returns true if the buff should be removed.
     * @param timeSinceLastFrame
     * @return
     */
    public boolean update(float timeSinceLastFrame){
        timeSinceActivation+=timeSinceLastFrame;
        return timeSinceActivation >= duration;
    }
    public float getModifier(){
        return this.modifier;
    }
    public void draw(SpriteBatch batch, Coordinate coordinate,float width, float height){
        if(this.indicator != null){
            batch.draw(indicator,coordinate.x(),coordinate.y(),width,height);
        }
    }
}
