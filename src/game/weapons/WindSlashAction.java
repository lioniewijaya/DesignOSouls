package game.weapons;

import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.enums.Abilities;
import game.enums.Status;
import game.weapons.StormRuler;

/**
 * An action that perform the windSlash
 * An action where the player can use Storm Ruler to windslash Yhorm, available when Storm Ruler is fully charged and holder is adjacent to Yhorm.
 * When executed, it deals 2x damage ‘wind slash’ to Yhorm with a 100% hit rate and stuns it and let Storm Ruler has charge skill again.
 */
public class WindSlashAction extends WeaponAction {
    private Actor target; //target to attack
    private String direction; // attack direction
    private StormRuler stormRuler; // stormRuler that has this skill

    /**
     * constructor
     * @param stormRuler a Storm Ruler
     * @param actor actor as target to hit
     * @param direction direction to hit target
     */
    public WindSlashAction(StormRuler stormRuler, Actor actor, String direction) {
        super(stormRuler);
        this.target = actor;
        this.direction = direction;
        this.stormRuler = stormRuler;
    }

    /**
     * Perform the windSlash action
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string of windSlash action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        stormRuler.resetCharge();

        // Execute attack action with wind slash activated
        stormRuler.setWindSlashActivated(true);
        String attackResult = new AttackAction(target,direction).execute(actor,map);
        stormRuler.setWindSlashActivated(false);

        // Stun target, remove wind slash ability and activate charge ability
        target.addCapability(Status.STUNNED);
        stormRuler.removeCapability(Abilities.WINDSLASH);
        stormRuler.addCapability(Abilities.CHARGE);

        return attackResult;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " windslashes " + target + " at " + direction;
    }
}

