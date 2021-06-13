package se.codetests.views;

import se.codetests.models.Board;
import se.codetests.models.Cell;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private Board board;

    public GamePanel(Board board) {
        this.board = board;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintBoard(this.board, g);

    }

    public void paintBoard(Board board, Graphics g) {
        Cell[][] cells = board.getCells();

        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {
                paintCell(g, cells[x][y]);
            }
        }

    }

    public void paintCell(Graphics g, Cell cell) {
        Color color = cell.isAlive() ? Color.black : Color.white;
        g.setColor(color);
        g.fillRect(cell.getX(), cell.getY(), cell.getWidth(), cell.getHeight());
    }


    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
