package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.PickUpItemAction;
import game.interfaces.Soul;

/**
 * Token containing number of souls transferred when Player dies, adds number of souls to player and removed from map if picked up
 */
public class TokenOfSoul extends PortableItem implements Soul {
    private int souls = 0; // current soul in token

    /**
     * constructor
     */
    public TokenOfSoul() {
        super("Token of Soul", '$');
    }

    /**
     * Transfer soul
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
     * @return true if soul is added
     */
    @Override
    public boolean addSouls(int souls) {
        this.souls += souls;
        return true;
    }

    /**
     * Subtract soul
     * @param souls number souls to be deducted
     * @return true if soul is subtracted
     */
    @Override
    public boolean subtractSouls(int souls) {
        return false;
    }

    /**
     * Action to allow TokenOfSoul to be pick up
     * @param actor an actor that will interact with this item
     * @return action to pick up TokenOfSoul
     */
    @Override
    public PickUpItemAction getPickUpAction(Actor actor) {
        return new PickUpTokenAction(this);
    }
}
