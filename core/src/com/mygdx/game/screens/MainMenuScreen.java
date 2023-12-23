package com.mygdx.game.screens;

import static com.mygdx.game.TDGame.TEXTURE_EXTENSION;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.TDGame;
import com.mygdx.game.screens.buttons.MenuButton;
import com.mygdx.game.screens.buttons.ExitButton;
import com.mygdx.game.screens.buttons.LoadScreenButton;

import java.util.concurrent.TimeUnit;

public class MainMenuScreen extends MenuScreen {

    MenuButton playButton,exitButton;
    public MainMenuScreen(TDGame game){
        super(game);
        System.out.println("LOADING MainMenuScreen");
        this.playButton=new LoadScreenButton(this.game,TDGame.fetchTexture("buttons/play_active"),TDGame.fetchTexture("buttons/play"),1,"chooseMap");
        this.exitButton=new ExitButton(TDGame.fetchTexture("buttons/exit_active"),TDGame.fetchTexture("buttons/exit"),2);
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
