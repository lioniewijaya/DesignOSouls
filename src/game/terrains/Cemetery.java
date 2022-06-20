package game.terrains;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.enemies.Undead;

import java.util.Random;

/**
 * A terrain in the map that is capable of spawning undeads.
 * Each cemetery has 25% success rate to spawn an undead at every turn.
 */
public class Cemetery extends Ground {
    /**
     * Constructor.
     */
    public Cemetery() {
        super('C');
    }

    /**
     * Check if Actor are allowed to enter Cemetery
     * @param actor the Actor to check
     * @return false as Actor is not allowed to enter
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * Place an Undead on the cemetery if Undead object is created for every turn
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);

        // Spawn undead
        Undead spawnedUndead = spawn();
        if (spawnedUndead != null) {
            location.addActor(spawnedUndead);
        }
    }

    /**
     * Spawns an undead with 25% success rate.
     * @return an undead or null
     */
    private Undead spawn() {
        // Return undead with probability of 25%
        if (new Random().nextInt(4)==0) {
            return new Undead("Hollow soldier");
        }
        return null;
    }
}
