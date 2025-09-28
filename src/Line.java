public class Line {
    private double x2;
    private double y2;

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }
    
    public void setX2(double x2) {
        this.x2 = x2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }


    @Override
    public String toString() {
        return "Line{" + "x2=" + x2 + ", y2=" + y2 + '}';
    }
}
