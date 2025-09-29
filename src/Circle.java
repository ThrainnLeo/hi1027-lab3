public class Circle extends Shape {

    private double diameter;

    public Circle() {
        super();
        diameter = 10;
    }

    public Circle(int x, int y, double diameter) {
        super(x, y);
        this.diameter = diameter;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        if (diameter < 0) {
            diameter = 0;
        }
        this.diameter = diameter;
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.setStroke(getColor());
        gc.strokeOval(getX(), getY(), diameter, diameter);
    }

    @Override
    public void constrain(double boxX, double boxY, double boxWidth, double boxHeight) {
        if (getX() < boxX || getX() + diameter > boxX + boxWidth) {
            setVelocity(-getDx(), getDy());
        }
        if (getY() < boxY || getY() + diameter > boxY + boxHeight) {
            setVelocity(getDx(), -getDy());
        }
    }

    @Override
    public String toString() {
        String info = "Circle: " + super.toString();
        info += ", Diameter = " + diameter;
        return info;
    }
}