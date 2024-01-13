package com.wooly.avalon.units.heroes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.projectiles.Projectile;
import com.wooly.avalon.units.projectiles.ProjectileSpawner;

import java.util.ArrayList;
import java.util.List;

public abstract class RangedHero extends Hero{
    ProjectileSpawner projectileSpawner;
    List<Projectile> projectiles=new ArrayList<>();
    String projectileName;

    /**
     * @param texture
     * @param position
     * @param movementSpeed
     * @param maxHp
     * @param armor
     * @param magicResistance
     * @param damage
     * @param attackDelay
     * @param searchRange
     * @param damageType
     */
    public RangedHero(Texture texture, Coordinate position, float movementSpeed, int maxHp, int armor, int magicResistance, int damage, float attackDelay, float attackRange, String projectileName, float searchRange, String damageType) {
        super(texture, position, movementSpeed, maxHp, armor, magicResistance, damage, attackDelay, searchRange, damageType);
        projectileSpawner=new ProjectileSpawner(position);
        this.attackRange=attackRange;
        this.projectileName=projectileName;
    }
    @Override
    public void attack() {
        projectiles.add(projectileSpawner.spawnProjectile(projectileName,target,damage,damageType));
    }
    protected void updateExistingProjectiles(){
        if(projectiles==null) return;
        List<Projectile> shouldBeDeleted=new ArrayList<>();

        for (Projectile projectile:projectiles) {
            //if it returns true we should delete the projectile
            if(projectile.update()){ //moves the projectiles towards the target
                shouldBeDeleted.add(projectile);
            }
        }
        //delete the ones that hit
        for (Projectile projectile:shouldBeDeleted) {
            projectiles.remove(projectile);
        }
    }
    @Override
    public void draw(SpriteBatch batch){
        for (Projectile projectile:projectiles) {
            projectile.draw(batch);
        }
        if(selected){
            batch.end();
            drawRange(attackRange,false);
            batch.begin();
        }
        super.draw(batch);
    }
}
