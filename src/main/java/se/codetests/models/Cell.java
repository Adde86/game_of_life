package se.codetests.models;

public class Cell {
    private boolean alive = false;
    private int width;
    private int height;
    private int x;
    private int y;

    public Cell(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX(){
        if(this.x == 0){
            return 0;
        }else {
            return this.x * this.width;
        }
    }

    public int getY(){
        if(this.y == 0){
            return 0;
        }else {
            return this.y * this.height;
        }
    }
}
