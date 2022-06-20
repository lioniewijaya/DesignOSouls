package game.enemies;

import edu.monash.fit2099.engine.*;
import game.weapons.DarkmoonLongbow;
import game.RepositionActorAction;
import game.enums.Status;

/**
 * Aldrich the Devourer, is a Lord of Cinder (Boss) in the game.
 * Is represented by a 'A', have 350 hit points, give 5000 souls to Player if killed by an attack, and carries Darkmoon Longbow.
 * Aldrich does nothing until it detected a player (will follow and attack whenever possible).
 * Aldrich enters second phase where HP is healed by 20% when HP is below half of maximum HP.
 */
public class AldrichTheDevourer extends LordOfCinder {
    private DarkmoonLongbow darkmoon; // a weapon used by AldrichTheDevourer

    /**
     * Constructor.
     *
     * @param name            the name of the Aldrich
     * @param initialLocation initial location when game is started
     */
    public AldrichTheDevourer(String name, Location initialLocation) {
        super(name,'A',350, 5000, initialLocation);
        darkmoon = new DarkmoonLongbow(this);
        this.addItemToInventory(darkmoon);
    }

    @Override
    /**
     * Reset as Lord of Cinder and heals 20% of current HP
     */
    public void secondPhase() {
        super.secondPhase();
        this.heal((int) (0.2*hitPoints));

    }

    /**
     * Perform the action by AldrichTheDevourer
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return action perform by AldrichTheDevourer
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // Repositioned when game is reset
        if (this.hasCapability(Status.REPOSITIONED)) {
            this.removeCapability(Status.REPOSITIONED);
            return new RepositionActorAction(this);
        }

        // HP less than half, activate second phase
        if (!this.enteredSecondPhase && this.hitPoints < 0.5*this.maxHitPoints) {
            display.println(this + " goes berserk");
            secondPhase();
        }

        // Get attack or behaviour action from enemy super class
        return super.playTurn(actions,lastAction,map,display);
    }
}
