package game.terrains;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;

public class FogDoor extends Ground {
    Location locationToTransfer; // location that is connected by fog door
    String mapName; // name of the map of the location

    public FogDoor(Location locationToTransfer, String mapName) {
        super('=');
        this.locationToTransfer = locationToTransfer;
        this.mapName = mapName;
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(Abilities.ENTER_DOOR);
    }

    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        if (actor.hasCapability(Abilities.ENTER_DOOR)) {
            String moveString = " to " + mapName;
            return new Actions(new MoveActorAction(locationToTransfer,moveString));
        }
        return new Actions();
    }
}
