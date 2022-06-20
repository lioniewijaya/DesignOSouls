package game.enums;

/**
 * Enum that represents an ability of Actor, Item, or Ground.
 */
public enum Abilities {
    REST, // use this to indicate if an actor can rest at Bonfire
    FALL, // use this to indicate if an actor can fall into Valley
    ENTER_FLOOR, // use this to indicate if an actor can enter Floor area
    ENTER_DOOR, // use this to indicate if an actor can go through Door
    CHARGE, // Storm Ruler's active skill
    WINDSLASH // Storm Ruler's active skill
}
