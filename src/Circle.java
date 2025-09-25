public class Circle extends Shape {

    private int radius;

    public Circle() {
        super();
        radius = 10;
    }

    public Circle(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        if (radius < 0) {
            radius = 0;
        }
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("Faking draw...");
        System.out.println(this.toString());
    }

    @Override
    public void move(int dx, int dy) {
        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);
    }

    @Override
    public String toString() {
        String info = "Circle: " + super.toString();
        info += ", radius = " + radius;
        return info;
    }
}