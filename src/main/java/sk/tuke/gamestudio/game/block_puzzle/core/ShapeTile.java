package sk.tuke.gamestudio.game.block_puzzle.core;

public class ShapeTile {
    private int x;
    private int y;

    public ShapeTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
