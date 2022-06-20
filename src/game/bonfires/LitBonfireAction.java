package game.bonfires;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.bonfires.Bonfire;
import game.enums.Status;

/**
 * An action to lit and activate bonfire.
 */
public class LitBonfireAction extends Action {
    Bonfire bonfire; // bonfire to lit

    /**
     * constructor
     * @param bonfire bonfire to be lit
     */
    public LitBonfireAction(Bonfire bonfire) {
        this.bonfire = bonfire;
    }

    /**
     * When player chooses to lit bonfire, indicate bonfire is lit and set bonfire to be the latest bonfire interacted
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string to indicate reset
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        bonfire.addCapability(Status.IS_LIT);
        bonfire.getBonfireManager().setLastBonfireInteracted(bonfire);
        return "Bonfire lit";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " lits the bonfire";
    }
}
