package game;

import edu.monash.fit2099.engine.*;

/**
 * An action to pick up Token of Soul
 */
public class PickUpTokenAction extends PickUpItemAction {
    private TokenOfSoul token; // token to be picked up

    /**
     * constructor
     * @param token Token of Soul to be picked up
     */
    public PickUpTokenAction(TokenOfSoul token) {
        super(token);
        this.token = token;
    }

    /**
     * Perform the pick up token action
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string of the pick up token action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        token.transferSouls(actor.asSoul());

        // if the ground has item, remove that item.
        map.locationOf(actor).removeItem(token);
        return actor + " picks up " + token;
    }
}
