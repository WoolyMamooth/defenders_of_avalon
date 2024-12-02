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
    /**
     * King Arthur.
     * @param position
     */
    public ArthurPendragon(Coordinate position) {
        super(fetchTexture("heroes/arthur/arthur"), position,"Arthur",
                "Arthur Pendragon, the one true king.\nChosen by the Lady of the lake herself, Arthur\nwas blessed with great power and resiliance.\nHe can summon his royal guard to aid him\nin battle and buff and heal them,\n as well as himself.",
                100, 100, 10, 10, 10, 1, 200, "physical");
        spawner=new SummonSpawner(position,searchRange);

        setAbilities(new HeroAbility[]{
            new SummonGuards(),
            new BuffGuards(),
            new HealSelf()
        });

        summons=new Summon[maxLevel*2];
    }
    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (int i = 0; i < summons.length; i++) {
            if(summons[i]!=null) summons[i].draw(batch);
        }
    }
    @Override
    public void update(List<Enemy> enemies, float timeSinceLastFrame) {
        super.update(enemies, timeSinceLastFrame);
        for (int i = 0; i < summons.length; i++) {
            try {
                Summon summon = summons[i];
                if(summon!=null){
                    summon.setSearchCenterPosition(summon.getPosition());
                    summon.update(enemies,timeSinceLastFrame);
                    if(summon.shouldBeDead()){
                        summon.die();
                        summons[i]=null;
                    }
                }
            }catch (Exception e){
                //kagi
            }
        }
    }
    private class SummonGuards extends HeroAbility{
        public SummonGuards() {
            super("For the King",fetchTexture("heroes/arthur/ability1"),30);
            setDescription("For every level he has,\nArthur summons 2 guards to help fight\nagainst his enemies.");
        }
        @Override
        public void activate() {
            for (int i = 0; i < level*2; i++) {
                summons[i]=spawner.spawnSummon("guard",position);
            }
            super.activate();
        }
    }
    private class BuffGuards extends HeroAbility{
        Texture buffIndicator=fetchTexture("heroes/arthur/armorBuff");
        public BuffGuards() {
            super("Buff Guards", fetchTexture("heroes/arthur/ability2"),15);
            setDescription("Arthur uses his power to heal his guards\nand to grant them bonus armor.");
        }
        @Override
        public void activate() {
            for (int i = 0; i < summons.length; i++) {
                if(summons[i]==null) continue;
                summons[i].addBuff(new UnitBuff("armor",20,10,buffIndicator));
                summons[i].addBuff(new UnitBuff("healing",1,10));
            }
            super.activate();
        }
    }
    private class HealSelf extends HeroAbility{
        private int amount=100;
        public HealSelf() {
            super("Heal Self",fetchTexture("heroes/arthur/ability3"),40);
            setDescription("Arthur channels his power \nto heal himself for "+amount+" HP.");
        }
        @Override
        public void activate() {
            heal(amount);
            super.activate();
        }
    }
}
