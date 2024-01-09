package com.mygdx.game.units.heroes;

import static com.mygdx.game.TDGame.SCREEN_HEIGHT;
import static com.mygdx.game.TDGame.fetchTexture;
import static com.mygdx.game.TDGame.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.screens.buttons.Button;
import com.mygdx.game.screens.buttons.CustomButton;
import com.mygdx.game.units.enemies.Enemy;
import com.mygdx.game.units.towers.AlliedUnit;

import java.util.List;

public abstract class Hero extends AlliedUnit {
    HeroSelectorButton button;
    protected boolean selected=false;
    boolean moving=false;
    Coordinate goal;
    ShapeRenderer rangeOutline;
    /**
     *
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
    public Hero(Texture texture, Coordinate position, float movementSpeed, int maxHp, int armor, int magicResistance, int damage, float attackDelay, float searchRange, String damageType) {
        super(texture, position, movementSpeed, maxHp, armor, magicResistance, damage, attackDelay, searchRange, damageType);
        button=new HeroSelectorButton(position);
        turnAround();

        rangeOutline=new ShapeRenderer();
        rangeOutline.setColor(Color.BLACK);
    }

    @Override
    public void update(List<Enemy> enemies, float timeSinceLastFrame) {
        button.update(position);
        if(isDead()) {
            die(); //TODO heroes will respawn, but for now they just get deleted
            return;
        }
        if(moving){ //TODO should only move when map is clicked, not menu
            if (target != null) {
                target.setTarget(null);
                target = null;
            }
            move(goal);
            if(atCoordinate(goal)){
                moving=false;
            }
            checkMovement();
        }else {
            searchCenterPosition=position;
            checkMovement();
            super.update(enemies, timeSinceLastFrame);
        }
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void draw(SpriteBatch batch) {
        if(!isDead()) {
            super.draw(batch);
            button.drawCheckClick(batch);
            if (selected) {
                batch.end();
                drawRange();
                batch.begin();
            }
        }
    }
    protected void drawRange(){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        rangeOutline.begin(ShapeRenderer.ShapeType.Filled);
        rangeOutline.setColor(0,0,0,0.1f);
        rangeOutline.circle(position.x() + width / 2f, position.y() + width / 2f,searchRange);
        rangeOutline.end();

        rangeOutline.begin(ShapeRenderer.ShapeType.Line);
        rangeOutline.setColor(0,0,0,0.7f);
        rangeOutline.circle(position.x() + width / 2f, position.y() + width / 2f,searchRange);
        rangeOutline.end();
    }
    private void checkMovement(){
        if (selected && Gdx.input.justTouched()) {
            selected=false;
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            goal = new Coordinate(x, SCREEN_HEIGHT - y);
            moving=true;
        }
    }
    protected class HeroSelectorButton extends Button {
        /**
         * Used for selecting the hero, floats on top of them and is only visible when clicked.
         * @param position
         */
        public HeroSelectorButton(Coordinate position) {
            super(position, fetchTexture("white_square"), fetchTexture("white_square"));
        }
        public void update(Coordinate position){
            this.position=position;
        }
        @Override
        public void draw(SpriteBatch batch) {
            if(isActive()){
                batch.setColor(1,1,1,0.2f);
                batch.draw(activeTexture,position.x(),position.y(),width,height);
                batch.setColor(Color.WHITE);
            }
        }
        @Override
        public void onClick() {
            selected=!selected;
        }
    }
    protected class HeroAbilityMenu{
        protected class HeroAbilityButton extends CustomButton {
            HeroAbility ability;
            /**
             * Used to activate the abilities of the hero.
             *
             * @param position
             * @param text
             * @param fontsize
             * @param textColor
             * @param backgroundColor
             * @param width
             * @param height
             */
            public HeroAbilityButton(Coordinate position, String text, int fontsize, Color textColor, Color backgroundColor, float width, float height) {
                super(position, text, fontsize, textColor, backgroundColor, width, height);
            }
            @Override
            public void onClick() {
                //TODO activate the ability
            }
        }
        HeroAbilityButton[] abilityButtons;
        public HeroAbilityMenu(HeroAbility[] abilities){
            int abilityNum=abilities.length;
            abilityButtons=new HeroAbilityButton[abilityNum];
        }
    }
}
