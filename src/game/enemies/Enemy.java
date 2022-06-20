package game.enemies;

import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.FollowBehaviour;
import game.Player;
import game.RepositionActorAction;
import game.enums.Status;
import game.interfaces.Behaviour;
import game.interfaces.Resettable;
import game.interfaces.Soul;

import java.util.ArrayList;
import java.util.Random;

/**
 *  Abstract base class that represents enemy in the game
 *  When adjacent to player, enemy follows player until death/reset
 */
public abstract class Enemy extends Actor implements Resettable, Soul {
    private ArrayList<Behaviour> behaviours = new ArrayList<>(); // behaviours of an enemy
    private int soul; // souls to dropped when enemy is killed
    private FollowBehaviour followBehaviour = null; // following behaviour for enemy

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param souls       the number of souls hold by Actor
     */
    public Enemy(String name, char displayChar, int hitPoints, int souls) {
        super(name, displayChar, hitPoints);
        this.soul = souls;
        this.registerInstance();
        this.addCapability(Status.HOSTILE_TO_PLAYER);
    }

    /**
     * Add behaviour to Enemy
     * @param behaviour behaviour to add
     */
    public void addBehaviour(Behaviour behaviour) {
        behaviours.add(0,behaviour);
    }

    /**
     * Get the behaviours for enemy
     * @return the arraylist containing behaviours
     */
    public ArrayList<Behaviour> getBehaviours() {
        return behaviours;
    }

    /**
     * Get allowable actions that can be performed by Enemy
     * @param otherActor the Actor that is adjacent to enemy
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     * @see Status#HOSTILE_TO_ENEMY
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();

        // following behaviour when player is adjacent
        if(otherActor instanceof Player) {
            if (followBehaviour == null) {
                followBehaviour = new FollowBehaviour(otherActor);
                addBehaviour(followBehaviour);
            }
        }

        // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY) && this.hasCapability(Status.HOSTILE_TO_PLAYER)) {
            actions.add(new AttackAction(this,direction));
        }

        // get weapon action
        Weapon weapon = otherActor.getWeapon();
        WeaponAction weaponAction = weapon.getActiveSkill(this, direction);
        if(weaponAction != null){
            actions.add(weaponAction);
        }

        return actions;
    }

    /**
     * Transfer soul
     * @param soulObject a target souls.
     */
    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(this.soul);
    }

    /**
     * Enemy's description
     * @return string describing enemy
     */
    @Override
    public String toString() {
        String returnString = super.toString() + " HP: " + this.hitPoints + "/" + this.maxHitPoints + " Weapon: ";
        if (this.getWeapon() instanceof IntrinsicWeapon) {
            returnString += "(no weapon)";
        }
        else {
            returnString += this.getWeapon();
        }
        return returnString;
    }

    /**
     * Remove following behaviour
     */
    public void removeFollowBehaviour() {
        this.followBehaviour = null;
    }

    /**
     * Get a random attack or weapon action from list of actions allowable
     * @param actions list of actions
     * @return attack or weapon action to be performed by enemy
     */
    private Action getAttackOrWeaponAction(Actions actions) {
        Actions filteredActions = new Actions();
        // loop through available actions and get all attack or weapon actions
        for(Action action: actions) {
            if (action instanceof AttackAction || action instanceof WeaponAction) {
                filteredActions.add(action);
            }
        }
        if (filteredActions.size() > 0) {
            return filteredActions.get(new Random().nextInt(filteredActions.size()));
        }
        return null;
    }

    /**
     * Get an action based on behaviour prioritized
     * @param map current GameMap
     * @return behaviour based action to be performed by enemy
     */
    private Action getBehaviourAction(GameMap map) {
        for(Behaviour Behaviour : getBehaviours()) {
            Action action = Behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return null;
    }

    /**
     * Action to be performed by enemy
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return action to be performed by enemy
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // Return an attack or weapon action is available
        Action attackOrWeaponAction = getAttackOrWeaponAction(actions);
        if (attackOrWeaponAction != null) {
            return attackOrWeaponAction;
        }

        // Return a behaviour based action is available
        Action behaviourAction = getBehaviourAction(map);
        if (behaviourAction != null) {
            return behaviourAction;
        }

        return new DoNothingAction();
    }
}
