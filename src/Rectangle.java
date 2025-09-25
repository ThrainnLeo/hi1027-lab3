public class Rectangle extends Shape {

    private int x2, y2;

    public Rectangle() {
        super();
        x2 = 0;
        y2 = 0;
    }

    public Rectangle(int x, int y, int x2, int y2) {
        super(x, y);
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
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
        this.setX2(this.getX2() + dy);
        this.setY2(this.getY2() + dy);
    }

    @Override
    public String toString() {
        String info = "Rectangle: " + super.toString();
        info += ", (x2,y2) = (" + x2 + "," + y2 + ")";
        return info;
    }
}