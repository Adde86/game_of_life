package se.codetests.models;

public class Board {

    private Cell[][] cells;

    public Board(Cell[][] cells){
        this.cells = cells;

    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }
}
