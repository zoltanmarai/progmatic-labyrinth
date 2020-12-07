package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.List;

public class RandomPlayerImpl implements Player {
    public RandomPlayerImpl() {
    }

    @Override
    public Direction nextMove(Labyrinth l) {
        List<Direction> possibleMoves = l.possibleMoves();
        int element = (int) (Math.random()* possibleMoves.size());
        return possibleMoves.get(element);
    }
}
