package com.wooly.avalon.screens;

import static com.wooly.avalon.TDGame.SCREEN_BOT_LEFT;
import static com.wooly.avalon.TDGame.SCREEN_BOT_RIGHT;
import static com.wooly.avalon.TDGame.SCREEN_HEIGHT;
import static com.wooly.avalon.TDGame.SCREEN_TOP_LEFT;
import static com.wooly.avalon.TDGame.SCREEN_TOP_RIGHT;
import static com.wooly.avalon.TDGame.SCREEN_WIDTH;
import static com.wooly.avalon.TDGame.fetchTexture;
import static com.wooly.avalon.TDGame.place;
import static com.wooly.avalon.TDGame.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.buttons.Button;
import com.wooly.avalon.screens.buttons.Clickable;
import com.wooly.avalon.screens.buttons.CustomButton;
import com.wooly.avalon.screens.buttons.LoadScreenButton;
import com.wooly.avalon.screens.other.TextBubble;
import com.wooly.avalon.units.heroes.ArthurPendragon;
import com.wooly.avalon.units.heroes.Hero;
import com.wooly.avalon.units.heroes.HeroAbility;
import com.wooly.avalon.units.heroes.Merlin;
import com.wooly.avalon.units.heroes.Mordred;
import com.wooly.avalon.units.towers.Tower;
import com.wooly.avalon.units.towers.towers.ArcherTower;
import com.wooly.avalon.units.towers.towers.BallistaTower;
import com.wooly.avalon.units.towers.towers.BarracksTower;
import com.wooly.avalon.units.towers.towers.FarmTower;
import com.wooly.avalon.units.towers.towers.PaladinsTower;
import com.wooly.avalon.units.towers.towers.PriestTower;
import com.wooly.avalon.units.towers.towers.WizardTower;

import java.util.Arrays;
import java.util.Objects;

public class UnitSetupScreen extends MenuScreen{
    Clickable mainMenuButton;
    TextBubble stardustText;
    UnitContainer container;
    Coordinate containerPos,stardustTextPos;
    EquippedUnitsContainer equippedUnits;
    SwitchUnitButton forward;
    SwitchUnitButton backward;
    int containerUnitId=0;
    /**
     * On this screen the Player can purchase new towers and heroes as well as equip or unequip the units they have.
     * @param game
     */
    public UnitSetupScreen(TDGame game) {
        super(game);
        stardustTextPos=centerButton(1);
        updateStardustText();

        //Coordinate temp=centerButton(2);
        this.mainMenuButton=new LoadScreenButton(this.game,TDGame.fetchTexture("buttons/menu_active"),
                TDGame.fetchTexture("buttons/menu"),
                SCREEN_TOP_RIGHT.subtract(new Coordinate(256,128)),"mainMenu");
        mainMenuButton.width=mainMenuButton.width*2;
        mainMenuButton.height=mainMenuButton.height*2;

        containerPos=place(SCREEN_WIDTH*0.125f,0);
        container=new HeroContainer(containerPos,new ArthurPendragon(containerPos));

        forward=new SwitchUnitButton(SCREEN_BOT_RIGHT.subtract(new Coordinate(192,0)),true); //TODO place this correctly
        backward=new SwitchUnitButton(SCREEN_BOT_LEFT.add(new Coordinate(64,0)),false);

        equippedUnits=new EquippedUnitsContainer(SCREEN_TOP_LEFT);
    }
    private void updateStardustText(){
        stardustText =new TextBubble(stardustTextPos,player.getStardust()+" Stardust",40, Color.GOLD,SCREEN_WIDTH);
    }

    /**
     * Swaps the unit on the screen. EVERY SINGLE UNIT must be added here.
     */
    private void swapContainerUnit(){
        int maxUnitId=9; //case number/id of the last possible unit
        switch (containerUnitId){
        // HEROES
            case 0:
                container.dispose();
                container=new HeroContainer(containerPos,new ArthurPendragon(containerPos));
                break;
            case 1:
                container.dispose();
                container=new HeroContainer(containerPos,new Mordred(containerPos));
                break;
            case 2:
                container.dispose();
                container=new HeroContainer(containerPos,new Merlin(containerPos));
                break;
        // TOWERS
            case 3:
                container.dispose();
                container=new TowerContainer(containerPos,new ArcherTower(containerPos,0));
                break;
            case 4:
                container.dispose();
                container=new TowerContainer(containerPos,new BarracksTower(containerPos,0));
                break;
            case 5:
                container.dispose();
                container=new TowerContainer(containerPos,new WizardTower(containerPos,0));
                break;
            case 6:
                container.dispose();
                container=new TowerContainer(containerPos,new FarmTower(containerPos,0));
                break;
            case 7:
                container.dispose();
                container=new TowerContainer(containerPos,new BallistaTower(containerPos,0));
                break;
            case 8:
                container.dispose();
                container=new TowerContainer(containerPos,new PaladinsTower(containerPos,0));
                break;
            case 9:
                container.dispose();
                container=new TowerContainer(containerPos,new PriestTower(containerPos,0));
                break;

            case -1:
                //went left from ID 0
                containerUnitId=maxUnitId;
                swapContainerUnit();
                break;
            default:
                //went right from last one
                containerUnitId=0;
                swapContainerUnit();
                break;
        }
        System.out.println("swapping unit to "+containerUnitId);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();
        stardustText.draw(game.batch);
        renderButton(mainMenuButton);
        try {
            container.draw(game.batch);
        }catch (NullPointerException e){
            //caused when we exit to the main menu because hero is disposed of
            //doesn't cause issues, fixes itself next frame
        }
        renderButton(forward);
        renderButton(backward);
        equippedUnits.draw(game.batch);
        game.batch.end();
    }
    @Override
    public void dispose(){
        mainMenuButton.dispose();
        container.dispose();
    }
    private class SwitchUnitButton extends Button{
        boolean forward;
        /**
         * Switches the unit in container to the next one/previous one.
         */
        public SwitchUnitButton(Coordinate position,boolean forward) {
            super(position, fetchTexture("buttons/switch_unit"), fetchTexture("buttons/switch_unit"));
            this.forward=forward;
        }
        @Override
        public void onClick() {
            if(forward){
                containerUnitId++;
            }else{
                containerUnitId--;
            }
            swapContainerUnit();
        }

        @Override
        public void draw(SpriteBatch batch) {
            if(forward) super.draw(batch);
            else batch.draw((isActive() ? activeTexture : inactiveTexture), position.x()+width, position.y(), -width, height);
        }
    }
    private class BuyButton extends CustomButton{
        String whatToUnlock;
        int cost;
        boolean hero;
        /**
         * @param hero if true this will use player.unlockHero else it will use player.unlockTower
         */
        public BuyButton(Coordinate position,String whatToUnlock, int cost,boolean hero) {
            super(position, "BUY for "+cost+" SD", 30, Color.WHITE, Color.BLACK, 500, 128);
            this.whatToUnlock=whatToUnlock;
            this.cost=cost;
            this.hero=hero;
        }
        @Override
        public void onClick() {
            if (player.spendStardust(cost)){
                if(hero)player.unlockHero(whatToUnlock);
                else player.unlockTower(whatToUnlock);
                //update the screen
                updateStardustText();
                swapContainerUnit();
            }
        }
    }
    private class EquipButton extends CustomButton{
        String whatToEquip;
        boolean hero;
        boolean unequip;
        /**
         * Adds the currently displayed unit to the equippedTowers/hero of the player.
         * If already equipped it instead unequips it.
         */
        public EquipButton(Coordinate position, boolean unequip, String whatToEquip, boolean hero) {
            super(position, unequip?"Unequip":"Equip", 30, Color.WHITE, Color.BLACK, 500, 128);
            this.unequip=unequip;
            this.whatToEquip = whatToEquip;
            this.hero=hero;
        }
        @Override
        public void onClick() {
            if(hero) player.equipHero(whatToEquip,unequip);
            else player.equipTower(whatToEquip,unequip);
            //update the screen
            equippedUnits=new EquippedUnitsContainer(SCREEN_TOP_LEFT);
            swapContainerUnit();
        }
    }
    private class EquippedUnitsContainer{
        Coordinate position;
        int unitNum; //how many units it contains
        Texture[] unitTextures;
        Texture emptyPosTexture;
        float unitOffsetX;
        float textureBaseSize=64,textureSizeMultiplier=2;
        /**
         * Shows what the player currently has equipped.
         */
        public EquippedUnitsContainer(Coordinate position) {
            unitNum=5; //4 towers + 1 hero
            unitTextures=new Texture[unitNum];
            emptyPosTexture=fetchTexture("white_square");

            if(!Objects.equals(player.getEquippedHero(), "None")){
                unitTextures[0]=fetchTexture("heroes/"+player.getEquippedHero().toLowerCase()+"/"+player.getEquippedHero().toLowerCase());
            }else{
                unitTextures[0]=emptyPosTexture;
            }

            int i=1;
            for (String unit: player.getEquippedTowers()) {
                if(Objects.equals(unit, "None")){
                    unitTextures[i]=emptyPosTexture;
                }else{
                    unitTextures[i]=fetchTexture("towers/towerTextures/"+unit);
                }
                i++;
            }
            unitOffsetX=unitTextures[0].getWidth()*textureSizeMultiplier;
            this.position=position.subtract(new Coordinate(0,unitTextures[0].getHeight()*textureSizeMultiplier));
        }
        public void draw(SpriteBatch batch){
            float x=position.x();
            float y=position.y();
            for (int i = 0; i < unitNum; i++) {
                batch.draw(unitTextures[i],x,y,textureBaseSize*textureSizeMultiplier,textureBaseSize*textureSizeMultiplier);
                x+=unitOffsetX;
            }
        }
    }
    private abstract class UnitContainer{
        Coordinate position;
        Texture background;
        Color backgroundColor=new Color(1,1,1,0.3f);
        TextBubble nameText;
        TextBubble descriptionText;
        boolean canBeBought=true; //true if the Player has yet to unlock the unit
        CustomButton button;
        float width,height;
        float TEXTURE_SIZE_MULTIPLIER =5f;

        public UnitContainer(Coordinate position) {
            this.position = position;
            this.background=fetchTexture("white_square");

            this.width=SCREEN_WIDTH*0.75f;
            this.height=SCREEN_HEIGHT*0.8f;
        }
        public void draw(SpriteBatch batch){
            batch.setColor(backgroundColor);
            batch.draw(background,position.x(),position.y(),width,height);
            batch.setColor(Color.WHITE);

            //text
            nameText.draw(batch);
            descriptionText.draw(batch);

            //buy / equip
            renderButton(button);
        }
        void updateDescriptionPosition(){
            descriptionText.position=nameText.position.subtract(new Coordinate(0,descriptionText.getHeight()+30));
        }
        public abstract void dispose();
    }
    private class HeroContainer extends UnitContainer{
        protected class HeroInfoButton extends Button {
            String abilityDescription;
            /**
             * When clicked a description of the ability will be displayed.
             * @param position
             */
            public HeroInfoButton(Coordinate position, String text) {
                super(position, fetchTexture("white_square"), fetchTexture("enemies/red_square"));
                abilityDescription=text;
            }
            public HeroInfoButton(Coordinate position, String text,Texture texture) {
                super(position, texture, texture);
                abilityDescription=text;
            }
            public HeroInfoButton(Coordinate position, String text,float width, float height,Texture texture) {
                super(position, texture, texture);
                this.width=width;
                this.height=height;
                abilityDescription=text;
            }
            @Override
            public void draw(SpriteBatch batch) {
                super.draw(batch);
            }
            @Override
            public void onClick() {
                descriptionText.setText(abilityDescription);
                updateDescriptionPosition();
            }
        }
        Hero hero;
        HeroInfoButton heroDescriptionButton;
        HeroInfoButton[] abilityInfoButtons;

        /**
         * A container that displays the textrue and info of heroes and their abilities.
         * @param position
         * @param hero
         */
        public HeroContainer(Coordinate position,Hero hero) {
            super(position);
            this.hero = hero;

            //position components here
            //hero texture
            this.hero.setWidth(hero.getWidth()* TEXTURE_SIZE_MULTIPLIER);
            this.hero.setHeight(hero.getHeight()* TEXTURE_SIZE_MULTIPLIER);
            this.hero.setPosition(hero.position.add(new Coordinate(0,height-hero.getHeight())));

            //text
            this.nameText=new TextBubble(position.add(hero.position.add(new Coordinate(hero.getWidth(),hero.getHeight()-100)))
                    , hero.name,50,Color.CYAN,SCREEN_WIDTH*0.75f);
            this.descriptionText =new TextBubble(nameText.position, hero.description,30,Color.WHITE,SCREEN_WIDTH*0.75f);
            descriptionText.setWrap(false);
            updateDescriptionPosition();

            //ability infos
            heroDescriptionButton=new HeroInfoButton(hero.position, hero.description,hero.getWidth(),hero.getHeight(),fetchTexture("white_square"));

            HeroAbility[] abilities=this.hero.getAbilities();
            int abilitynum=abilities.length;
            abilityInfoButtons=new HeroInfoButton[abilitynum];
            int offsetX=abilities[0].icon.getWidth();
            int offsetY=-abilities[0].icon.getHeight()*2;
            for (int i = 0; i < abilitynum; i++) {
                abilityInfoButtons[i]=new HeroInfoButton(hero.position.add(new Coordinate(offsetX*i,offsetY)),abilities[i].getDescription(),abilities[i].icon);
            }


            //if the player already has the hero then it shouldn't be buy-able
            if(Arrays.asList(player.getUnlockedHeroes()).contains(hero.name)){
                //button that allows equiping
                button=new EquipButton(position, Objects.equals(player.getEquippedHero(), hero.name),hero.name,true);
                button.setPosition(button.getPosition().add(new Coordinate(width - button.width - 20, 10)));
                canBeBought=false;
            }else{
                //button that allows player to buy it
                button = new BuyButton(position, hero.name, 100, true);
                button.setPosition(button.getPosition().add(new Coordinate(width - button.width - 20, 10)));
            }
        }
        @Override
        public void draw(SpriteBatch batch){
            super.draw(batch);

            //texture
            batch.draw(hero.texture,hero.position.x(),hero.position.y(),hero.getWidth(),hero.getHeight());

            if(heroDescriptionButton.isActive()) {
                batch.setColor(0, 0, 0, 0.3f);
                heroDescriptionButton.drawCheckClick(batch);
                batch.setColor(Color.WHITE);
            }

            //abilities
            for (HeroInfoButton infoButton:abilityInfoButtons) {
                infoButton.drawCheckClick(batch);
            }
        }
        @Override
        public void dispose(){
            hero.dispose();
        }
    }
    private class TowerContainer extends UnitContainer{
        Tower tower;
        public TowerContainer(Coordinate position,Tower tower) {
            super(position);
            this.tower=tower;

            //position components here
            //tower texture
            this.tower.setWidth(tower.getWidth()* TEXTURE_SIZE_MULTIPLIER);
            this.tower.setHeight(tower.getHeight()* TEXTURE_SIZE_MULTIPLIER);
            this.tower.setPosition(tower.position.add(new Coordinate(0,height-tower.getHeight())));

            //text
            this.nameText=new TextBubble(position.add(tower.position.add(new Coordinate(tower.getWidth(),tower.getHeight()-100)))
                    , tower.name,50,Color.CYAN,SCREEN_WIDTH*0.75f);
            this.descriptionText =new TextBubble(nameText.position, tower.description,30,Color.WHITE,SCREEN_WIDTH*0.75f);
            descriptionText.setWrap(false);
            updateDescriptionPosition();

            //button that allows player to buy it/equip it
            if(Arrays.asList(player.getUnlockedTowers()).contains(tower.name)){
                button=new EquipButton(position, Arrays.asList(player.getEquippedTowers()).contains(tower.name), tower.name,false);
                button.setPosition(button.getPosition().add(new Coordinate(width - button.width - 20, 10)));
            }else{
                button = new BuyButton(position, tower.name, 100, false);
                button.setPosition(button.getPosition().add(new Coordinate(width - button.width - 20, 10)));
            }
        }

        @Override
        public void draw(SpriteBatch batch) {
            super.draw(batch);
            //texture
            batch.draw(tower.texture,tower.position.x(),tower.position.y(),tower.getWidth(),tower.getHeight());
        }
        @Override
        public void dispose() {
            tower.dispose();
        }
    }
}
