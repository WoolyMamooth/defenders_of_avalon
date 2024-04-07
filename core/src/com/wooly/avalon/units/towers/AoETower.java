package com.wooly.avalon.units.towers;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.enemies.Enemy;
import com.wooly.avalon.units.projectiles.Projectile;

import java.util.ArrayList;
import java.util.List;

public abstract class AoETower extends RangedTower{
    float aoeRange;

    /**
     * A special ranged tower which shoots projectiles that deal damage in an area.
     * @param texture
     * @param position
     * @param towerSpawnID
     * @param range
     * @param projectileName
     * @param damage
     * @param attackDelay
     * @param upgrades
     * @param damageType
     * @param aoeRange
     */
    public AoETower(Texture texture, Coordinate position, int towerSpawnID, float range, String projectileName, int damage, float attackDelay, TowerUpgrade[] upgrades, String damageType,float aoeRange) {
        super(texture, position, towerSpawnID, range, projectileName, damage, attackDelay, upgrades, damageType);
        this.aoeRange=aoeRange;
    }
    @Override
    public void update(List<Enemy> enemies, float timeSinceLastFrame){
        super.update(enemies,timeSinceLastFrame);
        projectileSpawner.update(enemies,aoeRange);

        //attack if possible
        timeSinceLastAttack+=timeSinceLastFrame;
        if(timeSinceLastAttack>=attackDelay){
            //update target
            target=getTarget(enemies);
            if(target==null) return; //don't attack if there isn't anyone in range
            else attack();
            timeSinceLastAttack=0;
        }
    }
}
