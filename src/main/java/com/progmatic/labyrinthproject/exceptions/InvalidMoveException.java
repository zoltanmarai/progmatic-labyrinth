package com.progmatic.labyrinthproject.exceptions;

/**
 *
 * @author pappgergely
 */
public class InvalidMoveException extends Exception {
    private final int row;

    private final int column;

    private final String message;
    public InvalidMoveException (int row, int col, String message) {
        this.row = row;
        this.column = col;
        this.message = message;
    }
    @Override
    public String getMessage() {
        return "Problem with the labyrinth cell with coordinates: (" + row + ", " + column + "). The problem is: " + message;
    }
}
