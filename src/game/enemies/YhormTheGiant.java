package game.enemies;

import edu.monash.fit2099.engine.*;
import game.RepositionActorAction;
import game.weapons.YhormGreatMachete;
import game.enums.Status;

/**
 * Yhorm the Giant, is a Lord of Cinder (Boss) in the game.
 * Is represented by a 'Y', have 500 hit points, give 5000 souls to Player if killed by an attack, carries Yhorm's Great Machete, and weak to StormRuler.
 * Yhorm does nothing until it detected a player (will follow and attack whenever possible).
 * Yhorm enters second phase where weapon become more effective when HP is below half of maximum HP.
 */
public class YhormTheGiant extends LordOfCinder {
    private YhormGreatMachete machete; // a weapon used by YhormTheGiant

    /**
     * constructor
     * @param name              the name of the Yhorm
     * @param initialLocation   initial location when game is started
     */
    public YhormTheGiant(String name, Location initialLocation) {
        super(name, 'Y', 500, 5000, initialLocation);
        machete = new YhormGreatMachete();
        this.addItemToInventory(machete);
        this.addCapability(Status.WEAK_TO_STORMRULER);
    }

    @Override
    /**
     * Reset as Lord of Cinder and activate machete's second phase
     */
    public void secondPhase() {
        super.secondPhase();
        machete.setSecondPhase(true);
    }

    /**
     * Perform the action by YhormTheGiant
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return action perform by YhormTheGiant
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

        // Stunned by storm ruler
        if (this.hasCapability(Status.STUNNED)) {
            this.removeCapability(Status.STUNNED);
            return new DoNothingAction();
        }

        // Get attack or behaviour action from enemy super class
        return super.playTurn(actions,lastAction,map,display);
    }

    /**
     * Reset YhormTheGiant
     */
    @Override
    public void resetInstance() {
        super.resetInstance();
        machete.setSecondPhase(false);
    }
}

