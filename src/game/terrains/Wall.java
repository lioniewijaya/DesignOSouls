package game.terrains;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;

/**
 * A class that represent wall
 */
public class Wall extends Ground {

	/**
	 * constructor
	 */
	public Wall() {
		super('#');
	}

	/**
	 * Actor cannot enter the wall
	 * @param actor the Actor to check
	 * @return false as actor cannot enter
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return false;
	}

	/**
	 * implement terrain that blocks thrown objects but not movement
	 * @return true
	 */
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}
}
