package com.wooly.avalon.units.heroes;

import static com.wooly.avalon.TDGame.fetchTexture;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.TDMap;
import com.wooly.avalon.maps.TowerSpace;
import com.wooly.avalon.units.UnitBuff;
import com.wooly.avalon.units.enemies.Enemy;

public class Merlin extends RangedHero{
    public Merlin(Coordinate position) {
        super(fetchTexture("heroes/merlin/merlin"),
                position,"Merlin","The great mage uses the power\nof the elements to hinder the advancement of\nthe enemy, and to aid his allies in battle.",
                75f, 70, 0, 10, 8, 1.4f, 200,
                "merlin_magic",220, "magic");
        setAbilities(new HeroAbility[]{
                new LikeTheWind(),
                new LikeTheSea(),
                new LikeTheEarth()
        });
    }
    private class LikeTheWind extends HeroAbility{
        float duration=10;
        public LikeTheWind(){
            super("Like The Wind",fetchTexture("enemies/red_square"),10);
            setDescription("Merlin calls on the power of the wind\nto grant all towers an attack speed\nboost for"+duration+" seconds.");
        }

        @Override
        public void activate() {
            for (TowerSpace towerSpace:map.getTowerSpaces()) {
                try {
                    towerSpace.getTower().addBuff(new UnitBuff("attackSpeed",100,duration));
                }catch (NullPointerException e){
                    //no tower has been built there yet
                    continue;
                }
            }
            super.activate();
        }
    }
    private class LikeTheEarth extends HeroAbility{
        float duration=10;
        int resistance=3;
        int healing=5;
        public LikeTheEarth(){
            super("Like The Earth",fetchTexture("enemies/red_square"),20);
            setDescription("Merlin calls on the power\nof the earth to grant all allied units bonus armor,\nmagic resistance and healing for"+duration+" seconds.");
        }
        @Override
        public void activate() {
            //add to self
            addBuff(new UnitBuff("armor",resistance,duration));
            addBuff(new UnitBuff("magicResistance",resistance,duration));
            addBuff(new UnitBuff("healing",healing,duration));

            //add to every tower(will be activated on all units of summon towers)
            for (TowerSpace towerSpace:map.getTowerSpaces()) {
                try {
                    towerSpace.getTower().addBuff(new UnitBuff("armor",resistance,duration));
                    towerSpace.getTower().addBuff(new UnitBuff("magicResistance",resistance,duration));
                    towerSpace.getTower().addBuff(new UnitBuff("healing",healing,duration));
                }catch (NullPointerException e){
                    //no tower has been built there yet
                    continue;
                }
            }
            super.activate();
        }
    }
    private class LikeTheSea extends HeroAbility{
        float duration=6;
        float modifier=-0.5f;
        public LikeTheSea(){
            super("Like The Sea",fetchTexture("enemies/red_square"),10);
            setDescription("Merlin calls on the power\nof the sea to slow down every enemy on screen.\nLasts for "+duration+" seconds.");
        }
        @Override
        public void activate() {
            for (Enemy enemy:map.getEnemies()) {
                enemy.addBuff(new UnitBuff("movementSpeed",enemy.getMovementSpeed()*modifier,duration));
            }
            super.activate();
        }
    }
}