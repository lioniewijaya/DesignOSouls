package game.bonfires;

import edu.monash.fit2099.engine.Location;
import game.Player;
import game.bonfires.Bonfire;
import game.enums.Status;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A manager to handle bonfires in the game
 */
public class BonfireManager {
    private HashMap<Bonfire,Location> bonfiresInGame = new HashMap(); // hashmap containing bonfire and their respective location
    private Player player; // player in game that uses bonfire

    /**
     * constructor
     * @param player a player who possibly interacts with bonfires in game
     */
    public BonfireManager(Player player) {
        this.player = player;
    }

    /**
     * Add an instance of bonfire and location to the manager
     *
     * @param bonfire bonfire to be added
     * @param location location of bonfire
     */
    public void addBonfire(Bonfire bonfire, Location location) {
        bonfiresInGame.put(bonfire, location);
        // If no bonfire is set in game yet, this bonfire is set by default as bonfire to interact with player
        if (player.getLastBonfireInteractedLocation() == null) {
            player.setLastBonfireInteractedLocation(getBonfireLocation(bonfire));
        }
    }

    /**
     * Return a list of active bonfires in game aside of given bonfire
     *
     * @param bonfire bonfire to exclude
     * @return active bonfires in game
     */
    public ArrayList<Bonfire> getOtherActiveBonfiresInGame(Bonfire bonfire) {
        ArrayList<Bonfire> bonfires = new ArrayList<>();
        for (Bonfire otherBonfire : bonfiresInGame.keySet()) {
            if (otherBonfire.hasCapability(Status.IS_LIT) && otherBonfire != bonfire) {
                bonfires.add(otherBonfire);
            }
        }
        return bonfires;
    }

    /**
     * Get location of a particular bonfire
     *
     * @return location of a bonfire
     */
    public Location getBonfireLocation(Bonfire bonfire) {
        return bonfiresInGame.get(bonfire);
    }

    /**
     * Set player's last bonfire interacted location
     *
     * @param lastBonfireInteracted last bonfire interacted
     */
    public void setLastBonfireInteracted(Bonfire lastBonfireInteracted) {
        player.setLastBonfireInteractedLocation(getBonfireLocation(lastBonfireInteracted));
    }
}
