package se.codetests.game_of_life;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.codetests.models.Board;
import se.codetests.models.Cell;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {
    private GameConfig gameConfig;
    private GameOfLife gameOfLife;


    @BeforeEach
    void setUp() {
        this.gameConfig = new GameConfig(60,10, 1000);
        gameOfLife = new GameOfLife(this.gameConfig);
    }

    @Test
    void createCells() {
        assertEquals(60, gameOfLife.createCells().length);
    }

    @Test
    void populateCellArray() {
        Cell[][] cells = gameOfLife.populateCellArray(gameOfLife.createCells(), this.gameConfig.getCellSize());

        int nrOfCells = 0;
        for(int i = 0; i < cells.length; i++){
            for(int y = 0; y < cells.length; y++){
                assertNotNull(cells[i][y]);
                nrOfCells++;
            }
        }
        assertEquals(gameConfig.getBoardSize() * gameConfig.getBoardSize(), nrOfCells);
    }

    @Test
    void run() {
        System.out.println(gameOfLife.getBoard());
        gameOfLife.run();
        System.out.println(gameOfLife.getBoard());



    }

    @Test
    void updateCellStatuses() {
    }

    @Test
    void isCellAliveAfterUpdate() {
    }

    @Test
    void shouldCellRevive() {
    }

    @Test
    void shouldCellSurvive() {
    }

    @Test
    void getCellNeighbours() {
    }
}