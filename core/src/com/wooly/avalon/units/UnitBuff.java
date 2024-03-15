package com.wooly.avalon.units;

public class UnitBuff {
    public String stat;
    private float modifier;
    private float duration; //amount of time this should be applied for
    private float timeSinceActivation=0; //time that has passed since activation

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
}
