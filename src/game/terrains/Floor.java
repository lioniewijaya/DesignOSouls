package game.terrains;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import game.enums.Abilities;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {

	public Floor() {
		super('_');
	}

	/**
	 *
	 * @param actor the Actor to check
	 * @return true if actor has ENTER_FLOOR abilities, false otherwise
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return actor.hasCapability(Abilities.ENTER_FLOOR);
	}

}
