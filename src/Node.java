import java.util.Objects;

public class Node {
    private int x;
    private int y;
    private double temperature;
    private boolean boundaryConditions;

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

    public boolean isBoundaryConditions() {
        return boundaryConditions;
    }

    public void setBoundaryConditions(boolean boundaryConditions) {
        this.boundaryConditions = boundaryConditions;
    }

    public Node(int x, int y, double temperature, boolean boundaryConditions) {
        this.x = x;
        this.y = y;
        this.temperature = temperature;
        this.boundaryConditions = boundaryConditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x &&
                y == node.y &&
                Double.compare(node.temperature, temperature) == 0 &&
                boundaryConditions == node.boundaryConditions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, temperature, boundaryConditions);
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", temperature=" + temperature +
                ", boundaryConditions=" + boundaryConditions +
                '}';
    }
}
