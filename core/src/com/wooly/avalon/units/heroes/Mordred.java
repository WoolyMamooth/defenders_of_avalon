package com.wooly.avalon.units.heroes;

import static com.wooly.avalon.TDGame.fetchTexture;

import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.units.UnitBuff;
import com.wooly.avalon.units.enemies.Enemy;

import java.util.List;

public class Mordred extends RangedHero{
    private int baseDamage; //necessary for AllConsumingFlames
    /**
     * Mordred is a ranged fire mage that gains more power the more enemies she defeats.
     * @param position
     */
    public Mordred(Coordinate position) {
        super(fetchTexture("heroes/mordred/mordred"),
                position,"Mordred","Mordred, a gifted young mage\nshe uses elemental fire damage to\nmassacre her enemies.",
                150, 70, 0, 5, 3, 3, 200,
                "mordred_fireball",300, "magic");
        baseDamage=damage;
        setAbilities(new HeroAbility[]{
            new AllConsumingFlames(),
            new Lightspeed(),
            new Cataclysm()
        });
    }
    @Override
    public void update(List<Enemy> enemies, float timeSinceLastFrame) {
        super.update(enemies, timeSinceLastFrame);
        if(target!=null && target.shouldBeDead()) abilities[0].activate(); //if the target died get the damage buff from the passive
    }

    @Override
    public void die() {
        super.die();
        damage=baseDamage;
    }

    private class AllConsumingFlames extends HeroAbility{
        int damageIncreasePerStack;
        public AllConsumingFlames(){
            super("All Consuming Flames",fetchTexture("heroes/mordred/ability1"));
            damageIncreasePerStack=1;
            setDescription(" Mordred gains power from the enemies she fells.\n After every unit killed she gets "
                    +damageIncreasePerStack+" damage.\n Stacks infinitely, but resets on death.");
        }
        @Override
        public void activate() {
            damage+=damageIncreasePerStack;
        }
    }
    private class Lightspeed extends HeroAbility{
        float buffDuration =10f;
        int movementSpeedModifier=200;
        int attackSpeedModifier=250;
        public Lightspeed(){
            super("Lightspeed",fetchTexture("heroes/mordred/ability2"),20);
            setDescription(" Mordred channels the power of fire to accelerate herself,\n for "+buffDuration+" seconds she gains "+
                    movementSpeedModifier+" movement speed\n and "+attackSpeedModifier+" attack speed.");
        }
        @Override
        public void activate() {
            addBuff(new UnitBuff("movementSpeed",movementSpeedModifier, buffDuration));
            addBuff(new UnitBuff("attackSpeed",attackSpeedModifier,buffDuration));
            super.activate();
        }
    }
    private class Cataclysm extends HeroAbility{
        public Cataclysm(){
            super("Cataclysm",fetchTexture("heroes/mordred/ability3"),60);
            setDescription("Mordred sacrifices the power she has gained\nfrom All Consuming Flames to deal pure damage\nequal to her current damage to all enemies on screen.\nHer damage is then reset to it's original value.");
        }
        @Override
        public void activate() {
            for (Enemy enemy:map.getEnemies()) {
                projectileSpawner.spawnLocation=enemy.position.add(new Coordinate(0,400));
                projectileSpawner.spawnProjectile("mordred_cataclysm",enemy,damage,"pure");
                projectileSpawner.spawnLocation=position;
            }
            damage=baseDamage;
            super.activate();
        }
    }
}
