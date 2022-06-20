package game.enums;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

import java.awt.*;

/**
 * Enum that represents the coordinate for each direction.
 */
public enum Direction {
    NORTH(0,-1),
    NORTHEAST(1,-1),
    EAST(1,0),
    SOUTHEAST(1,1),
    SOUTH(0,1),
    SOUTHWEST(-1,1),
    WEST(-1,0),
    NORTHWEST(-1,-1);

    private Point move;

    Direction(int i, int j) {
        move = new Point(i,j);
    }

    private Point getMove() {
        return move;
    }

    /**
     * Get last location before the selected direction from hotkey
     * @param hotkey a character inputted by user representing a move
     * @param map the map the actor is on.
     * @param currentPosition current position of an actor after move from hotkey is executed
     * @return last location before hotkey is selected
     */
    public static Location lastPosition(String hotkey, GameMap map, Location currentPosition) {
        Point moveNeeded;
        switch (hotkey) {
            case "1":
                moveNeeded = NORTHEAST.getMove();
                break;
            case "2":
                moveNeeded = NORTH.getMove();
                break;
            case "3":
                moveNeeded = NORTHWEST.getMove();
                break;
            case "4":
                moveNeeded = EAST.getMove();
                break;
            case "6":
                moveNeeded = WEST.getMove();
                break;
            case "7":
                moveNeeded = SOUTHEAST.getMove();
                break;
            case "8":
                moveNeeded = SOUTH.getMove();
                break;
            case "9":
                moveNeeded = SOUTHWEST.getMove();
                break;
            default:
                moveNeeded = new Point(0,0);
                break;
        }
        return map.at(currentPosition.x()+moveNeeded.x, currentPosition.y()+moveNeeded.y);
    }
}

