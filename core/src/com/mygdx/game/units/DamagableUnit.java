package com.mygdx.game.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.Coordinate;

public class DamagableUnit extends MovableUnit{
    public static final float HPBAR_HEIGHT=5; // height of the HP bar above the unit
    public static final int MAX_ARMOR=20; //1 armor = 5% physical damage reduction
    public static final int MAX_MAGIC_RESISTANCE=20;
    int currentHp;
    int maxHp; // amount of damage that can be taken before dying
    int armor; //reduces physical damage taken
    int magicResistance; //reduces magical damage taken
    boolean damageImmune=false;
    HPBar hpBar;

    /**
     * Extends on Movable units with health and resistances.
     * Anything that can be hit should inherit from this.
     * @param texture
     * @param position
     * @param movementSpeed
     * @param maxHp
     * @param armor
     * @param magicResistance
     */
    public DamagableUnit(Texture texture, Coordinate position, float movementSpeed, int maxHp, int armor, int magicResistance,boolean isAlly) {
        super(texture, position, movementSpeed);
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.armor = armor;
        this.magicResistance = magicResistance;

        Color hpBarColor;
        if(isAlly) hpBarColor=Color.CYAN;
        else hpBarColor=Color.RED;
        this.hpBar=new HPBar(position,height,width,hpBarColor);
    }
    public DamagableUnit(Texture texture, Coordinate position, float movementSpeed, int maxHp, int armor, int magicResistance) {
        this(texture,position,movementSpeed,maxHp,armor,magicResistance,false);
        if(this.armor>MAX_ARMOR) this.armor=MAX_ARMOR; //100% damage resistance would cause issues
        if(this.magicResistance>MAX_MAGIC_RESISTANCE) this.magicResistance=MAX_MAGIC_RESISTANCE;
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
    //triggered when health reaches zero
    public void die(){
        // die, duh
        //TODO might need some more work
        movementSpeed=0;
        hpBar.dispose();
        super.dispose();
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
