import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
//
        Grid gridTest = new Grid();
        gridTest.gridBuilder();
       Element[] elements = gridTest.elementBuilder();
        MatrixCalculations matrixCalculations = new MatrixCalculations();
//
//        matrix.xiDerivativesMatrix();//nie wywolywac tych f w mainie tylko w tych co licza macierze
//        matrix.shapeFunctionsMatrix();
//        for (int i = 0; i < matrix.xiDerivativesMatrix().length; i++) {
//            for (int j = 0; j < matrix.xiDerivativesMatrix()[i].length; j++) {
//                System.out.println("dN" + (j + 1) + "dXi value in " + (i + 1) + " integration point: " + matrix.xiDerivativesMatrix()[i][j]);
//            }
//        }
//        matrix.etaDerivativesMatrix();
//        for (int i = 0; i < matrix.etaDerivativesMatrix().length; i++) {
//            for (int j = 0; j < matrix.etaDerivativesMatrix()[i].length; j++) {
//                System.out.println("dN" + (j + 1) + "dEta value in " + (i + 1) + " integration point: " + matrix.etaDerivativesMatrix()[i][j]);
//            }
//        }
//
        matrixCalculations.jacobianDeterminant(elements[0]);
        matrixCalculations.dNdX(elements[0]);
//        for (int i = 0; i < matrix.dNdX(elements[0]).length; i++) {
//            for (int j = 0; j < matrix.dNdX(elements[0])[i].length; j++) {
//                System.out.println("dN" + (j + 1) + "dx in " + (i + 1) + " integration point: " + matrix.dNdX(elements[0])[i][j]);
//            }
//        }
       matrixCalculations.dNdy(elements[0]); //todo wywolac to dla wszystkich elementow
//        for (int i = 0; i < matrix.dNdy(elements[0]).length; i++) {
//            for (int j = 0; j < matrix.dNdy(elements[0])[i].length; j++) {
//                System.out.println("dN" + (j + 1) + "dy in " + (i + 1) + " integration point: " + matrix.dNdy(elements[0])[i][j]);
//            }
//        }
//        matrix.multiplydNdxByTransposed(elements[0]);
//        System.out.println("dndx x dndx T: ");
//        for (int i = 0; i < matrix.multiplydNdxByTransposed(elements[0]).length; i++) {
//            System.out.println("dndx x t in " + (i + 1) + " integration point");
//            for (int j = 0; j < matrix.multiplydNdxByTransposed(elements[0])[i].length; j++) {
//                System.out.println(Arrays.toString(matrix.multiplydNdxByTransposed(elements[0])[i][j]));
//            }
//        }
//        matrix.multiplydNdyByTransposed(elements[0]);
//        System.out.println("dndy x dndy T: ");
//        for (int i = 0; i < matrix.multiplydNdyByTransposed(elements[0]).length; i++) {
//            System.out.println("dndy x t in " + (i + 1) + " integration point");
//            for (int j = 0; j < matrix.multiplydNdyByTransposed(elements[0])[i].length; j++) {
//                System.out.println(Arrays.toString(matrix.multiplydNdyByTransposed(elements[0])[i][j]));
//            }
//        }
//        System.out.println("h matrix"); //trzeba chyba wrzucic wszystko do jednej funkcji bo bez dwoch poprzednich nie dziala XDXDX
//        for (int j = 0; j < matrix.calculateLocalHMatrix(elements[0]).length; j++) {
//            System.out.println(Arrays.toString(matrix.calculateLocalHMatrix(elements[0])[j]));
//        }
//        System.out.println("c matrix:");
//        for (int i = 0; i < matrix.calculateLocalCMatrix(elements[0]).length; i++) {
//            System.out.println(Arrays.toString(matrix.calculateLocalCMatrix(elements[0])[i]));
//        }

//        for (int i = 0; i <matrix.calculateLocalCMatrix(elements[0]).length ; i++) {
//            System.out.println((i+1)+" integration point:");
//            for (int j = 0; j <matrix.calculateLocalCMatrix(elements[0]).length ; j++) {
//                System.out.println(Arrays.toString(matrix.calculateLocalCMatrix(elements[0])[i][j]));
//            }
//
//        }
//        for (int i = 0; i < matrix.shapeFunctionsMatrix().length; i++) {
//            for (int j = 0; j < matrix.shapeFunctionsMatrix().length; j++) {
//                System.out.println("N" + (j + 1) + " value in " + (i + 1) + " integration point: " + matrix.shapeFunctionsMatrix()[i][j]);
//            }
//        }

//matrix.matrixHWithBC(elements[14]);
       // matrix.jacobianDeterminant(elements[0]);
//        matrix.PVector(elements[0]);
//        System.out.println("global H:");
//     matrix.globalHMatrix(elements);
//        System.out.println("global C:");
//        matrix.globalCMatrix(elements);
//        System.out.println("global P:");
      //  matrix.globalPVector(elements);
        Simulation simulation = new Simulation();
        simulation.heatTransferSimulation(gridTest, matrixCalculations);

    }

}