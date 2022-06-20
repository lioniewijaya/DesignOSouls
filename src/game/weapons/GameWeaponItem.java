package game.weapons;

import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.FollowBehaviour;
import game.enemies.Enemy;
import game.enums.Status;
import game.interfaces.Resettable;

import java.util.ArrayList;
import java.util.List;

/**
 *  GameWeaponItem is weapon that is used for range attack. range should be specified for usage and enemy that is
 *  holding the weapon can attack and follow from the specified range
 */
public abstract class GameWeaponItem extends WeaponItem implements Resettable {
    private int range; // range of the weapon
    private Actor currentHolder; // instance of actor holding the weapon
    private Enemy enemyHolder; // instance of enemy holding the weapon
    private Location holderLocation; // location of the holder
    private ArrayList<Actor> target = new ArrayList<Actor>(); // actor targeted by the weapon

    /**
     * Constructor if weapon is not hold
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
     */
    public GameWeaponItem(String name, char displayChar, int damage, String verb, int hitRate, int range) {
        super(name, displayChar, damage, verb, hitRate);
        this.range = range;
        this.registerInstance();
    }

    /**
     * Constructor if weapon is hold by any actor that is not enemy
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
     * @param actor non enemy actor holding the weapon
     */
    public GameWeaponItem(String name, char displayChar, int damage, String verb, int hitRate, int range, Actor actor) {
        super(name, displayChar, damage, verb, hitRate);
        this.range = range;
        this.currentHolder = actor;
        this.registerInstance();
    }

    /**
     * Constructor if weapon is hold by enemy
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
     * @param enemy enemy actor holding the weapon
     */
    public GameWeaponItem(String name, char displayChar, int damage, String verb, int hitRate, int range, Enemy enemy) {
        super(name, displayChar, damage, verb, hitRate);
        this.range = range;
        this.enemyHolder = enemy;
        this.registerInstance();
    }

    /**
     * In this game, weapon cannot be dropped
     * @param actor an actor that will interact with this item
     * @return null
     */
    @Override
    public DropItemAction getDropAction(Actor actor) {
        return null;
    }

    /**
     * Called once per turn, updates current holder and holder's location.
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);
        currentHolder = actor;
        holderLocation = currentLocation;
    }

    /**
     * List of actions allowed for weapon
     * @return list of actions that can be performed by weapon
     */
    @Override
    public List<Action> getAllowableActions() {
        allowableActions.clear();
        // Weapon is hold by an actor
        if (holderLocation != null) {
            // Detect target
            ArrayList<Location> targetLocations = targetLocations(holderLocation);

            // Target detected
            if (targetLocations.size() != 0) {
                // Update to enable following behaviour for enemy holder
                if (target.size() == 0) {
                    for (Location targetLocation: targetLocations) {
                        target.add(targetLocation.getActor());
                    }
                }
                // Add attack if no wall detected
                for (Location targetLocation: targetLocations) {
                    AttackAction attack = attackTarget(holderLocation, targetLocation);
                    if (attack != null) {
                        allowableActions.add(attack);
                    }
                }
            }

            // Follow target if holder is an enemy, has previously detected target, and cannot attack target for now
            if (allowableActions.size() == 0 && target.size() != 0 && currentHolder == enemyHolder) {
                enemyHolder.addBehaviour(new FollowBehaviour(target.get(0)));
            }
        }

        return super.getAllowableActions();
    }

    /**
     * Return list of targets' location which can be attacked by holder in range
     * @return list of targets' location
     */
    private ArrayList<Location> targetLocations(Location currentLocation) {
        ArrayList<Location> locations = new ArrayList<Location>();
        for (int x = currentLocation.x() - range; x <= currentLocation.x() + range; x++) {
            for (int y = currentLocation.y() - range; y <= currentLocation.y() + range; y++) {
                Location location = currentLocation.map().at(x, y);
                if (location.containsAnActor() ) {
                    if ((location.getActor().hasCapability(Status.HOSTILE_TO_ENEMY) && currentHolder.hasCapability(Status.HOSTILE_TO_PLAYER)) || (location.getActor().hasCapability(Status.HOSTILE_TO_PLAYER) && currentHolder.hasCapability(Status.HOSTILE_TO_ENEMY))) {
                        locations.add(location);
                    }
                }
            }
        }
        return locations;
    }

    /**
     * Return an attack action for a detected target if not blocked by wall
     * @return attack action if not blocked or null
     */
    private AttackAction attackTarget(Location currentLocation, Location targetLocation) {
        if (!(blockedByWall(currentLocation,targetLocation))) {
            return new AttackAction(targetLocation.getActor(), "");
        }
        return null;
    }

    /**
     * Check if a wall is between two locations
     * @return boolean indicating if path is blocked by wall
     */
    private boolean blockedByWall(Location currentLocation, Location targetLocation) {
        boolean gotWall = false;

        // Check if target is in the same row or column as holder
        NumberRange xs, ys;
        if (currentLocation.x() == targetLocation.x() || currentLocation.y() == targetLocation.y()) {
            xs = new NumberRange(Math.min(currentLocation.x(), targetLocation.x()), Math.abs(currentLocation.x() - targetLocation.x()) + 1);
            ys = new NumberRange(Math.min(currentLocation.y(), targetLocation.y()), Math.abs(currentLocation.y() - targetLocation.y()) + 1);
            for (int xx : xs) {
                for (int yy : ys) {
                    if (currentLocation.map().at(xx, yy).getGround().blocksThrownObjects())
                        gotWall = true;
                }
            }
        }
        // Check if target is diagonal from holder
        else if (Math.abs(currentLocation.x()-targetLocation.x()) == Math.abs(currentLocation.y()-targetLocation.y())) {
            for (int i = 1; i <= Math.abs(currentLocation.x()-targetLocation.x()); i++) {
                if (currentLocation.map().at(currentLocation.x()+i, currentLocation.y()+i).getGround().blocksThrownObjects())
                    gotWall = true;
            }
        }
        return gotWall;
    }

    /**
     * Reset weapon to let holder stop following target
     */
    @Override
    public void resetInstance() {
        target.clear();
    }

    /**
     * A weapon is permanent
     */
    @Override
    public boolean isExist() {
        return true;
    }
}
