package game.enemies;

import edu.monash.fit2099.engine.Location;

/**
 * Abstract class that represents Lord of Cinder (bosses of Design o' Souls) in the game
 */
public abstract class LordOfCinder extends ResettableEnemy {
    protected boolean enteredSecondPhase = false; // indicator if Lord Of Cinder has entered second phase

    /**
     * Constructor.
     *
     * @param name            the name of the enemy
     * @param displayChar     the character that will represent the enemy in the display
     * @param hitPoints       the enemy's starting hit points
     * @param souls           the soul that is given if enemy is killed
     * @param initialLocation initial location when game is started
     */
    public LordOfCinder(String name, char displayChar, int hitPoints, int souls, Location initialLocation) {
        super(name, displayChar, hitPoints, souls, initialLocation);
    }

    /**
     * Set Lord of Cinder to enter second phase of fight
     */
    public void secondPhase() {
        enteredSecondPhase = true;
    }

    /**
     * Reset Lord of Cinder to not enter second phase
     */
    @Override
    public void resetInstance() {
        super.resetInstance();
        enteredSecondPhase = false;
    }
}