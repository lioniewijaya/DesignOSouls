package game;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.engine.Menu;
import game.enemies.Enemy;
import game.enums.Abilities;
import game.enums.Direction;
import game.enums.Status;
import game.interfaces.Consumer;
import game.interfaces.Resettable;
import game.interfaces.Soul;
import game.terrains.Valley;
import game.weapons.Broadsword;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Soul, Resettable, Consumer {

	private final Menu menu = new Menu();
	private int souls = 0; // current souls of the player
	private Location lastBonfireInteractedLocation; // location of the last bonfire interacted

	/**
	 * Constructor.
	 * @param name        Name to call the player in the UI
	 */
	public Player(String name) {
		super(name, '@', 200);
		this.addCapability(Status.HOSTILE_TO_ENEMY);
		this.addCapability(Abilities.REST);
		this.addCapability(Abilities.FALL);
		this.addCapability(Abilities.ENTER_FLOOR);
		this.addCapability(Abilities.ENTER_DOOR);
		this.addItemToInventory(new EstusFlask("Estus Flask",'E',this));
		this.addItemToInventory(new Broadsword());
		this.registerInstance();
	}

	/**
	 * Perform the Player's action
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the action performed by the Player
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Player dies
		if (!this.isConscious()) {
			// Print dying message
			display.println(
					"\n#     # ####### #     #    ######  ### ####### ######  \n" +
							" #   #  #     # #     #    #     #  #  #       #     # \n" +
							"  # #   #     # #     #    #     #  #  #       #     # \n" +
							"   #    #     # #     #    #     #  #  #####   #     # \n" +
							"   #    #     # #     #    #     #  #  #       #     # \n" +
							"   #    #     # #     #    #     #  #  #       #     # \n" +
							"   #    #######  #####     ######  ### ####### ######  \n");

			return new ResetAction(lastBonfireInteractedLocation,getLastLocation(map,lastAction));
		}

		// Print player's HP and souls in console
		display.println(this.name + ", HP: " + this.hitPoints + "/" + this.maxHitPoints + ", Weapon: " + this.getWeapon() + ", Souls: " + this.souls);

		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// return/print the console menu
		return menu.showMenu(this, actions, display);
	}

	/**
	 * Transfer Souls
	 * @param soulObject a target souls.
	 */
	@Override
	public void transferSouls(Soul soulObject) {

		if (soulObject.addSouls(this.souls)) {
			this.subtractSouls(this.souls);
		}
	}

	/**
	 * Add souls
	 * @param souls number of souls to be incremented.
	 * @return true as soul is added
	 */
	@Override
	public boolean addSouls(int souls) {
		this.souls += souls;
		return true;
	}

	/**
	 * Subtract soul
	 * @param souls number souls to be deducted
	 * @return true as soul is subtracted
	 */
	@Override
	public boolean subtractSouls(int souls) {
		this.souls -= souls;
		return true;
	}

	/**
	 * Reset hit points
	 */
	@Override
	public void resetInstance() {
		this.hitPoints = this.maxHitPoints;
	}

	/**
	 * @return true
	 */
	@Override
	public boolean isExist() {
		return true;
	}

	/**
	 * List of Actions that can be performed by Player
	 * @param otherActor the Actor that might be performing attack
	 * @param direction  String representing the direction of the other Actor
	 * @param map        current GameMap
	 * @return the list of actions that can be performed by Player
	 */
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions actions = new Actions();
		// it can be attacked only by enemy
		if(otherActor.hasCapability(Status.HOSTILE_TO_PLAYER)) {
			actions.add(new AttackAction(this,direction));
		}
		// get weapon action
		Weapon weapon = otherActor.getWeapon();
		WeaponAction weaponAction = weapon.getActiveSkill(this, direction);
		if(weaponAction != null){
			actions.add(weaponAction);
		}
		return actions;
	}

	/**
	 * Get the last location before dying
	 * @param map game map
	 * @param lastAction last action before player's death
	 * @return last position in the map
	 */
	public Location getLastLocation(GameMap map, Action lastAction) {
		Location lastLocation = map.locationOf(this);
		// Player falls into valley
		if (map.locationOf(this).getGround() instanceof Valley) {
			// Get player's last last location based on hotkey and current position
			lastLocation = Direction.lastPosition(lastAction.hotkey(), map, lastLocation);
		}
		return lastLocation;
	}

	/**
	 * Getter method to get max HP
	 * @return maximum hit points
	 */
	@Override
	public int getMaxHp() {
		return this.maxHitPoints;
	}

	/**
	 * Getter method to get last bonfire interacted location
	 * @return location of last bonfire interacted
	 */
	public Location getLastBonfireInteractedLocation() {
		return lastBonfireInteractedLocation;
	}

	/**
	 * Setter method to set last bonfire interacted location
	 */
	public void setLastBonfireInteractedLocation(Location lastBonfireInteractedLocation) {
		this.lastBonfireInteractedLocation = lastBonfireInteractedLocation;
	}
}