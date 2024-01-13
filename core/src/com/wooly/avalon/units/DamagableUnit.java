package com.wooly.avalon.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class DamagableUnit extends MovableUnit{
    public static final float HPBAR_HEIGHT=5; // height of the HP bar above the unit
    public static final int MAX_ARMOR=20; //1 armor = 5% physical damage reduction
    public static final int MAX_MAGIC_RESISTANCE=20;
    protected int currentHp;
    protected int maxHp; // amount of damage that can be taken before dying
    protected int armor; //reduces physical damage taken
    protected int magicResistance; //reduces magical damage taken
    protected boolean damageImmune=false;
    public int healingAmount=0; //amount of health that will be recovered per tick
    protected float timeSinceLastHeal=0f;
    protected static float HEALING_TICK_INTERVAL=1f;
    HPBar hpBar;
    List<UnitBuff> buffs;
    /**
     * Extends on Movable units with health and resistances.
     * Anything that can be hit should inherit from this.
     * @param texture
     * @param position
     * @param movementSpeed
     * @param maxHp
     * @param armor
     * @param magicResistance
     * @param isAlly
     */
    public DamagableUnit(Texture texture, Coordinate position, float movementSpeed, int maxHp, int armor, int magicResistance,boolean isAlly) {
        super(texture, position, movementSpeed);
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.armor = armor;
        this.magicResistance = magicResistance;
        if(this.armor>MAX_ARMOR) this.armor=MAX_ARMOR; //100% damage resistance would cause issues
        if(this.magicResistance>MAX_MAGIC_RESISTANCE) this.magicResistance=MAX_MAGIC_RESISTANCE;

        Color hpBarColor;
        if(isAlly) hpBarColor=Color.CYAN;
        else hpBarColor=Color.RED;
        this.hpBar=new HPBar(position,height,width,hpBarColor);

        buffs=new ArrayList<>();
    }

    /**
     * By default makes non Ally units.
     * @param texture
     * @param position
     * @param movementSpeed
     * @param maxHp
     * @param armor
     * @param magicResistance
     */
    public DamagableUnit(Texture texture, Coordinate position, float movementSpeed, int maxHp, int armor, int magicResistance) {
        this(texture,position,movementSpeed,maxHp,armor,magicResistance,false,0);
    }

    /**
     * You can specify a base healing amount that the unit will heal per sec
     * @param texture
     * @param position
     * @param movementSpeed
     * @param maxHp
     * @param armor
     * @param magicResistance
     * @param isAlly
     * @param baseHealing
     */
    public DamagableUnit(Texture texture, Coordinate position, float movementSpeed, int maxHp, int armor, int magicResistance,boolean isAlly, int baseHealing) {
        this(texture,position,movementSpeed,maxHp,armor,magicResistance,isAlly);
        healingAmount+=baseHealing;
    }
    /**
     * The unit takes damage. Incoming damage is reduced by resistances in the following way:
     * - every point of armor the unit has reduces physical damage taken by 5%
     * - every point of magicResistance the unit has reduces magical damage taken by 5%
     * - pure damage ignores all resistances
     * - if the units damageImmune parameter is true it takes no damage
     * @param damage
     * @param damageType
     */
    public void takeDamage(int damage,String damageType) {
        if(!damageImmune) {
            switch (damageType) {
                case "ph":
                case "phy":
                case "physical":
                    currentHp -= damage * (1 - (armor / 20f));
                    break;
                case "m":
                case "magic":
                case "magical":
                    currentHp -= damage * (1 - magicResistance / 20f);
                    break;
                case "pure":
                default:
                    this.currentHp -= damage;
            }
        }
    }
    /**
     * The unit takes pure damage.
     * @param damage
     */
    public void takeDamage(int damage){
        takeDamage(damage,"pure");
    }
    protected boolean inRange(DamagableUnit other,float range){
        return position.distanceFrom(other.position)<=range;
    }

    /**
     * Updates the timers of the buffs.
     * @param timeSinceLastFrame
     */
    public void updateBuffs(float timeSinceLastFrame){
        //update the buffs and remove the ones that have expired
        List<UnitBuff> shouldBeRemoved=new ArrayList<>();
        for (UnitBuff buff:buffs) {
            if (buff.update(timeSinceLastFrame)){
                shouldBeRemoved.add(buff);
            }
        }
        for (UnitBuff buff:shouldBeRemoved) {
            removeBuff(buff);
        }

        //handle healing the unit if applicable
        if(healingAmount>0){
            timeSinceLastHeal+=timeSinceLastFrame;
            if (timeSinceLastHeal>=HEALING_TICK_INTERVAL){
                heal(healingAmount);
                timeSinceLastHeal=0;
            }
        }
    }
    /**
     * Heals the unit by the given amount up to its maxHp;
     * @param amount
     */
    public void heal(int amount){
        currentHp+=amount;
        if (currentHp>maxHp) currentHp=maxHp;
    }
    public void addBuff(UnitBuff buff){
        this.buffs.add(buff);
        applyBuff(buff,false);
    }
    public void removeBuff(UnitBuff buff){
        applyBuff(buff,true);
        this.buffs.remove(buff);
    }

    /**
     * Applies the stat modifier of the buff to the stats of the unit.
     * All possible buff must be defined here or in an override.
     * @param buff
     * @param removeMode if true the modifier will instead be subtracted.
     */
    protected void applyBuff(UnitBuff buff,boolean removeMode){
        float modifier=buff.getModifier();
        if(removeMode) modifier*=-1;
        switch (buff.stat){
            case "armor":
                armor+=modifier;
                if(armor>MAX_ARMOR) armor=MAX_ARMOR; //100% damage resistance would cause issues
                break;
            case "magicResistance":
                magicResistance+=modifier;
                if(magicResistance>MAX_MAGIC_RESISTANCE) magicResistance=MAX_MAGIC_RESISTANCE;
                break;
            case "movementSpeed":
                movementSpeed+=modifier;
                break;
            case "damageImmunity":
                damageImmune=!removeMode;
                break;
            case "healing":
                healingAmount+=modifier;
                break;
            default:
                System.out.println("WARNING no buff for stat "+buff.stat);
        }
    }
    /**
     * triggered when health reaches zero
     */
    public void die(){
        // die, duh
        //TODO might need some more work
        movementSpeed=0;
        hpBar.dispose();
        dispose();
    }
    public int getCurrentHp() {
        return currentHp;
    }
    public int getArmor() {
        return armor;
    }
    public int getMagicResistance() {
        return magicResistance;
    }
    @Override
    public void draw(SpriteBatch batch){
        if(currentHp<maxHp) {
            hpBar.update(position, maxHp, currentHp);
            hpBar.draw(batch);
        }
        super.draw(batch);
    }
    /**
     * @return true if HP is zero or less.
     */
    public boolean isDead(){
        return currentHp<=0;
    }
    private class HPBar extends DrawableUnit{
        Coordinate offset; // the amount by which the hpbar is drawn above the parent unit
        Color color;
        float currentWidth; // current status, should equal the hp percentage of the unit

        /**
         * Creates a bar floating above the units head, 100% HP will be represented by a white bar
         * while the current HP will be red.
         * @param parentPosition position of the unit this is attached to
         * @param parentHeight height of parent units texture
         * @param parentWidth width of parent units texture
         */
        public HPBar(Coordinate parentPosition, float parentHeight,float parentWidth,Color color){
            super(parentPosition);
            this.texture=TDGame.fetchTexture("white_square");
            this.width=parentWidth;
            this.currentWidth=parentWidth;
            this.height=HPBAR_HEIGHT;
            this.offset=new Coordinate(0,parentHeight+HPBAR_HEIGHT);
            this.position=position.add(offset);
            this.color=color;
        }
        public HPBar(Coordinate parentPosition, float parentHeight,float parentWidth){
            this(parentPosition,parentHeight,parentWidth,Color.RED);
        }
        public void update(Coordinate position, float maxHp, float currentHp){
            if(currentHp<0) currentHp=0;
            float hpPercent=currentHp/maxHp;
            this.currentWidth=width*hpPercent;
            this.position=position.add(offset);
        }
        @Override
        public void draw(SpriteBatch batch){
            batch.draw(texture, position.x(), position.y(), width, height); //max hp in white
            batch.setColor(color);
            batch.draw(texture, position.x(), position.y(), currentWidth, height); //current hp in red
            batch.setColor(Color.WHITE);
        }
        @Override
        public void dispose() {
            super.dispose();
            offset=null;
        }
    }
}
