package game.weapons;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponAction;
import game.enums.Abilities;

/**
 * An action where the player can charge Storm Ruler for three (3) turns
 */
public class ChargeAction extends WeaponAction {
    private StormRuler stormRuler; // an instance of stormRuler to charge

    /**
     * Constructor
     * @param stormRuler a Storm Ruler
     */
    public ChargeAction(StormRuler stormRuler) {
        super(stormRuler);
        this.stormRuler = stormRuler;
    }

    /**
     * Perform the charge action
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string of charge action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        stormRuler.setCharging(true);
        stormRuler.removeCapability(Abilities.CHARGE); // Remove ability to charge when charging
        return actor + " is charging " + stormRuler;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " charges " + stormRuler;
    }
}
