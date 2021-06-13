package se.codetests.views;

import se.codetests.game_of_life.GameConfig;
import se.codetests.models.Board;

import javax.swing.*;
import java.awt.*;


public class GameView extends JFrame {

    private Board board;
    private final GameConfig gameConfig;
    private final GamePanel panel;

    public GameView(Board board, GameConfig gameConfig, GamePanel panel) {
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

    private void configureFrame() {
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        int windowSize = this.board.getCells().length * gameConfig.getCellSize();
        this.setSize(new Dimension(windowSize, windowSize));
        this.add(panel);


    }


}
