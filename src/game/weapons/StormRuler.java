package game.weapons;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;

import java.util.List;
import java.util.Random;

/**
 * StormRuler as one of the weapons in the game
 * Passive skill: Critical Strike (20% chance of double damage)
 * Active skills: Charge (charge weapon for 3 turns to unleash WindSlash and disarm holder) and WindSlash (remove charge and deals double damage wirh 100% hit rate that stuns Yhorm)
 */
public class StormRuler extends MeleeWeapon {

    private int charge = 0; //current charge of the weapon
    private int maxCharge = 3; //maximum charge of the weapon
    private ChargeAction chargeAction = new ChargeAction(this); // instance of the charge action
    private boolean isCharging = false; // indicator if weapon is charging
    private boolean isWindSlashActivated = false; // indicator if wind slash is activated
    private boolean isChargeActionAdded = false; // indicator if charge action is added

    /**
     * constructor
     */
    public StormRuler() {
        super("Storm Ruler", '7', 70, "hits", 60);
    }

    /**
     * Damage from Broadsword, has 20% chance of double damage (Passive skill: Critical Strike), has double damage if used on Yhorm (Active skill: WindSlash)
     * @return StormRuler's damage
     */
    @Override
    public int damage() {
        if (this.isWindSlashActivated) {
            return this.damage*2;
        }
        return super.damage();
    }

    /**
     * @return string when Storm Ruler is used to attack
     */
    @Override
    public String verb() {
        if (this.isWindSlashActivated) {
            return "windslashes";
        }
        return super.verb();
    }

    /**
     * Chance of hit rate, is 100% when wind slash is executed (Active skill: WindSlash)
     * @return hitRate
     */
    @Override
    public int chanceToHit() {
        if (this.isWindSlashActivated) {
            return 100;
        }
        return super.chanceToHit();
    }

    /**
     * Reset the charge for StormRuler
     */
    public void resetCharge() {
        this.charge = 0;
    }

    /**
     * setter method for charging
     * @param charging
     */
    public void setCharging(boolean charging) {
        isCharging = charging;
    }

    /**
     * setter method to indicate windSlash is executed
     * @param windSlashActivated
     */
    public void setWindSlashActivated(boolean windSlashActivated) {
        isWindSlashActivated = windSlashActivated;
    }

    /**
     * Called once per turn, increase Storm Ruler's charge if is charging and provide wind slash when fully charged.
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);
        if (!isCharging && !isWindSlashActivated && (charge < maxCharge)) {
            this.addCapability(Abilities.CHARGE);
        }

        // Increase charge in each turn if it is charging and not full yet, disarm holder during charging
        if (isCharging && charge < maxCharge) {
            this.charge += 1;
            actor.removeCapability(Status.HOSTILE_TO_ENEMY);
        }

        // Remove charging and remove disarmed status from holder, let WindSlash available
        if (charge == maxCharge) {
            this.isCharging = false;
            this.addCapability(Abilities.WINDSLASH);
            actor.addCapability(Status.HOSTILE_TO_ENEMY);
        }
    }

    /**
     * Swap StormRuler with current weapon item in the inventory
     * @param actor an actor that will interact with this item
     * @return action to swap weapon
     */
    @Override
    public PickUpItemAction getPickUpAction(Actor actor) {
        return new SwapWeaponAction(this);
    }

    /**
     * @return string of the Storm Ruler with charging state
     */
    @Override
    public String toString() {
        if (isCharging) {
            return super.toString() + " (CHARGING)";
        }
        if (charge == maxCharge) {
            return super.toString() + " (FULLY CHARGED)";
        }
        return super.toString();
    }

    /**
     * List of actions allowed for Storm Ruler
     * @return list of actions that can be performed by StormRuler
     */
    @Override
    public List<Action> getAllowableActions() {
        // Add charge action if charge ability is active but charge action is not in list
        if (this.hasCapability(Abilities.CHARGE)) {
            if (!isChargeActionAdded) {
                this.allowableActions.add(chargeAction);
                isChargeActionAdded = true;
            }
        }
        else {
            this.allowableActions.remove(chargeAction);
            isChargeActionAdded = false;
        }
        return super.getAllowableActions();
    }

    /**
     * Get the active skill
     * @param target the target actor
     * @param direction the direction of target, e.g. "north"
     * @return Storm Ruler's active skill action
     */
    @Override
    public WeaponAction getActiveSkill(Actor target, String direction) {
        // Only allow wind slash when StormRuler is fully charged and enemy is weak to StormRuler
        if (this.hasCapability(Abilities.WINDSLASH) && target.hasCapability(Status.WEAK_TO_STORMRULER)) {
            return new WindSlashAction(this,target,direction);
        }
        return null;
    }
}

