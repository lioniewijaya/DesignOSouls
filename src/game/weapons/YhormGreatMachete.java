package game.weapons;

/**
 * YhormGreatMachete as one of the weapons in the game
 * Passive skill: Rage Mode / Ember Form (increases success hit rate by 30% when holder is in second phase)
 */
public class YhormGreatMachete extends MeleeWeapon {
    private boolean secondPhase = false; // indicator that YhormTheGiant entered second phase

    /**
     * constructor
     */
    public YhormGreatMachete() {
        super("YhormGreatMachete", 'M', 95, "hits", 60);
    }

    /**
     * Chance of hit rate, is increased by 30% when second phase is activated (Passive skill: Rage Mode / Ember Mode)
     * @return hitRate
     */
    @Override
    public int chanceToHit() {
        if (secondPhase) {
            return super.chanceToHit()+30;
        }
        return super.chanceToHit();
    }

    /**
     * Setter for the second phase, when second phase is activated chance of hit rate increases by 30%
     * @param secondPhase
     */
    public void setSecondPhase(boolean secondPhase) {
        this.secondPhase = secondPhase;
    }
}
