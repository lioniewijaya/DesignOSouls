package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;
import game.enemies.LordOfCinder;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;

	/**
	 * The direction of incoming attack.
	 */
	protected String direction;

	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 *
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target, String direction) {
		this.target = target;
		this.direction = direction;
	}

	/**
	 * Perform the available attack action
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return the string of the action to be performed
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

		if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		target.hurt(damage);
		if (!target.isConscious()) {
			// Allow dropping droppable items in inventory for non player only
			if (!(target instanceof Player)) {
				Actions dropActions = new Actions();
				// Drop items
				for (Item item : target.getInventory())
					dropActions.add(item.getDropAction(actor));
				for (Action drop : dropActions)
					drop.execute(target, map);
				// Transfer soul
				target.asSoul().transferSouls(actor.asSoul());
				// remove actor
				map.removeActor(target);
			}
			result += System.lineSeparator() + target + " is killed.";
			if (target instanceof LordOfCinder) {
				// Add additional message if target died is a Lord of Cinder
				result += System.lineSeparator() +
						" /$$                                 /$$                  /$$$$$$         /$$$$$$  /$$                 /$$                           /$$                 /$$$$$$$$       /$$ /$$                    \n" +
						"| $$                                | $$                 /$$__  $$       /$$__  $$|__/                | $$                          |__/                | $$_____/      | $$| $$                    \n" +
						"| $$        /$$$$$$   /$$$$$$   /$$$$$$$        /$$$$$$ | $$  \\__/      | $$  \\__/ /$$ /$$$$$$$   /$$$$$$$  /$$$$$$   /$$$$$$        /$$  /$$$$$$$      | $$    /$$$$$$ | $$| $$  /$$$$$$  /$$$$$$$ \n" +
						"| $$       /$$__  $$ /$$__  $$ /$$__  $$       /$$__  $$| $$$$          | $$      | $$| $$__  $$ /$$__  $$ /$$__  $$ /$$__  $$      | $$ /$$_____/      | $$$$$|____  $$| $$| $$ /$$__  $$| $$__  $$\n" +
						"| $$      | $$  \\ $$| $$  \\__/| $$  | $$      | $$  \\ $$| $$_/          | $$      | $$| $$  \\ $$| $$  | $$| $$$$$$$$| $$  \\__/      | $$|  $$$$$$       | $$__/ /$$$$$$$| $$| $$| $$$$$$$$| $$  \\ $$\n" +
						"| $$      | $$  | $$| $$      | $$  | $$      | $$  | $$| $$            | $$    $$| $$| $$  | $$| $$  | $$| $$_____/| $$            | $$ \\____  $$      | $$   /$$__  $$| $$| $$| $$_____/| $$  | $$\n" +
						"| $$$$$$$$|  $$$$$$/| $$      |  $$$$$$$      |  $$$$$$/| $$            |  $$$$$$/| $$| $$  | $$|  $$$$$$$|  $$$$$$$| $$            | $$ /$$$$$$$/      | $$  |  $$$$$$$| $$| $$|  $$$$$$$| $$  | $$\n" +
						"|________/ \\______/ |__/       \\_______/       \\______/ |__/             \\______/ |__/|__/  |__/ \\_______/ \\_______/|__/            |__/|_______/       |__/   \\_______/|__/|__/ \\_______/|__/  |__/"
				;
			}
		}

		return result;
	}

	/**
	 *
	 * @param actor The actor performing the action.
	 * @return string of attack action in the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target + " at " + direction;
	}
}