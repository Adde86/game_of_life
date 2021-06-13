package se.codetests.game_of_life;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.codetests.models.Board;
import se.codetests.models.Cell;
import se.codetests.views.GamePanel;
import se.codetests.views.GameView;


import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {
    private GameConfig gameConfig;
    private GameOfLife gameOfLife;

    @BeforeEach
    void setUp() {
        this.gameConfig = new GameConfig(60, 10, 1000);
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
        for (int i = 0; i < cells.length; i++) {
            for (int y = 0; y < cells.length; y++) {
                assertNotNull(cells[i][y]);
                nrOfCells++;
            }
        }
        assertEquals(gameConfig.getBoardSize() * gameConfig.getBoardSize(), nrOfCells);
    }

    @Test
    void run() {


    }


    @Test
    void setup() {
        MouseListener[] listeners = gameOfLife.getPanel().getMouseListeners();
        KeyListener[] keyListeners = gameOfLife.getGameView().getKeyListeners();
        gameOfLife.setup();
        assertEquals(keyListeners.length, gameOfLife.getGameView().getKeyListeners().length);
        assertEquals(listeners.length + 1, gameOfLife.getPanel().getMouseListeners().length);

    }

    @Test
    void setupGameViewKeyListenerFirstSetup() {
        GameView newGameView = new GameView(gameOfLife.getBoard(), gameConfig, gameOfLife.getPanel());
        int before = newGameView.getKeyListeners().length;
        gameOfLife.setupGameViewKeyListener(newGameView);
        assertEquals(before + 1, newGameView.getKeyListeners().length);
    }

    @Test
    void setGameViewKeyListenerAfterInitialSetup() {
        int before = gameOfLife.getGameView().getKeyListeners().length;

        gameOfLife.setupGameViewKeyListener(gameOfLife.getGameView());

        assertEquals(before, gameOfLife.getGameView().getKeyListeners().length);
    }

    @Test
    void spacePressed() {

        Cell[][] cells = gameOfLife.getBoard().getCells();

        cells[5][5].setAlive(true);
        boolean before = gameOfLife.isRunning();
        KeyEvent keyEvent = new KeyEvent(gameOfLife.getGameView(), 0, 0, 0, 32, ' ');
        Arrays.stream(gameOfLife.getGameView().getKeyListeners()).forEach(l -> {
            l.keyPressed(keyEvent);
        });
        assertNotEquals(before, gameOfLife.isRunning());
        try {
            Thread.sleep(2000);
            Cell[][] newCells = gameOfLife.getBoard().getCells();
            assertFalse(newCells[5][5].isAlive());
        } catch (InterruptedException e) {

        }

    }

    @Test
    void spacePressedWhileGameRunning() {

        boolean before = gameOfLife.isRunning();
        KeyEvent keyEvent = new KeyEvent(gameOfLife.getGameView(), 0, 0, 0, 32, ' ');
        Arrays.stream(gameOfLife.getGameView().getKeyListeners()).forEach(l -> {
            l.keyPressed(keyEvent);
            l.keyPressed(keyEvent);
        });
        assertEquals(before, gameOfLife.isRunning());

    }

    @Test
    void rPressed() {

        Cell[][] cells = gameOfLife.getBoard().getCells();

        cells[5][5].setAlive(true);
        boolean before = gameOfLife.isRunning();
        KeyEvent keyEvent = new KeyEvent(gameOfLife.getGameView(), 0, 0, 0, 82, 'r');
        Arrays.stream(gameOfLife.getGameView().getKeyListeners()).forEach(l -> {
            l.keyPressed(keyEvent);
        });
        Cell[][] newCells = gameOfLife.getBoard().getCells();
        assertFalse(newCells[5][5].isAlive());
    }


    @Test
    void setupPanelMouseListener() {

        int nrOfListenersBefore = gameOfLife.getPanel().getMouseListeners().length;

        gameOfLife.setupPanelMouseListener(gameOfLife.getPanel(), gameOfLife.getBoard().getCells());

        assertNotEquals(nrOfListenersBefore, gameOfLife.getPanel().getMouseListeners().length);
    }

    @Test
    void mouseClickedOnPanel() {

        MouseEvent mouseEvent = new MouseEvent(gameOfLife.getPanel(), 0, 0, 0, 300, 300, 1, false);
        int cellX = mouseEvent.getX() / gameConfig.getCellSize();
        int cellY = mouseEvent.getY() / gameConfig.getCellSize();
        Cell[][] cells = gameOfLife.getBoard().getCells();
        boolean before = cells[cellX][cellY].isAlive();
        Arrays.stream(gameOfLife.getPanel().getMouseListeners()).forEach(l -> {
            l.mouseClicked(mouseEvent);
        });
        assertNotEquals(before, cells[cellX][cellY].isAlive());
    }

    @Test
    void reset() {

        Cell[][] cells = gameOfLife.getBoard().getCells();
        cells[5][5].setAlive(true);
        cells[5][6].setAlive(true);
        cells[5][7].setAlive(true);
        gameOfLife.reset();
        cells = gameOfLife.getBoard().getCells();
        boolean anyCellAlive = false;

        for (int i = 0; i < cells.length; i++) {
            for (int y = 0; y < cells.length; y++) {
                if (cells[i][y].isAlive()) {
                    anyCellAlive = true;
                }
            }
        }
        assertFalse(anyCellAlive);
    }


    @Test
    void updateBoard() {

        Cell[][] cells = gameOfLife.getBoard().getCells();
        cells[5][6].setAlive(true);
        cells[5][7].setAlive(true);
        cells[5][8].setAlive(true);
        Board board = gameOfLife.updateBoard();
        cells = board.getCells();
        assertTrue(cells[5][7].isAlive());
        assertFalse(cells[5][6].isAlive());
        assertTrue(cells[4][7].isAlive());

    }

    @Test
    void updatePanel() {

        GamePanel panel = gameOfLife.getPanel();
        Board previousBoard = panel.getBoard();
        Board newBoard = gameOfLife.updateBoard();
        gameOfLife.updatePanel(newBoard);
        assertNotEquals(previousBoard, panel.getBoard());

    }

    @Test
    void updateCellStatuses() {
        Cell[][] previousCells = gameOfLife.getBoard().getCells();
        previousCells[5][6].setAlive(true);
        previousCells[5][7].setAlive(true);
        previousCells[5][8].setAlive(true);

        Cell[][] newCells = gameOfLife.updateCellStatuses();
        assertTrue(newCells[5][7].isAlive());
        assertFalse(newCells[5][6].isAlive());
        assertTrue(newCells[4][7].isAlive());
    }

    @Test
    void isCellAliveAfterUpdateNoLivingNeighbours() {
        Cell[][] cells = gameOfLife.getBoard().getCells();
        cells[5][5].setAlive(true);
        assertFalse(gameOfLife.isCellAliveAfterUpdate(5, 5));

    }

    @Test
    void isCellAliveAfterUpdateOneLivingNeighbour() {
        Cell[][] cells = gameOfLife.getBoard().getCells();
        cells[5][5].setAlive(true);
        cells[5][6].setAlive(true);
        assertFalse(gameOfLife.isCellAliveAfterUpdate(5, 5));
    }

    @Test
    void isCellAliveAfterUpdateTwoLivingNeighbours() {
        Cell[][] cells = gameOfLife.getBoard().getCells();
        cells[5][5].setAlive(true);
        cells[5][6].setAlive(true);
        cells[5][4].setAlive(true);
        assertTrue(gameOfLife.isCellAliveAfterUpdate(5, 5));
    }

    @Test
    void isCellAliveAfterUpdateThreeLivingNeighbours() {
        Cell[][] cells = gameOfLife.getBoard().getCells();
        cells[5][5].setAlive(true);
        cells[5][6].setAlive(true);
        cells[5][4].setAlive(true);
        cells[6][5].setAlive(true);
        assertTrue(gameOfLife.isCellAliveAfterUpdate(5, 5));
    }

    @Test
    void isCellAliveAfterUpdateFourLivingNeighbours() {
        Cell[][] cells = gameOfLife.getBoard().getCells();
        cells[5][5].setAlive(true);
        cells[5][6].setAlive(true);
        cells[5][4].setAlive(true);
        cells[6][5].setAlive(true);
        cells[4][5].setAlive(true);
        assertFalse(gameOfLife.isCellAliveAfterUpdate(5, 5));
    }

    @Test
    void shouldCellReviveThreeLivingNeighbours() {

        Cell[][] cells = gameOfLife.getBoard().getCells();
        Cell neighbour1 = cells[5][4];
        neighbour1.setAlive(true);
        Cell neighbour2 = cells[5][6];
        neighbour2.setAlive(true);
        Cell neighbour3 = cells[4][4];
        neighbour3.setAlive(true);
        Cell neighbour4 = cells[4][5];
        neighbour4.setAlive(false);
        Cell neighbour5 = cells[4][6];
        neighbour5.setAlive(false);
        Cell neighbour6 = cells[6][4];
        neighbour6.setAlive(false);
        Cell neighbour7 = cells[6][5];
        neighbour7.setAlive(false);
        Cell neighbour8 = cells[6][6];
        neighbour8.setAlive(false);

        List<Cell> neighbours = List.of(neighbour1, neighbour2, neighbour3, neighbour4, neighbour5, neighbour6, neighbour7, neighbour8);
        assertTrue(gameOfLife.shouldCellRevive(neighbours));

    }

    @Test
    void shouldCellReviveTwoLivingNeighbours() {

        Cell[][] cells = gameOfLife.getBoard().getCells();
        Cell neighbour1 = cells[5][4];
        neighbour1.setAlive(true);
        Cell neighbour2 = cells[5][6];
        neighbour2.setAlive(true);
        Cell neighbour3 = cells[4][4];
        neighbour3.setAlive(false);
        Cell neighbour4 = cells[4][5];
        neighbour4.setAlive(false);
        Cell neighbour5 = cells[4][6];
        neighbour5.setAlive(false);
        Cell neighbour6 = cells[6][4];
        neighbour6.setAlive(false);
        Cell neighbour7 = cells[6][5];
        neighbour7.setAlive(false);
        Cell neighbour8 = cells[6][6];
        neighbour8.setAlive(false);

        List<Cell> neighbours = List.of(neighbour1, neighbour2, neighbour3, neighbour4, neighbour5, neighbour6, neighbour7, neighbour8);
        assertFalse(gameOfLife.shouldCellRevive(neighbours));
    }

    @Test
    void shouldCellReviveFourLivingNeighbours() {

        Cell[][] cells = gameOfLife.getBoard().getCells();
        Cell neighbour1 = cells[5][4];
        neighbour1.setAlive(true);
        Cell neighbour2 = cells[5][6];
        neighbour2.setAlive(true);
        Cell neighbour3 = cells[4][4];
        neighbour3.setAlive(true);
        Cell neighbour4 = cells[4][5];
        neighbour4.setAlive(true);
        Cell neighbour5 = cells[4][6];
        neighbour5.setAlive(false);
        Cell neighbour6 = cells[6][4];
        neighbour6.setAlive(false);
        Cell neighbour7 = cells[6][5];
        neighbour7.setAlive(false);
        Cell neighbour8 = cells[6][6];
        neighbour8.setAlive(false);

        List<Cell> neighbours = List.of(neighbour1, neighbour2, neighbour3, neighbour4, neighbour5, neighbour6, neighbour7, neighbour8);
        assertFalse(gameOfLife.shouldCellRevive(neighbours));
    }

    @Test
    void shouldCellSurviveThreeLivingNeighbours() {

        Cell[][] cells = gameOfLife.getBoard().getCells();
        Cell neighbour1 = cells[5][4];
        neighbour1.setAlive(true);
        Cell neighbour2 = cells[5][6];
        neighbour2.setAlive(true);
        Cell neighbour3 = cells[4][4];
        neighbour3.setAlive(true);
        Cell neighbour4 = cells[4][5];
        neighbour4.setAlive(false);
        Cell neighbour5 = cells[4][6];
        neighbour5.setAlive(false);
        Cell neighbour6 = cells[6][4];
        neighbour6.setAlive(false);
        Cell neighbour7 = cells[6][5];
        neighbour7.setAlive(false);
        Cell neighbour8 = cells[6][6];
        neighbour8.setAlive(false);

        List<Cell> neighbours = List.of(neighbour1, neighbour2, neighbour3, neighbour4, neighbour5, neighbour6, neighbour7, neighbour8);
        assertTrue(gameOfLife.shouldCellSurvive(neighbours));
    }

    @Test
    void shouldCellSurviveTwoLivingNeighbours() {

        Cell[][] cells = gameOfLife.getBoard().getCells();
        Cell neighbour1 = cells[5][4];
        neighbour1.setAlive(true);
        Cell neighbour2 = cells[5][6];
        neighbour2.setAlive(true);
        Cell neighbour3 = cells[4][4];
        neighbour3.setAlive(false);
        Cell neighbour4 = cells[4][5];
        neighbour4.setAlive(false);
        Cell neighbour5 = cells[4][6];
        neighbour5.setAlive(false);
        Cell neighbour6 = cells[6][4];
        neighbour6.setAlive(false);
        Cell neighbour7 = cells[6][5];
        neighbour7.setAlive(false);
        Cell neighbour8 = cells[6][6];
        neighbour8.setAlive(false);

        List<Cell> neighbours = List.of(neighbour1, neighbour2, neighbour3, neighbour4, neighbour5, neighbour6, neighbour7, neighbour8);
        assertTrue(gameOfLife.shouldCellSurvive(neighbours));
    }

    @Test
    void shouldCellSurviveOneLivingNeighbours() {

        Cell[][] cells = gameOfLife.getBoard().getCells();
        Cell neighbour1 = cells[5][4];
        neighbour1.setAlive(true);
        Cell neighbour2 = cells[5][6];
        neighbour2.setAlive(false);
        Cell neighbour3 = cells[4][4];
        neighbour3.setAlive(false);
        Cell neighbour4 = cells[4][5];
        neighbour4.setAlive(false);
        Cell neighbour5 = cells[4][6];
        neighbour5.setAlive(false);
        Cell neighbour6 = cells[6][4];
        neighbour6.setAlive(false);
        Cell neighbour7 = cells[6][5];
        neighbour7.setAlive(false);
        Cell neighbour8 = cells[6][6];
        neighbour8.setAlive(false);

        List<Cell> neighbours = List.of(neighbour1, neighbour2, neighbour3, neighbour4, neighbour5, neighbour6, neighbour7, neighbour8);
        assertFalse(gameOfLife.shouldCellSurvive(neighbours));
    }

    @Test
    void shouldCellSurviveFourLivingNeighbours() {

        Cell[][] cells = gameOfLife.getBoard().getCells();
        Cell neighbour1 = cells[5][4];
        neighbour1.setAlive(true);
        Cell neighbour2 = cells[5][6];
        neighbour2.setAlive(true);
        Cell neighbour3 = cells[4][4];
        neighbour3.setAlive(true);
        Cell neighbour4 = cells[4][5];
        neighbour4.setAlive(true);
        Cell neighbour5 = cells[4][6];
        neighbour5.setAlive(false);
        Cell neighbour6 = cells[6][4];
        neighbour6.setAlive(false);
        Cell neighbour7 = cells[6][5];
        neighbour7.setAlive(false);
        Cell neighbour8 = cells[6][6];
        neighbour8.setAlive(false);

        List<Cell> neighbours = List.of(neighbour1, neighbour2, neighbour3, neighbour4, neighbour5, neighbour6, neighbour7, neighbour8);
        assertFalse(gameOfLife.shouldCellSurvive(neighbours));
    }

    @Test
    void getCellNeighboursCornerCell() {

        List<Cell> cells = gameOfLife.getCellNeighbours(0, 0);
        assertEquals(3, cells.size());
    }

    @Test
    void getCellNeighboursSideCell() {

        List<Cell> cells = gameOfLife.getCellNeighbours(0, 5);
        assertEquals(5, cells.size());
    }

    @Test
    void getCellNeighboursCell() {

        List<Cell> cells = gameOfLife.getCellNeighbours(5, 5);
        assertEquals(8, cells.size());
    }


}