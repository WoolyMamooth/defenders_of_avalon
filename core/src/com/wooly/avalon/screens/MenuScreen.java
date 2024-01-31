package com.wooly.avalon.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.buttons.Clickable;

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
        }

        protected void renderButton(Clickable button){
            button.drawCheckClick(game.batch);
        }

        /**
         * Returns a centered coordinate using the following logic:
         * x = TDGame.SCREEN_WIDTH / 2f
         * y = TDGame.SCREEN_HEIGHT - (TDGame.SCREEN_HEIGHT * MenuScreen.MENU_SPACING) * positionInMenu)
         * Intended for use on buttons in a menu. Positions should start from 1.
         * @param positionInMenu
         * @return
         */
        public Coordinate centerButton(int positionInMenu){
            Coordinate coord=new Coordinate(TDGame.SCREEN_WIDTH / 2f,TDGame.SCREEN_HEIGHT - (TDGame.SCREEN_HEIGHT * MENU_SPACING)*positionInMenu);
            return coord;
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
            dispose();
        }

        @Override
        public void dispose() {

        }
}
