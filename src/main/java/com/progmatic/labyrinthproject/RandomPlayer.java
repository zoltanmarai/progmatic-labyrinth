package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;
import java.util.List;
import java.util.Random;

/**
 *
 * @author pappgergely
 */
public class RandomPlayer implements Player {

    private static final Random RANDOM_GENERATOR = new Random();
    
    @Override
    public Direction nextMove(Labyrinth l) {
        List<Direction> myMoves = l.possibleMoves();
        return myMoves.get( RANDOM_GENERATOR.nextInt(myMoves.size()) );
    }

}
