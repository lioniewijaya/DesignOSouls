package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Item;
import game.interfaces.Consumer;
import game.interfaces.Drinkable;
import game.interfaces.Resettable;

import java.util.List;

/**
 * A unique health potion that heals the Player with 40% of the maximum hit points
 */
public class EstusFlask extends Item implements Resettable, Drinkable {
    private int maxCharge = 3; // maximum charge of the Estus Flasks
    private int currentCharge = maxCharge; // current charge of the Estus Flasks
    private DrinkAction drinkAction; // a drinking action
    private boolean isDrinkActionAdded = false; // an indicator to check if drink action has been added
    private Actor toBeDrinkedBy; // an actor that drinks

    /***
     * Constructor.
     * @param name the name of Estus Flask
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param player the player who is holding the Estus Flask
     */
    public EstusFlask(String name, char displayChar, Player player) {
        super(name, displayChar, false);
        drinkAction = new DrinkAction(this,player);
        this.toBeDrinkedBy = player;
        this.registerInstance();
    }

    /**
     * Increase Player's HP by 40% of maxHP
     * @param consumer
     */
    public void drink(Consumer consumer) {
        if (currentCharge > 0) {
            toBeDrinkedBy.heal((int) (0.4 * consumer.getMaxHp()));
            currentCharge -= 1;
        }
    }

    /**
     * Get action list that contains drinking action if currentCharge > 0
     * @return list of Actions
     */
    @Override
    public List<Action> getAllowableActions() {
        if (currentCharge > 0) {
            if (!isDrinkActionAdded) {
                allowableActions.add(drinkAction);
                isDrinkActionAdded = true;
            }
        }
        else {
            allowableActions.remove(drinkAction);
            isDrinkActionAdded = false;
        }
        return super.getAllowableActions();
    }

    /**
     * Reset the charges of Estus Flask
     */
    @Override
    public void resetInstance() {
        currentCharge = maxCharge;
    }

    /**
     * @return true if Estus Flask exist
     */
    @Override
    public boolean isExist() {
        return true;
    }

    /**
     * Display the number of charges in the menu
     * @return string of the number of charges of Estus Flask
     */
    @Override
    public String toString() {
        return super.toString() + " (" + currentCharge + "/" + maxCharge + ")";
    }
}
