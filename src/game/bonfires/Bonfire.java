package game.bonfires;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.ResetAction;
import game.enums.Abilities;
import game.enums.Status;

/**
 * An area in map surrounded by walls and ground is made of floors. Only player can enter this area.
 * Player can rest here to reset and is spawned back to Bonfire when he dies.
 */
public class Bonfire extends Ground {

    private String name; // name of Bonfire
    private BonfireManager bonfireManager; // a manager that manages bonfire

    /**
     * Constructor
     * @param name name of the Bonfire
     * @param location location of the Bonfire
     */
    public Bonfire(String name, Location location, BonfireManager bonfireManager) {
        super('B');
        this.name = name;
        this.bonfireManager = bonfireManager;
        bonfireManager.addBonfire(this,location);
    }

    /**
     * Implement reset action in Bonfire
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return the Reset Action
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        if (actor.hasCapability(Abilities.REST)) {
            if (!this.hasCapability(Status.IS_LIT)) {
                return new Actions(new LitBonfireAction(this));
            }
            Actions actions = new Actions();
            actions.add(new ResetAction(this));
            // Add list of transport action to other active bonfire
            for (Bonfire bonfire : bonfireManager.getOtherActiveBonfiresInGame(this)) {
                actions.add(new TransportAction(bonfire));
            }
            return actions;
        }
        return new Actions();
    }

    /**
     *
     * @return bonfire manager
     */
    public BonfireManager getBonfireManager() {
        return bonfireManager;
    }

    /**
     *
     * @return name of the Bonfire
     */
    @Override
    public String toString() {
        return name;
    }
}
