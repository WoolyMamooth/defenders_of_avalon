package com.mygdx.game.screens;

import static com.mygdx.game.TDGame.TEXTURE_EXTENSION;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.TDGame;
import com.mygdx.game.screens.buttons.LoadScreenButton;

public class LostScreen extends MenuScreen{

    LoadScreenButton mainMenuButton;
    public LostScreen(TDGame game) {
        super(game);
        this.mainMenuButton=new LoadScreenButton(this.game,TDGame.fetchTexture("buttons/menu_active"),TDGame.fetchTexture("buttons/menu"),1,"mainMenu");
    }

    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        game.batch.begin();
        renderButton(this.mainMenuButton);
        game.batch.end();
    }

    @Override
    public void dispose() {
        mainMenuButton.dispose();
    }
}
