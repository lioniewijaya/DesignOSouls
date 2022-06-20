package game.interfaces;

import edu.monash.fit2099.engine.GameMap;

/**
 * A contract for repositionable instance (in other words, object/instance that is moved back to initial location when constructed).
 */
public interface Repositionable {

    /**
     * Reposition a repositionable.
     * @param map a map where repositionable is located.
     */
    void reposition(GameMap map);
}
