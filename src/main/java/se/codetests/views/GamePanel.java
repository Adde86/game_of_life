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

    private void paintBoard(Board board, Graphics g){
        Cell[][] cells = board.getCells();

        for(int x = 0; x < cells.length; x++){
            for(int y = 0; y < cells.length; y++){
                if(cells[x][y].isAlive()){
                    paintLivingCell(g, cells[x][y]);
                }else {
                    paintDeadCell(g, cells[x][y]);
                }

            }
        }

    }


    private void paintLivingCell(Graphics g, Cell cell){

        g.setColor(Color.black);
        g.fillRect(cell.getX(), cell.getY(), cell.getWidth(), cell.getHeight());
    }

    private void paintDeadCell(Graphics g, Cell cell){

        g.setColor(Color.white);
        g.fillRect(cell.getX(), cell.getY(), cell.getWidth(), cell.getHeight());
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
