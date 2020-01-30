
import org.la4j.linear.GaussianSolver;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.dense.BasicVector;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Simulation {


    public Simulation() {

    }

    public void heatTransferSimulation(Grid grid, MatrixCalculations matrixCalculations) throws FileNotFoundException {

        double simulationTime = GlobalData.getSimulationTime();
        double simulationTimeStep = GlobalData.getSimulationStepTime();
        int initialTemperature = GlobalData.getInitialTemperature();
        grid.gridBuilder();
        Element[] elements = grid.elementBuilder();
        Node[]nodes=grid.getNodes();

        matrixCalculations.jacobianDeterminant(elements[0]);
        matrixCalculations.dNdX(elements[0]);
        matrixCalculations.dNdy(elements[0]);

        matrixCalculations.xiDerivativesMatrix();
        matrixCalculations.shapeFunctionsMatrix();
        matrixCalculations.etaDerivativesMatrix();

        int iterationsNumber = (int) simulationTime / (int) simulationTimeStep;
        double[] t0 = new double[GlobalData.getNumberOfNodes()];
        Arrays.fill(t0, initialTemperature);

        for (int i = 0; i < iterationsNumber; i++) {

            double[][] globalH = matrixCalculations.globalHMatrix(elements);
            double[][] globalC = matrixCalculations.globalCMatrix(elements);
            double[] globalP = matrixCalculations.globalPVector(elements);
            for (int j = 0; j < globalP.length; j++) {
                globalP[j] *= -1;
            }

            for (int j = 0; j < globalH.length; j++) {
                for (int k = 0; k < globalH[j].length; k++) {
                    globalH[j][k] = globalH[j][k] + globalC[j][k] / simulationTimeStep;
                    globalP[k] = globalP[k] + (globalC[j][k] / simulationTimeStep * nodes[j].getTemperature());
                }
            }

            Basic2DMatrix HMatrix = new Basic2DMatrix(globalH); //h+c
            BasicVector PVector = new BasicVector(globalP);
            GaussianSolver equationSolver = new GaussianSolver(HMatrix);
            BasicVector t1 = (BasicVector) equationSolver.solve(PVector);
            t0 = t1.toArray();
            double []tmp=t1.toArray();

            for (int j = 0; j <tmp.length; j++) {
                nodes[j].setTemperature(tmp[j]);
              //  System.out.println("Node number: " +nodes[j].getNodeIndex()+" - temperature: "+nodes[j].getTemperature());
            }


//            System.out.println("H matrix, iteration number: " + (i+1));
//            System.out.println(HMatrix);
//            System.out.println("P vector, iteration number: " + (i+1));
//            System.out.println(PVector);
//            System.out.println("temperatures: ");
//            System.out.println(Arrays.toString(t0));
            Arrays.sort(tmp);
            System.out.println("Temp. min: "+tmp[0]+", temp. max: "+tmp[tmp.length-1]);


        }


    }
}
