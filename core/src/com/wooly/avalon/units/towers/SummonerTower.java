package com.wooly.avalon.units.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.UnitBuff;
import com.wooly.avalon.units.enemies.Enemy;

import java.util.ArrayList;
import java.util.List;

public abstract class SummonerTower extends Tower{
    SummonSpawner spawner;
    List<Summon> summons=new ArrayList<>();
    protected int maxSummons; //defines the maximum number of summoned units that can be created by this tower
    protected String summonName;
    public SummonerTower(Texture texture, Coordinate position, int towerSpawnID,float range, String summonName, float summonDelay, int maxSummons, TowerUpgrade[] upgrades) {
        super(texture, position, towerSpawnID, summonDelay,range, upgrades);
        this.maxSummons=maxSummons;
        this.summonName=summonName;
        spawner=new SummonSpawner(position,range);
    }
    /**
     * Spawns a summon.
     */
    @Override
    public void attack() {
        summons.add(spawner.spawnSummon(summonName));
        System.out.println("summoned: "+summons.get(0));
    }

    /**
     * Updates summons and tries to spawn a new one if possible.
     * @param enemies
     * @param timeSinceLastFrame
     */
    @Override
    public void update(List<Enemy> enemies, float timeSinceLastFrame) {
        super.update(enemies,timeSinceLastFrame);
        updateExistingSummons(enemies,timeSinceLastFrame);

        if(summons.size()<maxSummons){
            timeSinceLastAttack += timeSinceLastFrame;
        }
        if(timeSinceLastAttack>attackDelay){
            attack();
            timeSinceLastAttack=0;
        }
    }
    private void updateExistingSummons(List<Enemy> enemies, float timeSinceLastFrame){
        List<Enemy> targets=getPossibleTargets(enemies);
        List<Summon> shouldBeDeleted=new ArrayList<>();

        for (Summon summon:summons) {
            summon.update(targets,timeSinceLastFrame);
            if(summon.getCurrentHp()<=0){
                shouldBeDeleted.add(summon);
            }
        }
        for (Summon summon:shouldBeDeleted) {
            summon.die();
            summons.remove(summon);
        }
    }
    /**
     * Gets all enemies on screen and returns the ones that are in range of the tower.
     * @param enemies
     * @return
     */
    private List<Enemy> getPossibleTargets(List<Enemy> enemies) {
        if(enemies.isEmpty()) return null;
        List<Enemy> inRange=new ArrayList<>();
        for (Enemy enemy:enemies) {
            if(position.distanceFrom(enemy.position) <= range){
                inRange.add(enemy);
            }
        }
        return inRange;
    }
    @Override
    protected void applyUpgrade(TowerUpgrade u){
        switch (u.stat){
            case "summons":
                this.maxSummons+=u.getIncrease();
                break;
            case "armor":
                spawner.armorUpgrade+=u.getIncrease();
                break;
            case "magic resistance":
                spawner.magicResistanceUpgrade+=u.getIncrease();
                break;
            case "HP":
                spawner.maxHpUpgrade+=u.getIncrease();
                break;
            case "damage":
                spawner.damageUpgrade+=u.getIncrease();
                break;
            case "heal":
            case "healing":
                spawner.healingUpgrade+=u.getIncrease();
                break;
            default:
                super.applyUpgrade(u);
                break;
        }
    }
    protected void applyBuff(UnitBuff buff, boolean removeMode) {
        float modifier = buff.getModifier();
        if (removeMode) modifier = -modifier;
        switch (buff.stat) {
            //pass it on to the units
            case "armor":
            case "magicResistance":
            case "healing":
                for (Summon summon:summons){
                    summon.addBuff(buff);
                }
                break;
            default:
                super.applyBuff(buff,removeMode);
        }
    }
    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for(Summon summon:summons){
            summon.draw(batch);
        }
    }
}
