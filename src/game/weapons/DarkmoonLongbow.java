package game.weapons;

import edu.monash.fit2099.engine.Actor;
import game.enemies.Enemy;

import java.util.Random;

/**
 * Darkmoon Longbow as one of the weapons in the game
 * Passive skill: Critical Strike (15% chance of double damage), Ranged Weapon (it can attack an enemy that is far-away or in other words, beyond hand-to-hand distance/adjacent squares.
 * The maximum range is 3-squares away. If there is a wall within its path, the attack will automatically ‘miss’ the target.)
 */
public class DarkmoonLongbow extends GameWeaponItem {
    /**
     * Constructor if Darkmoon is not hold by enemy.
     *
     * @param actor non enemy actor holding darkmoon
     */
    public DarkmoonLongbow(Actor actor) {
        super("DarkmoonLongbow", 'D', 70, "hits", 80, 3, actor);
    }

    /**
     * Constructor if Darkmoon is hold by enemy.
     *
     * @param enemy enemy actor holding darkmoon
     */
    public DarkmoonLongbow(Enemy enemy) {
        super("DarkmoonLongbow", 'D', 70, "hits", 80, 3, enemy);
    }

    /**
     * Damage from DarkmoonLongbow, has 15% chance of double damage (Passive skill: Critical hit)
     *
     * @return the DarkmoonLongbow's damage
     */
    @Override
    public int damage() {
        if (new Random().nextInt(100) < 15) {
            return this.damage * 2;
        }
        return super.damage();
    }
}
