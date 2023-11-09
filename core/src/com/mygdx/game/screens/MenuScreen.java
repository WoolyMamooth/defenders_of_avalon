package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.TDGame;
import com.mygdx.game.screens.buttons.MenuButton;

import java.util.concurrent.TimeUnit;

public abstract class MenuScreen implements Screen {
        public static float MENU_SCALE=1f;
        public static float MENU_SPACING=0.1f;
        public TDGame game;
        public MenuScreen(TDGame game){
            this.game=game;
        }

        @Override
        public void show() {

        }

        @Override
        public void render(float delta) {
            ScreenUtils.clear(0, 0, 0, 1);
            game.batch.begin();
            game.batch.end();
        }

        protected void renderButton(MenuButton button){
                button.draw(game.batch);
                if (button.isActive() && Gdx.input.justTouched()) {
                        button.onClick();
                }
        }

        @Override
        public void resize(int width, int height) {

        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {

        }

        @Override
        public void dispose() {

        }
}
