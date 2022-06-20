package game;

import edu.monash.fit2099.engine.*;
import game.bonfires.Bonfire;

/**
 * An action to reset all resettables.
 * Refill player's HP and Estus Flask, reset enemies' position, health, skill (Undead is an exception, they are removed instead).
 * This action is executed when player rested at a bonfire or player dies.
 */
public class ResetAction extends Action {
    private Location lastBonfireLocation; // location of the last bonfire interacted
    private Location lastLocation; // last location before player dies
    private Bonfire bonfirePassedBy; // bonfire that a player passes by

    /**
     * constructor
     * @param bonfirePassedBy a bonfire adjacent to player
     */
    public ResetAction(Bonfire bonfirePassedBy) {
        this.bonfirePassedBy = bonfirePassedBy;
    }

    /**
     * constructor
     * @param lastBonfireLocation last bonfire interacted location
     * @param lastLocation last location before player dies
     */
    public ResetAction(Location lastBonfireLocation, Location lastLocation) {
        this.lastBonfireLocation = lastBonfireLocation;
        this.lastLocation = lastLocation;
    }

    /**
     * When player chooses to rest, last bonfire rested is updated and rest manager is run to reset all resettables.
     * When player dies, token is placed in last location before dead and player is spawned back to latest bonfire rested before rest manager is run.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string to indicate reset
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Player is dead
        if (lastBonfireLocation != null) {
            // Put token at last location before dying
            TokenOfSoul token = new TokenOfSoul();
            actor.asSoul().transferSouls(token);
            map.at(lastLocation.x(), lastLocation.y()).addItem(token);

            // Spawn player at last bonfire interacted
            map.moveActor(actor, lastBonfireLocation);
        }
        // Player rest at bonfire
        else {
            bonfirePassedBy.getBonfireManager().setLastBonfireInteracted(bonfirePassedBy);
        }

        ResetManager.getInstance().run();
        return "Game is reset";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Rest at " + bonfirePassedBy;
    }
}
