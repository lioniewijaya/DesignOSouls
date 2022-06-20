package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Action to perform actor removal from game map
 */
public class RemoveActorAction extends Action {

    /**
     * Remove the Actor
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string removing actor
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        return actor + " is wiped out";
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
