package com.wooly.avalon.screens;

import static com.wooly.avalon.TDGame.place;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.buttons.LoadScreenButton;
import com.wooly.avalon.screens.other.TextBubble;

public class LostScreen extends MenuScreen{

    LoadScreenButton mainMenuButton;
    TextBubble textBubble;
    public LostScreen(TDGame game) {
        super(game);

        Coordinate pos=centerButton(3);
        this.textBubble=new TextBubble(place(pos.x()-175, pos.y()),"Avalon has fallen!",60, Color.RED,700);


        pos=centerButton(5);
        this.mainMenuButton=new LoadScreenButton(this.game,
                TDGame.fetchTexture("buttons/menu_active"),
                TDGame.fetchTexture("buttons/menu"),
                place(pos.x(), pos.y()),
                "mainMenu");
    }

    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        game.batch.begin();
        textBubble.draw(game.batch);
        renderButton(this.mainMenuButton);
        game.batch.end();
    }

    @Override
    public void dispose() {
        mainMenuButton.dispose();
    }
}
