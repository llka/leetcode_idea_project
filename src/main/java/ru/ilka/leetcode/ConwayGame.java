package ru.ilka.leetcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

public class ConwayGame {
    private static final int ALIVE_STATE_VALUE = 1;
    private static final int MORTAL_STATE_VALUE = 0;

    private static final int NEIGHBOURS_TO_BECOME_ALIVE = 3;
    private static final int NEIGHBOURS_TO_STAY_ALIVE_MIN = 2;
    private static final int NEIGHBOURS_TO_STAY_ALIVE_MAX = 3;
    private final List<Coordinate> neighboursVectors = List.of(
            new Coordinate(-1, -1),
            new Coordinate(-1, 0),
            new Coordinate(-1, 1),

            new Coordinate(0, 1),
            new Coordinate(1, 1),
            new Coordinate(1, 0),

            new Coordinate(1, -1),
            new Coordinate(0, -1)
    );
    private int[][] area;
    private int sizeI;
    private int sizeJ;

    public ConwayGame(int[][] area) {
        this.area = area;
        this.sizeI = area.length;
        if (sizeI != 0) {
            this.sizeJ = area[0].length;
        }
    }

    public void executeIteration() {
        update();
        printArea();
    }

    public void update() {
        List<Coordinate> fieldsWhichChangeState = new ArrayList<>();

        for (int i = 0; i < this.sizeI; i++) {
            for (int j = 0; j < this.area[i].length; j++) {
                Coordinate current = new Coordinate(i, j);
                if (checkIfStateChanges(current)) {
                    fieldsWhichChangeState.add(current);
                }
            }
        }

        fieldsWhichChangeState.forEach(coordinate -> changeFieldState(coordinate));
    }

    public void printArea() {
        for (int i = 0; i < this.sizeI; i++) {
            for (int j = 0; j < this.area[i].length; j++) {
                System.out.print(area[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    private boolean checkIfStateChanges(Coordinate field) {
        if (area.length == 0) {
            return false;
        }

        boolean isCurrentlyAlive = isAlive(field);

        int aliveNeighbourCounter = 0;

        for (Coordinate neighbourVector : neighboursVectors) {
            if (isValidNeighbour(field, neighbourVector)) {
                Coordinate neighbour = new Coordinate(field.i + neighbourVector.i, field.j + neighbourVector.j);
                if (isAlive(neighbour)) {
                    aliveNeighbourCounter++;
                }
            }
        }

        if (isCurrentlyAlive) {
            if (aliveNeighbourCounter >= NEIGHBOURS_TO_STAY_ALIVE_MIN && aliveNeighbourCounter <= NEIGHBOURS_TO_STAY_ALIVE_MAX) {
                return false;
            } else {
                return true;
            }
        } else {
            return aliveNeighbourCounter == NEIGHBOURS_TO_BECOME_ALIVE;
        }
    }

    private boolean isValidNeighbour(Coordinate field, Coordinate neighbour) {
        if (area.length == 0) {
            return false;
        }
        int i = field.i + neighbour.i;
        int j = field.j + neighbour.j;

        return i >= 0 && i < area.length
                && j >= 0 && j < area[0].length;
    }

    private boolean isAlive(Coordinate coordinate) {
        return area[coordinate.i][coordinate.j] == ALIVE_STATE_VALUE;
    }

    private void changeFieldState(Coordinate coordinate) {
        if (isAlive(coordinate)) {
            area[coordinate.i][coordinate.j] = MORTAL_STATE_VALUE;
        } else {
            area[coordinate.i][coordinate.j] = ALIVE_STATE_VALUE;
        }
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Coordinate {
        int i;
        int j;
    }


}
