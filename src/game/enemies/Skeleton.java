package game.enemies;

import edu.monash.fit2099.engine.*;
import game.weapons.Broadsword;
import game.RepositionActorAction;
import game.WanderBehaviour;
import game.enums.Status;

/**
 * A skeleton.
 * All Skeletons are represented by a 'S', have 1OO hit points, give 250 souls to Player if killed by an attack, and carry BroadSword.
 * Skeleton wanders around until it detected a player (will follow and attack whenever possible).
 */
public class Skeleton extends ResettableEnemy {

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     */
    public Skeleton(String name, Location initialLocation) {
        super(name, 'S', 100, 250, initialLocation);
        this.addItemToInventory(new Broadsword());
        this.addBehaviour(new WanderBehaviour());
    }

    /**
     * Action to be performed by Skeleton
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return action to be performed by skeleton
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // Repositioned when game is reset
        if (this.hasCapability(Status.REPOSITIONED)) {
            this.removeCapability(Status.REPOSITIONED);
            return new RepositionActorAction(this);
        }

        // Get attack or behaviour action from enemy super class
        return super.playTurn(actions,lastAction,map,display);
    }
}

