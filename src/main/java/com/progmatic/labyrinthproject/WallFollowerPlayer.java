package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;
import java.util.List;

/**
 * This implementation always turns right if he has more than one possible moves.
 * @author pappgergely
 */
public class WallFollowerPlayer implements Player {

    Direction currentDirection;
    
    @Override
    public Direction nextMove(Labyrinth l) {
        List<Direction> myMoves = l.possibleMoves();
        if (currentDirection == null || myMoves.size() == 1) {
            currentDirection = myMoves.get(0);
        } else {
            // We have more than one possible moves
            switch (currentDirection) {
                case NORTH:
                    if (myMoves.contains(Direction.EAST)) {
                        currentDirection = Direction.EAST;
                    } else if (myMoves.contains(Direction.NORTH)) {
                        // do nothing, continue
                    } else {
                        currentDirection = Direction.WEST;
                    }
                    break;
                case EAST:
                    if (myMoves.contains(Direction.SOUTH)) {
                        currentDirection = Direction.SOUTH;
                    } else if (myMoves.contains(Direction.EAST)) {
                        // do nothing, continue
                    } else {
                        currentDirection = Direction.NORTH;
                    }
                    break;
                case SOUTH:
                    if (myMoves.contains(Direction.WEST)) {
                        currentDirection = Direction.WEST;
                    } else if (myMoves.contains(Direction.SOUTH)) {
                        // do nothing, continue
                    } else {
                        currentDirection = Direction.EAST;
                    }
                    break;
                case WEST:
                    if (myMoves.contains(Direction.NORTH)) {
                        currentDirection = Direction.NORTH;
                    } else if (myMoves.contains(Direction.WEST)) {
                        // do nothing, continue
                    } else {
                        currentDirection = Direction.SOUTH;
                    }
                    break;
            }
        }
        return currentDirection;
    }

}
