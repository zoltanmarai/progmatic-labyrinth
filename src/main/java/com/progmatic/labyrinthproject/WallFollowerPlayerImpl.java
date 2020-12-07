package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.List;

public class WallFollowerPlayerImpl implements Player {
    public WallFollowerPlayerImpl() {
    }

    @Override
    public Direction nextMove(Labyrinth l) throws CellException {
        int element = 0 ;
        List<Direction> possibleMoves = l.possibleMoves();
        if(possibleMoves.size()>1){


                }


        return possibleMoves.get(element);
    }
}
