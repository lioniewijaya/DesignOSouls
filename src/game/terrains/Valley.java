package game.terrains;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.enums.Abilities;

/**
 * The gorge or endless gap that is dangerous for the Player.
 */
public class Valley extends Ground {

	/**
	 * constructor
	 */
	public Valley() {
		super('+');
	}

	/**
	 *
	 * @param actor the Actor to check
	 * @return false or actor cannot enter.
	 */
	@Override
	public boolean canActorEnter(Actor actor){
		return actor.hasCapability(Abilities.FALL);
	}

	/**
	 * Check if an actor falls into valley and kills them if true
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);

		// Player stepped into valley and got killed
		if (location.containsAnActor()) {
			location.getActor().hurt(Integer.MAX_VALUE);
		}
	}
}
