package inheritance.movable_interface;

public abstract class Shape implements Movable {

    private int x, y; // position

    protected Shape() {
        x = 0;
        y = 0;
    }

    protected Shape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x < 0) {
            x = 0;
        }
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y < 0) {
            y = 0;
        }
        this.y = y;
    }

    public abstract void draw();

    @Override
    public String toString() {
        return "(x,y) = (" + x + "," + y + ")";
    }
}