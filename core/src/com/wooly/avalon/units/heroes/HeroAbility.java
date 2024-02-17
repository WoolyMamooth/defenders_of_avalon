package com.wooly.avalon.units.heroes;

import com.badlogic.gdx.graphics.Texture;

public abstract class HeroAbility {
    public String name;
    public boolean isPassive;
    public Texture icon;
    protected float maxCooldown;
    protected float cooldown; //keeps track of when the spell can be cast next
    protected String description;
    /**
     * Any ability of a hero, implement them as private classes in the given heroes class.
     */
    public HeroAbility(){
    }
    public HeroAbility(String name,Texture icon) {
        this.name=name;
        this.isPassive = true;
        this.icon=icon;
    }
    public HeroAbility(String name,Texture icon,float cooldown) {
        this(name,icon);
        this.name=name;
        this.isPassive = false;
        this.icon=icon;
        this.maxCooldown=cooldown;
    }

    /**
     * This activates the actual effect of the ability and sets its cooldown to max. Call super.activate() in every non-passive Override!
     */
    public void activate(){
        cooldown=maxCooldown;
    }
    public void updateCooldown(float timeSinceLastFrame){
        if(onCooldown()){
            cooldown= Math.max(cooldown-timeSinceLastFrame,0);
        }
    }
    /**
     * @return true if the ability is still on cooldown, and therefore cannot be cast
     */
    public boolean onCooldown(){
        return (cooldown>0);
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = name+"\n"+description+"\n"+(isPassive?"Passive.":"Cooldown: "+maxCooldown);
    }
}
