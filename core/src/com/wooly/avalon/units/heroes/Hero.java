package com.wooly.avalon.units.heroes;

import static com.wooly.avalon.TDGame.SCREEN_BOT_LEFT;
import static com.wooly.avalon.TDGame.SCREEN_BOT_RIGHT;
import static com.wooly.avalon.TDGame.SCREEN_HEIGHT;
import static com.wooly.avalon.TDGame.fetchTexture;
import static com.wooly.avalon.TDGame.place;
import static com.wooly.avalon.TDGame.trueInput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.Path;
import com.wooly.avalon.maps.TDMap;
import com.wooly.avalon.screens.TextBubble;
import com.wooly.avalon.screens.buttons.Button;
import com.wooly.avalon.units.enemies.Enemy;
import com.wooly.avalon.units.AlliedUnit;

import java.util.ArrayList;
import java.util.List;

public abstract class Hero extends AlliedUnit {
    public String name;
    public String description;
    float maxDeathTimer=10f; //the duration for which the hero will be down for if they die
    float deathTimer=0f;
    Texture deadTexture; //texture to be displayed on death
    HeroSelectorButton selectorButton;
    protected boolean selected=false;
    //--movement related variables--------
    boolean moving=false;
    Coordinate goal;
    public Path mapPath;
    private List<Coordinate> pathTrace=new ArrayList<>();
    private int pathTraceIndex;
    Texture movementIndicator;
    //---------------
    ShapeRenderer rangeOutline;
    HeroAbility[] abilities;
    HeroAbilityMenu menu;
    public int maxLevel,level=1; //levels determine how many abilities the hero has available to them, max level is the number of abilities the hero has

    /**
     * Heroes are units that can be directly controlled by the player.
     * They can be moved around on the board and they have unique abilities that can be activated using a menu.
     *
     * @param texture
     * @param position
     * @param movementSpeed
     * @param maxHp
     * @param armor
     * @param magicResistance
     * @param damage
     * @param attackDelay
     * @param searchRange
     * @param damageType
     */
    public Hero(Texture texture, Coordinate position,String name, String description, float movementSpeed, int maxHp, int armor, int magicResistance, int damage, float attackDelay, float searchRange, String damageType) {
        super(texture, position, movementSpeed, maxHp, armor, magicResistance, damage, attackDelay, searchRange, damageType);
        this.name=name;
        this.description=description;
        selectorButton =new HeroSelectorButton(position);
        turnAround();
        deadTexture=fetchTexture("heroes/dead_hero");
        movementIndicator=fetchTexture("heroes/dead_hero");

        rangeOutline=new ShapeRenderer();
        rangeOutline.setColor(Color.BLACK);
    }

    @Override
    public void update(List<Enemy> enemies, float timeSinceLastFrame) {
        if(!isDead()) {
            selectorButton.update(position);
            updateCooldowns(timeSinceLastFrame);
            if (shouldBeDead()) {
                die();
                return;
            }
            if (moving) { //TODO should only move when map is clicked, not menu
                if (target != null) {
                    target.setTarget(null);
                    target = null;
                }
                move(goal);
                if (atCoordinate(goal)) {
                    pathTraceIndex++;
                    if(pathTraceIndex>=pathTrace.size()){
                        moving = false;
                    }else {
                        goal = pathTrace.get(pathTraceIndex);
                    }
                }
                checkMovement();
            } else {
                searchCenterPosition = position;
                checkMovement();
                super.update(enemies, timeSinceLastFrame);
            }
        }else{
            //the hero is dead, decrease death timer
            deathTimer-=timeSinceLastFrame;
            if (deathTimer<=0){
                //if the timer is up, resurrect
                currentHp=maxHp;
            }
        }
    }
    protected void updateCooldowns(float timeSinceLastFrame){
        for (HeroAbility ability:abilities) {
            ability.updateCooldown(timeSinceLastFrame);
        }
    }
    @Override
    public void die() {
        target=null;
        deathTimer=maxDeathTimer;
    }
    @Override
    public void draw(SpriteBatch batch) {
        if(!isDead()) {
            super.draw(batch);
            selectorButton.drawCheckClick(batch);
            menu.draw(batch);
            if (selected) {
                batch.end();
                drawRange(searchRange,true);
                drawRange(searchRange,false);
                batch.begin();
            }
            if(moving){
                Coordinate temp=pathTrace.get(pathTrace.size()-1);
                batch.draw(movementIndicator,temp.x(),temp.y());
            }
        }else{
            batch.draw(deadTexture,position.x(),position.y());
        }
    }
    private boolean isDead(){
        return deathTimer>0;
    }
    /**
     * Draws a circle around the hero at the specified range.
     * @param range
     * @param filled if true it will be filled with an almost transparent grey color
     *               else it will be a black line
     */
    protected void drawRange(float range,boolean filled){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if(filled) {
            rangeOutline.begin(ShapeRenderer.ShapeType.Filled);
            rangeOutline.setColor(0, 0, 0, 0.1f);
            rangeOutline.circle(position.x() + width / 2f, position.y() + width / 2f, range);
            rangeOutline.end();
        }else {
            rangeOutline.begin(ShapeRenderer.ShapeType.Line);
            rangeOutline.setColor(0, 0, 0, 0.7f);
            rangeOutline.circle(position.x() + width / 2f, position.y() + width / 2f, range);
            rangeOutline.end();
        }
    }
    private void checkMovement(){
        if (selected && Gdx.input.justTouched()) {
            //Gdx.input.vibrate(100); TODO maybe add this to manifest it seems kinda fun
            pathTrace.clear();

            selected=false;
            Coordinate input=trueInput();
            input=place(input.x(), SCREEN_HEIGHT-input.y());
            int fromIndex=mapPath.closestTo(position);
            int toIndex=mapPath.closestTo(input);
            if(toIndex>fromIndex) {
                for (int i = fromIndex; i < toIndex+1; i++) {
                    pathTrace.add(mapPath.getCoordinate(i));
                }
            }else{
                for (int i = fromIndex; i > toIndex-1; i--) {
                    pathTrace.add(mapPath.getCoordinate(i));
                }
            }

            /*checks if input is within x distance of the path if yes final coord should be the input if no it should be the closest possible spot
            if(input.distanceFrom(pathTrace.get(pathTrace.size()-1))>32){//some arbitrary distance
                Coordinate v=input.subtract(pathTrace.get(pathTrace.size()-1)).normalize();
                v=v.multiplyByScalar(32);
                pathTrace.add(pathTrace.get(pathTrace.size()-1).add(v));
            }*/
            pathTraceIndex=0;
            goal=pathTrace.get(pathTraceIndex);
            moving=true;
            //System.out.println("Hero moving");
        }
    }

    /**
     * Sets the heroes abilities, max level and creates their ability menu. Must be called in all hero constructors.
     * @param abilities
     */
    protected void setAbilities(HeroAbility[] abilities){
        this.abilities=abilities;
        maxLevel=abilities.length;
        menu=new HeroAbilityMenu(abilities);
    }
    public HeroAbility[] getAbilities() {
        return abilities;
    }
    protected class HeroSelectorButton extends Button {
        private boolean onHero=true;
        /**
         * Used for selecting the hero, floats on top of them and is only visible when clicked.
         * @param position
         */
        public HeroSelectorButton(Coordinate position) {
            super(position, fetchTexture("white_square"), fetchTexture("white_square"));
        }
        public HeroSelectorButton(Coordinate position,Texture texture) {
            super(position, fetchTexture("white_square"), texture);
            onHero=false;
        }
        public void update(Coordinate position){
            this.position=position;
        }
        @Override
        public void draw(SpriteBatch batch) {
            if(!onHero) {
                batch.draw(inactiveTexture,position.x(),position.y(),width,height);
            }
            if (isActive()) {
                batch.setColor(1, 1, 1, 0.2f);
                batch.draw(activeTexture, position.x(), position.y(), width, height);
                batch.setColor(Color.WHITE);
            }
        }
        @Override
        public void onClick() {
            selected=!selected;
            System.out.println("Hero "+(selected?"selected":"not selected"));
        }
    }
    protected class LevelUpButton extends Button{
        int cost=100;
        /**
         * Levels up the hero, costs 100 gold for now.
         *
         * @param position
         */
        public LevelUpButton(Coordinate position) {
            super(position, fetchTexture("heroes/level_up"), fetchTexture("heroes/level_up"));
        }

        @Override
        public void onClick() {
            if(level>=maxLevel) return;
            if(TDMap.attemptGoldSpend(cost)) level++;
        }
    }
    protected class HeroAbilityMenu{
        protected class HeroAbilityButton extends Button {
            HeroAbility ability;
            /**
             * Used for activating the abilities of heroes.
             * @param position
             * @param ability
             */
            public HeroAbilityButton(Coordinate position,HeroAbility ability) {
                super(position, fetchTexture("white_square"), ability.icon);
                this.ability=ability;
            }
            @Override
            public void onClick() {
                if(!ability.isPassive && !ability.onCooldown()) ability.activate();
            }
        }
        protected class HeroAbilityInfo extends Button{
            boolean toggled=false;
            TextBubble textBubble;
            /**
             * When clicked a description of the ability will be displayed.
             * @param position
             */
            public HeroAbilityInfo(Coordinate position, String text) {
                super(position, fetchTexture("white_square"), fetchTexture("enemies/red_square"));
                textBubble=new TextBubble(SCREEN_BOT_LEFT,text,20,Color.WHITE,600,new Color(0,0,0,0.5f));
            }
            @Override
            public void draw(SpriteBatch batch) {
                super.draw(batch);
                if (toggled) textBubble.draw(batch);
            }
            @Override
            public void onClick() {
                toggled=!toggled;
            }
        }
        HeroSelectorButton selectorButton;
        LevelUpButton levelUpButton;
        HeroAbilityButton[] abilityButtons;
        HeroAbilityInfo[] abilityInfos;
        int abilityNum;

        /**
         * Contains buttons for every ability the hero has plus the left-most button can be used to select the hero
         * as if the hero itself was been clicked.
         * @param abilities
         */
        public HeroAbilityMenu(HeroAbility[] abilities){
            abilityNum=abilities.length;

            int iconWidth=abilities[0].icon.getWidth();
            int iconHeight=abilities[0].icon.getHeight();

            abilityButtons=new HeroAbilityButton[abilityNum];
            abilityInfos=new HeroAbilityInfo[abilityNum];

            Coordinate buttonPosition=SCREEN_BOT_RIGHT.subtract(new Coordinate(iconWidth*(abilityNum+1)+10,-10));

            selectorButton=new HeroSelectorButton(buttonPosition,texture);
            levelUpButton=new LevelUpButton(buttonPosition.add(new Coordinate(0,iconHeight)));
            buttonPosition=buttonPosition.add(new Coordinate(iconWidth,0));

            for (int i = 0; i < abilityNum; i++) {
                abilityButtons[i]=new HeroAbilityButton(buttonPosition,abilities[i]);
                abilityInfos[i]=new HeroAbilityInfo(buttonPosition.add(new Coordinate(0,iconHeight)),abilities[i].description);

                buttonPosition=buttonPosition.add(new Coordinate(iconWidth,0));
            }
        }
        public void draw(SpriteBatch batch){
            selectorButton.drawCheckClick(batch);
            levelUpButton.drawCheckClick(batch);
            for (int i = 0; i < abilityNum; i++) {
                if(i<level) {
                    abilityButtons[i].drawCheckClick(batch);
                }
                else{
                    abilityButtons[i].draw(batch);
                }
                abilityInfos[i].drawCheckClick(batch);
            }
        }
    }
}
