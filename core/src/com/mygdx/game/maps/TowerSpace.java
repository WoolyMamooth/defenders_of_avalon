package com.mygdx.game.maps;

import static com.mygdx.game.TDGame.SCREEN_WIDTH;
import static com.mygdx.game.TDGame.TEXTURE_EXTENSION;
import static com.mygdx.game.TDGame.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.TDGame;
import com.mygdx.game.screens.buttons.Clickable;
import com.mygdx.game.units.enemies.Enemy;
import com.mygdx.game.units.towers.Tower;

import java.util.List;

public class TowerSpace extends Clickable {
    private class TowerChoiceMenu{
        private class TowerChoiceButton extends Clickable{
            String towerName;
            public TowerChoiceButton(Coordinate position, Texture activeTexture, Texture inactiveTexture,String towerName) {
                super(position, activeTexture, inactiveTexture);
                this.towerName=towerName;
            }

            @Override
            public void onClick() {
                build(towerName);
            }
        }
        String[] buildableTowerNames; //show a menu of these when the TowerSpace is clicked and build the one that is chosen
        TowerChoiceButton[] buttons=new TowerChoiceButton[4]; //buttons that can be clicked to build the chosen tower
        float buttonOffsetX,buttonOffsetY=-128; //offset for the buttons relative to TowerSpace position
        // y is a constant of -128 for now, 64 is the height of a button, so we place 1 above and 1 inline with the towerspace
        int CHOOSABLE_TOWER_NUMBER=4; //max number of towers that can be brought to a game. should remain 4 but you never know
        public TowerChoiceMenu(float buttonOffsetX) {
            this.buttonOffsetX=buttonOffsetX;
            if(position.x()>SCREEN_WIDTH/2f) buttonOffsetX *= -2; //flip the menu to left side if on right side of the screen

            this.buildableTowerNames = player.getEquippedTowers(); //we show the equipped towers as options
            for (int i = 0; i < CHOOSABLE_TOWER_NUMBER; i++) {
                buttons[i]=new TowerChoiceButton(
                        new Coordinate(position.x()+buttonOffsetX, position.y()+buttonOffsetY)
                        ,new Texture("towers/towerButtons/actives/"+buildableTowerNames[i]+TEXTURE_EXTENSION)
                        ,new Texture("towers/towerButtons/inactives/"+buildableTowerNames[i]+TEXTURE_EXTENSION)
                        ,buildableTowerNames[i]);
                buttonOffsetY+=buttons[i].getTexture().getHeight(); //increment vertical offset so we get a list of buttons
            }
        }
        public void draw(SpriteBatch batch){
            for (int i = 0; i < CHOOSABLE_TOWER_NUMBER; i++) {
                buttons[i].drawCheckClick(batch);
            }
        }
        public void dispose(){
            for (int i = 0; i < CHOOSABLE_TOWER_NUMBER; i++) {
                buttons[i].dispose();
            }
        }
    }
    TowerChoiceMenu menu; //this is a menu that shows the buildable towers
    //appears when TowerSpace is clicked
    Tower tower; //tower that has been built here
    int towerBuildID =0; //ID of the tower that will be built
    boolean occupied=false; //defines if a tower has been built here or not
    boolean menuVisible=false; //defines if the menu is visible or not, use in onClick and draw
    public TowerSpace(Coordinate position, Texture activeTexture, Texture inactiveTexture) {
        super(position, activeTexture, inactiveTexture);
        this.menu=new TowerChoiceMenu(activeTexture.getWidth());
    }
    @Override
    public void onClick() {
        towerBuildID =TDMap.lastTowerID+1;
        menuVisible=!menuVisible;
    }

    /**
     * The buildable towers are defined in this method
     * TODO implement gold subtraction for building etc
     * @param towerName what to build
     */
    private void build(String towerName){
        Texture texture=new Texture("towers/towerTextures/"+towerName+TEXTURE_EXTENSION);

        switch (towerName){
            case "archer":
                tower=new Tower(texture,position, towerBuildID,"arrow",0,0,0,0.5f);
                occupied=true;
                break;
            case "None":
            default:
                tower=new Tower(texture,position, towerBuildID,"arrow",0,0,0,1f);
                occupied=true;
                System.out.println("Warning tower "+ towerBuildID +" is set to default");
                break;
        }
        TDMap.lastTowerID++; //increment tower IDs to help keep track of them
    }

    @Override
    public void draw(SpriteBatch batch) {
        if(occupied) {
            tower.draw(batch);
        }else{
            super.draw(batch);
            if (menuVisible) {
                menu.draw(batch);
            }
        }
        if (isActive() && Gdx.input.justTouched()) {
            onClick();
        }
    }

    /**
     * Used for setting the targeting of towers
     * @param enemies
     */
    public void update(List<Enemy> enemies,float timeSinceLastFrame){
        if(tower==null) return;
        tower.update(enemies,timeSinceLastFrame);
    }

    @Override
    public void dispose(){
        super.dispose();
        menu.dispose();
    }
}
