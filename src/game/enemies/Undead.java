package game.enemies;


import edu.monash.fit2099.engine.*;
import game.RemoveActorAction;
import game.WanderBehaviour;
import game.enemies.Enemy;
import game.enums.Status;

/**
 * An undead minion.
 * All Undeads are represented by an 'u', have 30 hit points, give 50 souls to Player if killed by an attack, and do not carry any weapon.
 * Undeads wanders around until it detected a player (will follow and attack whenever possible).
 * Are randomly spawned at Cemetery at each game turn.
 */
public class Undead extends Enemy {

	/**
	 * Constructor.
	 * @param name the name of this Undead
	 */
	public Undead(String name) {
		super(name, 'u', 30, 50);
		this.addBehaviour(new WanderBehaviour());
	}

	/**
	 * Action to be performed by Undead
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return action to be performed by Undead
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Removed when game is reset
		if (this.hasCapability(Status.WIPED_OUT)) {
			return new RemoveActorAction();
		}

		// Get attack or behaviour action from enemy super class
		return super.playTurn(actions,lastAction,map,display);
	}

	/**
	 * Remove undead
	 */
	@Override
	public void resetInstance() {
		// Undead are wiped out
		this.addCapability(Status.WIPED_OUT);
	}

	/**
	 * @return false as Undead is not permanent in map
	 */
	@Override
	public boolean isExist() {
		return false;
	}
}