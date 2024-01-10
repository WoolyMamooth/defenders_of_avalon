package com.wooly.avalon.units.heroes;

import com.badlogic.gdx.graphics.Texture;

public abstract class HeroAbility {
    public String name;
    public boolean isPassive;
    public Texture icon;
    //TODO add cooldowns, description
    public HeroAbility(String name,boolean isPassive,Texture icon) {
        this.name=name;
        this.isPassive = isPassive;
        this.icon=icon;
    }
    public abstract void activate();
}
