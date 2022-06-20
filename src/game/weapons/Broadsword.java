package game.weapons;



/**
 * Broadsword as one of the weapons in the game.
 * Passive skill: Critical Strike (20% chance of double damage)
 */
public class Broadsword extends MeleeWeapon {

    /**
     * Constructor
     */
    public Broadsword() {
        super("Broadsword", '7', 30, "hits", 80);
    }


}
