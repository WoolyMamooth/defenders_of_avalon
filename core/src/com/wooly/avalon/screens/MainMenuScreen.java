package com.wooly.avalon.screens;

import static com.wooly.avalon.TDGame.place;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.buttons.Clickable;
import com.wooly.avalon.screens.buttons.DifficultyButton;
import com.wooly.avalon.screens.buttons.ExitButton;
import com.wooly.avalon.screens.buttons.LoadScreenButton;
import com.wooly.avalon.screens.other.TextBubble;
import com.wooly.avalon.screens.other.VolumeBox;

public class MainMenuScreen extends MenuScreen {
    Clickable playButton,exitButton,shopButton,difficultyButton;
    TextBubble difficultyText, volumeText;
    VolumeBox volumeBox;
    public MainMenuScreen(TDGame game){
        super(game);
        System.out.println("LOADING MainMenuScreen");
        Coordinate pos=centerButton(1);
        this.playButton=new LoadScreenButton(this.game,TDGame.fetchTexture("buttons/play_active"),
                TDGame.fetchTexture("buttons/play"),
                place(pos.x(), pos.y()),
                "chooseMap");

        pos=centerButton(2);
        this.exitButton=new ExitButton(TDGame.fetchTexture("buttons/exit_active"),
                TDGame.fetchTexture("buttons/exit"),
                place(pos.x(), pos.y()));

        pos=centerButton(3);
        this.shopButton=new LoadScreenButton(this.game,TDGame.fetchTexture("buttons/play_active"),
                TDGame.fetchTexture("buttons/play"),
                place(pos.x(), pos.y()),
                "shop");

        pos=centerButton(4);
        this.difficultyText=new TextBubble(place(pos.x(), pos.y()),"DIFFICULTY:",42, Color.WHITE,600);
        this.difficultyText.setPosition(
                this.difficultyText.getPosition().subtract(
                        new Coordinate(this.difficultyText.getWidth()*0.25f,0)
                )
        ); //center it correctly

        pos=centerButton(5);
        this.difficultyButton=new DifficultyButton(place(pos.x(), pos.y()),42,Color.BLACK,1200,64);

        pos=centerButton(6);
        this.volumeText=new TextBubble(place(pos.x(), pos.y()),"MUSIC",42,Color.WHITE,600);

        pos=centerButton(7);
        this.volumeBox=new VolumeBox(place(pos.x(),pos.y()));

        TDGame.musicHandler.playMusic("menu1");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();
        renderButton(playButton);
        renderButton(exitButton);
        renderButton(shopButton);
        difficultyText.draw(game.batch);
        renderButton(difficultyButton);
        volumeText.draw(game.batch);
        volumeBox.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void dispose() {
        playButton.dispose();
        exitButton.dispose();
        shopButton.dispose();
    }
}
