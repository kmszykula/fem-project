import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Grid grid = new Grid();
        MatrixCalculations matrixCalculations = new MatrixCalculations();
        Simulation simulation = new Simulation();
        simulation.heatTransferSimulation(grid, matrixCalculations);

    }

}