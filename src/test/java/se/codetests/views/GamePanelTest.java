package se.codetests.views;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.codetests.game_of_life.GameConfig;
import se.codetests.game_of_life.GameOfLife;
import se.codetests.models.Cell;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GamePanelTest {

    private GameConfig gameConfig;
    private GameOfLife gameOfLife;
    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        this.gameConfig = new GameConfig(60, 10, 1000);
        this.gameOfLife = new GameOfLife(this.gameConfig);
        this.gamePanel = gameOfLife.getPanel();
    }

    @Test
    void paintLivingCell() {
        Cell[][] cells = gameOfLife.getBoard().getCells();
        Cell cell = cells[5][5];
        cell.setAlive(true);

        gamePanel.paintCell(gamePanel.getGraphics(), cell);
        assertEquals(Color.black, gamePanel.getGraphics().getColor());

    }

}