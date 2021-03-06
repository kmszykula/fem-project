

public class Node {

    private int nodeIndex;
    private double x;
    private double y;
    private double temperature;
    private int boundaryCondition;

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

    public int isBoundaryCondition() {
        return boundaryCondition;
    }

    public void setBoundaryCondition(int boundaryCondition) {
        this.boundaryCondition = boundaryCondition;
    }

    public Node(int nodeIndex, double x, double y, double temperature, int boundaryCondition) {
        this.nodeIndex = nodeIndex;
        this.x = x;
        this.y = y;
        this.temperature = temperature;
        this.boundaryCondition = boundaryCondition;
    }


    @Override
    public String toString() {
        return "Node " + nodeIndex + ": " +
                "x=" + x +
                ", y=" + y +
                ", temperature=" + temperature +
                ", boundaryCondition=" + boundaryCondition;
    }
}
