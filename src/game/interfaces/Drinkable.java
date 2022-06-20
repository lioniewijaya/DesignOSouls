package game.interfaces;

/**
 * A contract for drinkable instance (in other words, object/instance that can be drinked by a consumer).
 */
public interface Drinkable {

    /**
     * Let a consumer drink the drinkable.
     * @param consumer instance that consumes the drinkable.
     */
    void drink(Consumer consumer);
}
