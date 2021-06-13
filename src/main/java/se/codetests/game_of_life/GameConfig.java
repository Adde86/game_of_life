package se.codetests.game_of_life;

public class GameConfig {

    private int boardSize;
    private int cellSize;
    private long ticTime;

    public GameConfig(int boardSize, int cellSize, long ticTime) {
        this.boardSize = boardSize;
        this.cellSize = cellSize;
        this.ticTime = ticTime;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public long getTicTime() {
        return ticTime;
    }

    public void setTicTime(long ticTime) {
        this.ticTime = ticTime;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }
}
