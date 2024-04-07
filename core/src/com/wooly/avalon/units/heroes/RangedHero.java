package com.wooly.avalon.units.heroes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.enemies.Enemy;
import com.wooly.avalon.units.projectiles.Projectile;
import com.wooly.avalon.units.projectiles.ProjectileSpawner;

import java.util.ArrayList;
import java.util.List;

public abstract class RangedHero extends Hero{
    ProjectileSpawner projectileSpawner;
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
    public RangedHero(Texture texture, Coordinate position,String name, String description, float movementSpeed, int maxHp, int armor, int magicResistance, int damage, float attackDelay, float attackRange, String projectileName, float searchRange, String damageType) {
        super(texture, position,name,description, movementSpeed, maxHp, armor, magicResistance, damage, attackDelay, searchRange, damageType);
        projectileSpawner=new ProjectileSpawner(position);
        this.attackRange=attackRange;
        this.projectileName=projectileName;
    }
    @Override
    public void attack() {
        projectileSpawner.spawnProjectile(projectileName,target,damage,damageType);
    }

    @Override
    public void update(List<Enemy> enemies, float timeSinceLastFrame) {
        super.update(enemies, timeSinceLastFrame);
        projectileSpawner.update();
        projectileSpawner.spawnLocation=textureCenterPosition();
    }
    @Override
    public void draw(SpriteBatch batch){
        projectileSpawner.draw(batch);
        if(selected){
            batch.end();
            drawRange(attackRange,false);
            batch.begin();
        }
        super.draw(batch);
    }
}
