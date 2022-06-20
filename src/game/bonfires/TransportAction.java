package game.bonfires;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.bonfires.Bonfire;

/**
 * An action to transport player from a bonfire to another bonfire.
 */
public class TransportAction extends Action {
    Bonfire bonfire; // bonfire transport to

    /**
     * constructor
     * @param bonfire bonfire to transport player to
     */
    public TransportAction(Bonfire bonfire) {
        this.bonfire = bonfire;
    }

    /**
     When player transports to another bonfire, move player and set bonfire to be the latest bonfire interacted
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string to indicate transport
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.moveActor(actor,bonfire.getBonfireManager().getBonfireLocation(bonfire));
        bonfire.getBonfireManager().setLastBonfireInteracted(bonfire);
        return actor + " transported to " + bonfire;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Transport to " + bonfire;
    }
}
