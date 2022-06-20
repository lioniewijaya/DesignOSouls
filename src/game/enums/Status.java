package game.enums;

/**
 * Use this enum class to give `buff` or `debuff`.
 * It is also useful to give a `state` to abilities or actions that can be attached-detached.
 */
public enum Status {
    HOSTILE_TO_ENEMY, // use this to be hostile towards something (e.g., to be attacked by enemy)
    HOSTILE_TO_PLAYER, // use this to be hostile towards something (e.g., to be attacked by player)
    WIPED_OUT, // use this to remove instance of actor
    REPOSITIONED, // use this to reposition actor during reset
    STUNNED, // use this to stun actor
    WEAK_TO_STORMRULER, // use this for actor weak to Storm Ruler
    IS_LIT, // use this for bonfire activation status
    TRANSFORMED, // use this for transformable actor (e.g. mimic)
}
