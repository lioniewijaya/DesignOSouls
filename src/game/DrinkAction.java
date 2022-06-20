package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.Consumer;
import game.interfaces.Drinkable;

/**
 *  An action that allows a consumer to drink drinkable
 */
public class DrinkAction extends Action {
    private Drinkable drinkable; // a drink
    private Consumer consumer; // an actor that drinks

    /**
     * constructor
     * @param drinkable a drink
     * @param consumer a consumer
     */
    public DrinkAction(Drinkable drinkable, Consumer consumer) {
        this.drinkable = drinkable;
        this.consumer = consumer;
    }

    /**
     * Perform drink action where consumer drinks drinkable
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string of drink action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        drinkable.drink(consumer);
        return consumer + " drinked " + drinkable;
    }

    @Override
    public String menuDescription(Actor actor) {
        return consumer + " drinks " + drinkable;
    }
}
