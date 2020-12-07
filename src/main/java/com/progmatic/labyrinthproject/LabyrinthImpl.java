package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {
    private Map<Coordinate, CellType> lab = new HashMap<>();
    private int height;
    private int width;
    private Coordinate playerCoordinate = new Coordinate(0, 0);
    private Labyrinth labyrinth;

    public LabyrinthImpl() {

    }

    @Override
    public int getWidth() {
        if (this.width != 0) {
            return this.width;
        }
        return -1;
    }

    @Override
    public int getHeight() {
        if (this.height != 0) {
            return this.height;
        }
        return -1;
    }

    @Override
    public void loadLabyrinthFile(String fileName) {

        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            int height = Integer.parseInt(sc.nextLine());
            labyrinth.setSize(width, height);

            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    Coordinate coordinate = new Coordinate(hh, ww);
                    switch (line.charAt(ww)) {
                        case 'W':
                            setCellType(coordinate, CellType.WALL);
                            break;
                        case 'E':
                            setCellType(coordinate, CellType.END);
                            break;
                        case 'S':
                            setCellType(coordinate, CellType.START);
                            playerCoordinate = coordinate;
                            break;
                        case ' ':
                            setCellType(coordinate, CellType.EMPTY);
                            break;
                    }
                }
            }
        } catch (FileNotFoundException | NumberFormatException | CellException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        if(lab.containsKey(c)) {
            return lab.get(c);
        } else{
            throw new CellException(c.getRow(),c.getCol(),"Hibás cella adatok!");
        }
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        if(!lab.containsKey(c)) {
            lab.put(c, type);
        } else{
            throw new CellException(c.getRow(),c.getCol(),"A cella már létezik!");
        }

    }

    @Override
    public Coordinate getPlayerPosition() {
        return playerCoordinate;
    }

    @Override
    public boolean hasPlayerFinished() {
        if (lab.get(getPlayerPosition()) == CellType.END) {
            return true;
        }
        return false;
    }

    @Override
    public List<Direction> possibleMoves() { // lehetséges irányok
        List<Direction> directionList = new ArrayList<>();
        int x = playerCoordinate.getCol();
        int y = playerCoordinate.getRow();

        if (x <= labyrinth.getHeight()) {
            directionList.add(Direction.EAST);
        }
        if (x >= 0) {
            directionList.add(Direction.WEST);
        }
        if (y >= 0) {
            directionList.add(Direction.SOUTH);
        }
        if (y <= labyrinth.getWidth()) {
            directionList.add(Direction.NORTH);
        }

        return directionList;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        int x = playerCoordinate.getCol();
        int y = playerCoordinate.getRow();
        switch (direction){
            case EAST:
                Coordinate c= new Coordinate( x+1,y);
                if (lab.get(c)== CellType.WALL){
                   throw new InvalidMoveException(x+1,y,"Wall at East");
                } else {
                    playerCoordinate = c;
                }
                break;
            case WEST:
                c= new Coordinate( x-1,y);
                if (lab.get(c)== CellType.WALL){
                    throw new InvalidMoveException(x-1,y,"Wall at West");
                } else {
                    playerCoordinate = c;
                }
                break;
            case SOUTH:
                c= new Coordinate( x,y+1);
                if (lab.get(c)== CellType.WALL){
                    throw new InvalidMoveException( x,y+1,"Wall at South");
                } else {
                    playerCoordinate = c;
                }
                break;
            case NORTH:
                c= new Coordinate( x,y-1);
                if (lab.get(c)== CellType.WALL){
                    throw new InvalidMoveException(x,y-1,"Wall at North");
                } else {
                    playerCoordinate = c;
                }
                break;
        }

    }



}
