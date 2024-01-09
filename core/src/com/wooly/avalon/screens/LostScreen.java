package com.wooly.avalon.screens;

import static com.wooly.avalon.TDGame.place;

import com.badlogic.gdx.utils.ScreenUtils;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.buttons.LoadScreenButton;

public class LostScreen extends MenuScreen{

    LoadScreenButton mainMenuButton;
    public LostScreen(TDGame game) {
        super(game);

        Coordinate pos=centerButton(1);
        this.mainMenuButton=new LoadScreenButton(this.game,
                TDGame.fetchTexture("buttons/menu_active"),
                TDGame.fetchTexture("buttons/menu"),
                place(pos.x(), pos.y()),
                "mainMenu");
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
