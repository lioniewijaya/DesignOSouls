package game.enemies;

import edu.monash.fit2099.engine.*;
import game.RepositionActorAction;
import game.TokenOfSoul;
import game.TransformAction;
import game.enums.Status;
import game.interfaces.Transformable;

import java.util.Random;

/**
 * A mimic/chest.
 * All mimic are initially a chest represented as by an '?' which can be opened.
 * Have 50% chance of turning into a mimic with 100 hit points, give 200 souls to Player if killed by an attack,
 * and do not carry any weapon, and drops 1 to 3 token of souls worth 100 souls when killed.
 * Have another 50% chance of instantly dropping 1 to 3 token of souls worth of 100 souls.
 */
public class Mimic extends ResettableEnemy implements Transformable {

    /**
     * Constructor.
     * @param name the name of this mimic
     */
    public Mimic(String name, Location initialLocation) {
        super(name, '?', 100, 200, initialLocation);
        this.removeCapability(Status.HOSTILE_TO_PLAYER);

        // Add token to inventory
        int tokenToDrop = new Random().nextInt(3)+1;
        for (int i=1; i <= tokenToDrop; i++) {
            TokenOfSoul token = new TokenOfSoul();
            token.addSouls(100);
            this.addItemToInventory(token);
        }
    }

    @Override
    public Weapon getWeapon() {
        return new IntrinsicWeapon(55,"kicks");
    }

    /**
     * Action to be performed by mimic
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return action to be performed by mimic
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // Repositioned when game is reset
        if (this.hasCapability(Status.REPOSITIONED)) {
            this.removeCapability(Status.REPOSITIONED);
            return new RepositionActorAction(this);
        }

        // A chest does nothing
        if (!this.hasCapability(Status.TRANSFORMED)) {
            return new DoNothingAction();
        }

        // Get attack or behaviour action from enemy super class
        return super.playTurn(actions,lastAction,map,display);
    }

    /**
     * Get allowable actions that can be performed by Chest/Mimic
     * @param otherActor the Actor that is adjacent to Chest/Mimic
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     * @see Status#HOSTILE_TO_ENEMY
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = super.getAllowableActions(otherActor, direction, map);

        // If player is adjacent and chest is not transformed yet
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY) && !this.hasCapability(Status.TRANSFORMED)) {
            actions.add(new TransformAction(this));
        }

        return actions;
    }

    /**
     * Reset hit points, position, following behaviour, and transform back to chest
     */
    @Override
    public void resetInstance() {
        super.resetInstance();
        this.name = "Chest";
        this.displayChar = '?';
        this.removeCapability(Status.TRANSFORMED);
    }

    /**
     * Transform chest to mimic.
     * @return a boolean indicating if transform is successful
     */
    @Override
    public boolean transform() {
        // Successful transform into a mimic
        if (new Random().nextInt(2)==0) {
            this.name = "Mimic";
            this.displayChar = 'M';
            this.addCapability(Status.TRANSFORMED);
            this.addCapability(Status.HOSTILE_TO_PLAYER);
            return true;
        }
        else {
            Actions dropActions = new Actions();
            for (Item item : this.getInventory()) {
                dropActions.add(item.getDropAction(this));
            }
            for (Action drop : dropActions) {
                drop.execute(this, this.getInitialLocation().map());
            }
            this.getInitialLocation().map().removeActor(this);
            return false;
        }
    }

    /**
     * Description of transformation result
     * @return a string representing transformation result
     */
    @Override
    public String transformResultString() {
        if (this.hasCapability(Status.TRANSFORMED)) {
            return "Chest transformed into mimic";
        }
        return "Chest is opened";
    }

    /**
     * Menu description to be shown in console to transform an instance.
     * @return a string representing menu description.
     */
    @Override
    public String menuDescription() {
        return "Open the chest";
    }

    /**
     * Mimic/chest's description
     * @return string describing mimic/chest
     */
    @Override
    public String toString() {
        if (this.hasCapability(Status.TRANSFORMED)) {
            return super.toString();
        }
        return this.name;
    }
}
