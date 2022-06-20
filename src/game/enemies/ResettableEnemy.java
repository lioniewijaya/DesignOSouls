package game.enemies;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.FollowBehaviour;
import game.enemies.Enemy;
import game.enums.Status;
import game.interfaces.Behaviour;
import game.interfaces.Repositionable;

import java.util.ArrayList;

/**
 *  Abstract class that represents resettable enemy (enemy that is repositioned and not wiped out when reset is performed) in the game
 */
public abstract class ResettableEnemy extends Enemy implements Repositionable {
    private Location initialLocation; // initial location an enemy is placed

    /**
     * Constructor.
     *
     * @param name            the name of the enemy
     * @param displayChar     the character that will represent the enemy in the display
     * @param hitPoints       the enemy's starting hit points
     * @param souls           the soul that is given if enemy is killed
     * @param initialLocation initial location when game is started
     */
    public ResettableEnemy(String name, char displayChar, int hitPoints, int souls, Location initialLocation) {
        super(name, displayChar, hitPoints, souls);
        this.initialLocation = initialLocation;
    }

    /**
     * Reset hit points, position, and following behaviour
     */
    @Override
    public void resetInstance() {
        this.hitPoints = this.maxHitPoints;
        this.addCapability(Status.REPOSITIONED);
        ArrayList<Behaviour> removedFollowing = new ArrayList<Behaviour>();
        for (Behaviour behaviour: this.getBehaviours()) {
            if (behaviour instanceof FollowBehaviour) {
                this.removeFollowBehaviour();
                removedFollowing.add(behaviour);
            }
        }
        for (Behaviour behaviour: removedFollowing) {
            getBehaviours().remove(behaviour);
        }
    }

    /**
     * @return true
     */
    @Override
    public boolean isExist() {
        return true;
    }

    /**
     * Reposition Enemy to initial location
     * @param map map where enemy is located
     */
    @Override
    public void reposition(GameMap map) {
        map.moveActor(this,initialLocation);
    }

    /**
     * Getter method to get initial Location
     * @return initial location
     */
    public Location getInitialLocation() {
        return initialLocation;
    }
}