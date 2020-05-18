package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author pappgergely
 */
public class ConsciousPlayer implements Player {

    private final Stack<Direction> myCalculatedMoves;
    private final int[][] ONE_MOVES = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    public ConsciousPlayer() {
        this.myCalculatedMoves = new Stack<>();
    }
    
    @Override
    public Direction nextMove(Labyrinth l) {
        
        if (myCalculatedMoves.isEmpty()) {
            int[][] reach = new int[l.getWidth()][l.getHeight()];
            
            Coordinate start = l.getPlayerPosition();
            Coordinate end = l.getPlayerPosition();
            
            for (int i = 0; i < reach.length; i++) {
                for (int j = 0; j < reach[i].length; j++) {
                    try {
                        CellType ct = l.getCellType(new Coordinate(i, j));
                        reach[i][j] = ct.equals(CellType.WALL) ? -2 : -1;
                        if (ct.equals(CellType.END)) {
                            end = new Coordinate(i, j);
                        }
                    } catch (CellException ex) {}
                }
            }
            
            Queue<Coordinate> myPossibleMoves = new LinkedList<>();
            myPossibleMoves.add(start);
            reach[start.getCol()][start.getRow()] = 0;
            while (! myPossibleMoves.peek().equals(end)) {
                Coordinate head = myPossibleMoves.poll();
                for (int[] m : ONE_MOVES) {
                    try {
                        Coordinate newCoord = new Coordinate(head.getCol() + m[0], head.getRow() + m[1]);
                        l.getCellType(newCoord);
                        if (reach[head.getCol() + m[0]][head.getRow() + m[1]] == -1) {
                            reach[head.getCol() + m[0]][head.getRow() + m[1]] = reach[head.getCol()][head.getRow()] + 1;
                            myPossibleMoves.add(newCoord);
                        }
                    } catch (CellException e) {}
                }
            }
            
            int posCol = end.getCol(), posRow = end.getRow();
            while (posCol != start.getCol() || posRow != start.getRow()) {
                for (int[] m : ONE_MOVES) {
                    if (reach[posCol][posRow] - 1 == reach[posCol+m[0]][posRow+m[1]]) {
                        // thinking backwards...
                        if (m[1] == -1) {
                            myCalculatedMoves.push(Direction.SOUTH);
                        } else if (m[1] == 1) {
                            myCalculatedMoves.push(Direction.NORTH);
                        } else if (m[0] == -1) {
                            myCalculatedMoves.push(Direction.EAST);
                        } else if (m[0] == 1) {
                            myCalculatedMoves.push(Direction.WEST);
                        }
                        posCol += m[0];
                        posRow += m[1];
                        break;
                    }
                }
            }
        }
        
        return myCalculatedMoves.pop();
    }

}