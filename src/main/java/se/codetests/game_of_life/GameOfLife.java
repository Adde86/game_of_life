package se.codetests.game_of_life;

import se.codetests.models.Board;
import se.codetests.models.Cell;
import se.codetests.views.GamePanel;
import se.codetests.views.GameView;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameOfLife {

    private final GameConfig gameConfig;
    private GameView gameView;
    private Board board;
    private GamePanel panel;
    private TimerTask timerTask;
    private boolean running = false;

    public GameOfLife(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        this.board = new Board(populateCellArray(createCells(), gameConfig.getCellSize()));
        this.panel = new GamePanel(this.board);
        gameView = new GameView(board, gameConfig, panel);
        setup();
    }

    public void setup() {


        Cell[][] cells = this.board.getCells();
        setupPanelMouseListener(this.panel, cells);
        setupGameViewKeyListener(this.gameView);

    }

    public void setupGameViewKeyListener(GameView gameView) {

        if (gameView.getKeyListeners().length == 0) {
            gameView.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent keyEvent) {

                }

                @Override
                public void keyPressed(KeyEvent keyEvent) {

                    if (keyEvent.getKeyCode() == 32) {
                        running = !running;

                        if (running) {
                            run();
                        } else {
                            timerTask.cancel();
                        }
                    }
                    if (keyEvent.getKeyCode() == 27) {
                        if (timerTask != null) {
                            timerTask.cancel();
                        }
                        int close = JOptionPane.showConfirmDialog(gameView, "Do you want to exit the game?", "Exit?", JOptionPane.YES_NO_OPTION);
                        if (close == 0) {
                            System.exit(1);
                        }

                    }

                    if (keyEvent.getKeyChar() == 'r') {
                        if (timerTask != null) {
                            timerTask.cancel();
                        }
                        running = false;
                        reset();
                    }
                }

                @Override
                public void keyReleased(KeyEvent keyEvent) {

                }
            });
        }
    }

    public void setupPanelMouseListener(JPanel panel, Cell[][] cells) {

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int x = mouseEvent.getX() / gameConfig.getCellSize();
                int y = mouseEvent.getY() / gameConfig.getCellSize();
                cells[x][y].setAlive(!cells[x][y].isAlive());
                gameView.update(gameView.getGraphics());
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    public void reset() {
        this.board = new Board(populateCellArray(createCells(), gameConfig.getCellSize()));
        this.panel.setBoard(board);
        this.panel.update(this.panel.getGraphics());
        setup();
    }

    public Cell[][] createCells() {
        int boardSize = gameConfig.getBoardSize();
        return new Cell[boardSize][boardSize];
    }

    public Cell[][] populateCellArray(Cell[][] cells, int cellSize) {
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {
                Cell cell = new Cell(cellSize, cellSize, x, y);
                cells[x][y] = cell;
            }
        }
        return cells;
    }

    public void run() {

        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                board = updateBoard();
                updatePanel(board);
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, gameConfig.getTicTime(), gameConfig.getTicTime());

    }

    public Board updateBoard() {
        return new Board(updateCellStatuses());
    }


    public void updatePanel(Board board) {
        this.getPanel().setBoard(board);
        this.getPanel().update(panel.getGraphics());
    }

    public Cell[][] updateCellStatuses() {
        Cell[][] updatedCells = createCells();

        Cell[][] cells = board.getCells();

        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {
                Cell cell = new Cell(gameConfig.getCellSize(), gameConfig.getCellSize(), x, y);
                cell.setAlive(isCellAliveAfterUpdate(x, y));
                updatedCells[x][y] = cell;
            }
        }
        return updatedCells;
    }

    public boolean isCellAliveAfterUpdate(int cellX, int cellY) {

        Cell[][] cells = board.getCells();
        Cell cell = cells[cellX][cellY];

        List<Cell> neighbours = getCellNeighbours(cellX, cellY);

        if (cell.isAlive()) {
            return shouldCellSurvive(neighbours);
        } else {
            return shouldCellRevive(neighbours);
        }


    }

    public boolean shouldCellRevive(List<Cell> neighbours) {
        int nrOfAliveNeighbours = (int) neighbours.stream().filter(Cell::isAlive).count();

        return nrOfAliveNeighbours == 3;
    }

    public boolean shouldCellSurvive(List<Cell> neighbours) {
        int nrOfAliveNeighbours = (int) neighbours.stream().filter(Cell::isAlive).count();

        return nrOfAliveNeighbours >= 2 && nrOfAliveNeighbours <= 3;
    }

    public List<Cell> getCellNeighbours(int cellX, int cellY) {

        Cell[][] cells = board.getCells();
        List<Cell> neighbours = new ArrayList<>();
        try {
            for (int x = cellX - 1; x < cellX + 2; x++) {
                for (int y = cellY - 1; y < cellY + 2; y++) {
                    if (!(x == cellX && y == cellY) && !outOfBounds(x, y, cells)) {
                        neighbours.add(cells[x][y]);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        return neighbours;
    }

    public boolean outOfBounds(int x, int y, Cell[][] cells) {
        return x < 0 || y < 0 || x >= cells.length || y >= cells.length;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public GameView getGameView() {
        return gameView;
    }

    public Board getBoard() {
        return board;
    }

    public GamePanel getPanel() {
        return panel;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public TimerTask getTimerTask() {
        return timerTask;
    }

    public void setTimerTask(TimerTask timerTask) {
        this.timerTask = timerTask;
    }
}
