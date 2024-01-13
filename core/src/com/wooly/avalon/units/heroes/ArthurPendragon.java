package com.wooly.avalon.units.heroes;

import static com.wooly.avalon.TDGame.fetchTexture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.UnitBuff;
import com.wooly.avalon.units.enemies.Enemy;
import com.wooly.avalon.units.towers.Summon;
import com.wooly.avalon.units.towers.SummonSpawner;

import java.util.List;

public class ArthurPendragon extends Hero{
    SummonSpawner spawner;
    Summon[] summons;
    private static final int MAX_SUMMON_NUMBER=1;
    /**
     * King Arthur.
     * @param position
     */
    public ArthurPendragon(Coordinate position) {
        super(fetchTexture("heroes/arthur"), position, 100, 100, 10, 10, 10, 1, 200, "physical");
        spawner=new SummonSpawner(position,searchRange);
        summons=new Summon[MAX_SUMMON_NUMBER];

        abilities=new HeroAbility[]{
            new SummonGuards(),
            new BuffGuards(),
            new HealSelf()
        };

        menu=new HeroAbilityMenu(abilities);
    }
    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (int i = 0; i < MAX_SUMMON_NUMBER; i++) {
            if(summons[i]!=null) summons[i].draw(batch);
        }
    }
    @Override
    public void update(List<Enemy> enemies, float timeSinceLastFrame) {
        super.update(enemies, timeSinceLastFrame);
        for (int i = 0; i < MAX_SUMMON_NUMBER; i++) {
            Summon summon=summons[i];
            if(summon!=null){
                summon.setSearchCenterPosition(summon.getPosition());
                summon.update(enemies,timeSinceLastFrame);
                if(summon.isDead()){
                    summon.die();
                    summons[i]=null;
                }
            }
        }
    }
    private class SummonGuards extends HeroAbility{
        public SummonGuards() {
            super("SummonGuards",fetchTexture("enemies/red_square"),30);
        }
        @Override
        public void activate() {
            for (int i = 0; i < MAX_SUMMON_NUMBER; i++) {
                summons[i]=spawner.spawnSummon("guard",position);
            }
            super.activate();
        }
    }
    private class BuffGuards extends HeroAbility{
        public BuffGuards() {
            super("BuffGuards", fetchTexture("enemies/red_square"),15);
        }
        @Override
        public void activate() {
            for (int i = 0; i < MAX_SUMMON_NUMBER; i++) {
                if(summons[i]==null) continue;
                summons[i].addBuff(new UnitBuff("armor",20,10));
                summons[i].addBuff(new UnitBuff("healing",1,10));
            }
            super.activate();
        }
    }
    private class HealSelf extends HeroAbility{
        public HealSelf() {
            super("HealSelf",fetchTexture("enemies/red_square"),40);
        }
        @Override
        public void activate() {
            heal(100);
            super.activate();
        }
    }
}
