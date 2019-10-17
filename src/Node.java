import java.util.Objects;

public class Node {
    private int x;
    private int y;
    private double temperature;
    private boolean boundaryCondition;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isBoundaryCondition() {
        return boundaryCondition;
    }

    public void setBoundaryCondition(boolean boundaryCondition) {
        this.boundaryCondition = boundaryCondition;
    }

    public Node(int x, int y, double temperature, boolean boundaryCondition) {
        this.x = x;
        this.y = y;
        this.temperature = temperature;
        this.boundaryCondition = boundaryCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x &&
                y == node.y &&
                Double.compare(node.temperature, temperature) == 0 &&
                boundaryCondition == node.boundaryCondition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, temperature, boundaryCondition);
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", temperature=" + temperature +
                ", boundaryCondition=" + boundaryCondition +
                '}';
    }
}
