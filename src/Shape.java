import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {

    public static final double BILLION = 1_000_000_000.0;
    private double x, y;
    private double dx, dy;
    private Color color;

    protected Shape() {
        this.x = 0;
        this.y = 0;
        this.dx = 0;
        this.dy = 0;
        this.color = Color.BLACK;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getDx() {
        return dx;
    }
    public double getDy() {
        return dy;
    }
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void moveAndConstrain(long dtNanos) {
        move(dtNanos);
    }

    protected void move(long dtNanos) {
        double dt = dtNanos / BILLION;
        x += dx * dt;
        y += dy * dt;
    }

    //public Color getColor() { return color; }
    //public void setColor(Color c) { this.color = c; }



    protected abstract void constrain(double left, double top, double right, double bottom);

    public abstract void paint(GraphicsContext gc);


    @Override
    public String toString() {
        return "(x,y) = (" + x + "," + y + ")";
    }
}