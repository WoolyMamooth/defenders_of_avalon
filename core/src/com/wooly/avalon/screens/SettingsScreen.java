package com.wooly.avalon.screens;

import static com.wooly.avalon.TDGame.place;
import static com.wooly.avalon.TDGame.player;

import com.badlogic.gdx.graphics.Color;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.buttons.Clickable;
import com.wooly.avalon.screens.buttons.CustomButton;
import com.wooly.avalon.screens.buttons.DifficultyButton;
import com.wooly.avalon.screens.buttons.LoadScreenButton;
import com.wooly.avalon.screens.other.HeroMenuScaleSlider;
import com.wooly.avalon.screens.other.TextBubble;
import com.wooly.avalon.screens.other.VolumeSlider;

public class SettingsScreen extends MenuScreen{
    Clickable mainMenuButton,difficultyButton, deleteSaveButton;
    TextBubble difficultyText, volumeText,menuScaleText;
    VolumeSlider volumeSlider;
    HeroMenuScaleSlider menuScaleSlider;
    public SettingsScreen(TDGame game) {
        super(game);
        Coordinate pos=centerButton(1);
        this.mainMenuButton=new LoadScreenButton(this.game,TDGame.fetchTexture("buttons/menu_active"),
                TDGame.fetchTexture("buttons/menu"),
                place(pos.x(), pos.y()),"mainMenu");

        pos=centerButton(2);
        this.difficultyText=new TextBubble(place(pos.x(), pos.y()),"DIFFICULTY",42, Color.WHITE,600);
        this.difficultyText.setPosition(
                this.difficultyText.getPosition().subtract(
                        new Coordinate(this.difficultyText.getWidth()*0.25f,0)
                )
        ); //center it correctly

        pos=centerButton(3);
        this.difficultyButton=new DifficultyButton(place(pos.x(), pos.y()),42,Color.BLACK,1200,64);

        pos=centerButton(4);
        this.volumeText=new TextBubble(place(pos.x()-20, pos.y()),"MUSIC",42,Color.WHITE,600);

        pos=centerButton(5);
        this.volumeSlider =new VolumeSlider(place(pos.x(),pos.y()));

        pos=centerButton(6);
        this.menuScaleText=new TextBubble(place(pos.x()-90, pos.y()),"INGAME UI SIZE",42,Color.WHITE,600);

        pos=centerButton(7);
        this.menuScaleSlider =new HeroMenuScaleSlider(place(pos.x(),pos.y()));

        pos=centerButton(8);
        //this button will allow the player to create a new save
        this.deleteSaveButton=new CustomButton(pos,"DELETE SAVE",42,Color.WHITE,Color.BLACK,600,64) {
            @Override
            public void onClick() {
                player.createSavefile();
                player.loadData();
            }
        };
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();
        renderButton(mainMenuButton);
        difficultyText.draw(game.batch);
        renderButton(difficultyButton);
        volumeText.draw(game.batch);
        volumeSlider.draw(game.batch);
        menuScaleText.draw(game.batch);
        menuScaleSlider.draw(game.batch);
        renderButton(deleteSaveButton);
        game.batch.end();
    }
}
