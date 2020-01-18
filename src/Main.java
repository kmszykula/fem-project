import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Grid gridTest = new Grid();
        gridTest.gridBuilder();
       Element[] elements = gridTest.elementBuilder();
        MatrixCalculations matrixCalculations = new MatrixCalculations();

        matrixCalculations.jacobianDeterminant(elements[0]);
        matrixCalculations.dNdX(elements[0]);

       matrixCalculations.dNdy(elements[0]); //todo wywolac to dla wszystkich elementow

        Simulation simulation = new Simulation();
        simulation.heatTransferSimulation(gridTest, matrixCalculations);

    }

}