package se.codetests.views;

import se.codetests.game_of_life.GameConfig;
import se.codetests.models.Board;
import se.codetests.models.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class GameView extends JFrame {

    private Board board;
    private GameConfig gameConfig;
    private GamePanel panel;

    public GameView(Board board, GameConfig gameConfig, GamePanel panel){
        this.board = board;
        this.gameConfig = gameConfig;
        this.panel = panel;
        configureFrame();



    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;

    }

    private void configureFrame(){
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(new Dimension(600,600));

        this.add(panel);

    }


}
