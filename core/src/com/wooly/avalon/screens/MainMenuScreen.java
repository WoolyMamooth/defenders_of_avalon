package com.wooly.avalon.screens;

import static com.wooly.avalon.TDGame.place;

import com.badlogic.gdx.utils.ScreenUtils;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.buttons.Clickable;
import com.wooly.avalon.screens.buttons.ExitButton;
import com.wooly.avalon.screens.buttons.LoadScreenButton;

public class MainMenuScreen extends MenuScreen {

    Clickable playButton,exitButton;
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
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        game.batch.begin();
        renderButton(this.playButton);
        renderButton(this.exitButton);
        game.batch.end();
    }

    @Override
    public void dispose() {
        playButton.dispose();
        exitButton.dispose();
    }
}
