package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.Transformable;

/**
 * An action to transform a transformable instance.
 */
public class TransformAction extends Action {
    private Transformable transformable; // something that can be transformed

    /**
     * constructor
     * @param transformable a transformable to transform
     */
    public TransformAction(Transformable transformable) {
        this.transformable = transformable;
    }

    /**
     An instance of a transformable is transformed
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string to indicate transformation result
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        transformable.transform();
        return transformable.transformResultString();
    }

    @Override
    public String menuDescription(Actor actor) {
        return transformable.menuDescription();
    }
}
