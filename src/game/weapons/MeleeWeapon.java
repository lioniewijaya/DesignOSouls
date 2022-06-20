package game.weapons;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DropItemAction;
import edu.monash.fit2099.engine.WeaponItem;

import java.util.Random;

public abstract class MeleeWeapon extends WeaponItem {
    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
     */
    public MeleeWeapon(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, damage, verb, hitRate);
    }

    /**
     * Damage from weapon, has 20% chance of double damage (Passive skill: Critical Strike)
     * @return the weapon's damage
     */
    @Override
    public int damage() {
        if (new Random().nextInt(5) == 0) {
            return this.damage * 2;
        }
        return this.damage;
    }

    /**
     *
     * @param actor an actor that will interact with this item
     * @return null as weapon cannot be dropped
     */
    @Override
    public DropItemAction getDropAction(Actor actor) {
        return null;
    }
}
