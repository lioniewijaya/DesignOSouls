package game.interfaces;

/**
 * A contract for transformable instance (in other words, object/instance that can transform into some form).
 */
public interface Transformable {

    /**
     * Transform a transformable.
     * @return a boolean indicating if transform is successful
     */
    boolean transform();

    /**
     * Description of transformation result
     * @return a string representing transformation result
     */
    String transformResultString();

    /**
     * Menu description to be shown in console to transform an instance.
     * @return a string representing menu description.
     */
    String menuDescription();
}
