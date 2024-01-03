package com.mygdx.game.units.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.units.enemies.Enemy;

import java.util.ArrayList;
import java.util.List;

public class SummonerTower extends Tower{
    SummonSpawner spawner;
    List<Summon> summons=new ArrayList<>();
    int maxSummons; //defines the maximum number of summoned units that can be created by this tower
    String summonName;
    public SummonerTower(Texture texture, Coordinate position, int towerSpawnID, String summonName, float summonDelay, int maxSummons, TowerUpgrade[] upgrades) {
        super(texture, position, towerSpawnID, summonDelay, upgrades);
        this.maxSummons=maxSummons;
        this.summonName=summonName;
        spawner=new SummonSpawner(textureCenterPosition(),range);
    }
    /**
     * Spawns a summon. TODO add offset their spawn location based on how many there are
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
            if(textureCenterPosition().distanceFrom(enemy.textureCenterPosition()) <= range){
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
                //TODO summons heal while not in combat
                break;
            default:
                super.applyUpgrade(u);
                break;
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
