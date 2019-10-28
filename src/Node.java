import java.util.Objects;

public class Node {


    private int nodeIndex;  //  output
    private double x; //OUTPUT
    private double y;
    private double temperature;
    private boolean boundaryCondition; //??

    public int getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
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

    public Node(int nodeIndex, double x, double y, double temperature, boolean boundaryCondition) {
        this.nodeIndex = nodeIndex;
        this.x = x;
        this.y = y;
        this.temperature = temperature;
        this.boundaryCondition = boundaryCondition;
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
