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
        updateExistingProjectiles(enemies);

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
    protected void updateExistingProjectiles(List<Enemy> enemies){
        if(projectiles==null) return;
        List<Projectile> shouldBeDeleted=new ArrayList<>();

        for (Projectile projectile:projectiles) {
            //if it returns true we should delete the projectile
            if(projectile.update(enemies,aoeRange)){ //moves the projectiles towards the target
                shouldBeDeleted.add(projectile);
            }
        }
        //delete the ones that hit
        for (Projectile projectile:shouldBeDeleted) {
            projectiles.remove(projectile);
        }
    }
}
