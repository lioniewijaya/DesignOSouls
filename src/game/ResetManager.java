package game;

import game.interfaces.Resettable;

import java.util.ArrayList;
import java.util.List;

/**
 * A global Singleton manager that does soft-reset on the instances.
 */
public class ResetManager {
    /**
     * A list of resettable instances (any classes that implements Resettable,
     * such as Player implements Resettable will be stored in here)
     */
    private List<Resettable> resettableList;

    /**
     * A singleton reset manager instance
     */
    private static ResetManager instance;

    /**
     * Get the singleton instance of reset manager
     * @return ResetManager singleton instance
     */
    public static ResetManager getInstance(){
        if(instance == null){
            instance = new ResetManager();
        }
        return instance;
    }

    /**
     * Constructor
     */
    private ResetManager(){
        resettableList = new ArrayList<>();
    }

    /**
     * Reset the game by traversing through all the list
     * By doing this way, it will avoid using `instanceof` all over the place.
     *
     */
    public void run(){
        for (Resettable resettable: this.resettableList) {
            resettable.resetInstance();
        }
        cleanUp();
    }

    /**
     * Add the Resettable instance to the list
     * @param resettable the interface instance
     */
    public void appendResetInstance(Resettable resettable){
        this.resettableList.add(resettable);
    }

    /**
     * clean up instances (actor, item, or ground) that doesn't exist anymore in the map
     */
    private void cleanUp(){
        ArrayList<Resettable> removedInstances = new ArrayList();
        for (Resettable resettable: this.resettableList) {
            if (!resettable.isExist()) {
                removedInstances.add(resettable);
            }
        }
        for (Resettable resettable: removedInstances) {
            this.resettableList.remove(resettable);
        }
    }
}
