package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    private int w, h;
    private CellType[][] cells;
    Coordinate playerPosition;

    public LabyrinthImpl() {
        this.w = -1;
        this.h = -1;
        this.playerPosition = new Coordinate(-1, -1);
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        if (c.getRow() < 0 || c.getRow() >= h) {
            throw new CellException(c, "Invalid cell");
        }
        if (c.getCol() < 0 || c.getCol() >= w) {
            throw new CellException(c, "Invalid cell");
        }
        return cells[c.getCol()][c.getRow()];
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        getCellType(c);
        cells[c.getCol()][c.getRow()] = type;
        if (type.equals(CellType.START)) {
            playerPosition = c;
        }
    }

    @Override
    public boolean hasPlayerFinished() {
        return cells[playerPosition.getCol()][playerPosition.getRow()].equals(CellType.END);
    }

    @Override
    public Coordinate getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public List<Direction> possibleMoves() {
        List<Direction> possibleMoves = new ArrayList<>();
        possibleMoves.addAll(Arrays.asList(Direction.values()));

        try {
            Coordinate cellToNorth = new Coordinate(playerPosition.getCol(), playerPosition.getRow() - 1);
            if (playerPosition.getRow() == 0 || getCellType(cellToNorth).equals(CellType.WALL)) {
                possibleMoves.remove(Direction.NORTH);
            }
        } catch (CellException ex) {
            possibleMoves.remove(Direction.NORTH);
        }
        try {
            Coordinate cellToSouth = new Coordinate(playerPosition.getCol(), playerPosition.getRow() + 1);
            if (playerPosition.getRow() == h - 1 || getCellType(cellToSouth).equals(CellType.WALL)) {
                possibleMoves.remove(Direction.SOUTH);
            }
        } catch (CellException ex) {
            possibleMoves.remove(Direction.SOUTH);
        }
        try {
            Coordinate cellToWest = new Coordinate(playerPosition.getCol() - 1, playerPosition.getRow());
            if (playerPosition.getCol() == 0 || getCellType(cellToWest).equals(CellType.WALL)) {
                possibleMoves.remove(Direction.WEST);
            }
        } catch (CellException ex) {
            possibleMoves.remove(Direction.WEST);
        }
        try {
            Coordinate cellToEast = new Coordinate(playerPosition.getCol() + 1, playerPosition.getRow());
            if (playerPosition.getCol() == w - 1 || getCellType(cellToEast).equals(CellType.WALL)) {
                possibleMoves.remove(Direction.EAST);
            }
        } catch (CellException ex) {
            possibleMoves.remove(Direction.EAST);
        }

        return possibleMoves;
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            setSize(Integer.parseInt(sc.nextLine()), Integer.parseInt(sc.nextLine()));

            for (int hh = 0; hh < h; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < w; ww++) {
                    switch (line.charAt(ww)) {
                        case 'W':
                            cells[ww][hh] = CellType.WALL;
                            break;
                        case 'E':
                            cells[ww][hh] = CellType.END;
                            break;
                        case 'S':
                            cells[ww][hh] = CellType.START;
                            playerPosition = new Coordinate(ww, hh);
                            break;
                    }
                }
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public void setSize(int width, int height) {
        w = width;
        h = height;

        cells = new CellType[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                cells[i][j] = CellType.EMPTY;
            }
        }
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        Coordinate newPosition = null;
        switch (direction) {
            case NORTH:
                newPosition = new Coordinate(playerPosition.getCol(), playerPosition.getRow() - 1);
                break;
            case SOUTH:
                newPosition = new Coordinate(playerPosition.getCol(), playerPosition.getRow() + 1);
                break;
            case EAST:
                newPosition = new Coordinate(playerPosition.getCol() + 1, playerPosition.getRow());
                break;
            case WEST:
                newPosition = new Coordinate(playerPosition.getCol() - 1, playerPosition.getRow());
                break;
        }
        try {
            if (getCellType(newPosition).equals(CellType.WALL)) {
                throw new InvalidMoveException();
            }
        } catch (CellException ex) {
            throw new InvalidMoveException();
        }
        playerPosition = newPosition;
    }
}
