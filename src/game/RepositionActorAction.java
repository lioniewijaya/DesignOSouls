package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.Repositionable;

/**
 * Action to perform reposition of a repositionable to initial location
 */
public class RepositionActorAction extends Action {
    private Repositionable repositionable; // something that can be reposition to the initial position

    /**
     * constructor
     * @param repositionable
     */
    public RepositionActorAction(Repositionable repositionable) {
        this.repositionable = repositionable;
    }

    /**
     * Reposition the Actor
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string indicating reposition of Actor
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        repositionable.reposition(map);
        return actor + " is repositioned";
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
