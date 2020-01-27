import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.util.Arrays;

@Getter
@Setter
public class MatrixCalculations {

    private double xi = 1 / Math.sqrt(3);
    private double eta = 1 / Math.sqrt(3);
    private int[] integrationPointsWeights = new int[]{1, 1};
    private int shapeFunctionsNumber = 4;

    private LocalCoordinatesPoint integrationPoint1 = new LocalCoordinatesPoint(-xi, -eta, integrationPointsWeights);
    private LocalCoordinatesPoint integrationPoint2 = new LocalCoordinatesPoint(xi, -eta, integrationPointsWeights);
    private LocalCoordinatesPoint integrationPoint3 = new LocalCoordinatesPoint(xi, eta, integrationPointsWeights);
    private LocalCoordinatesPoint integrationPoint4 = new LocalCoordinatesPoint(-xi, eta, integrationPointsWeights);
    private LocalCoordinatesPoint[] universalElement2D = new LocalCoordinatesPoint[]{integrationPoint1, integrationPoint2, integrationPoint3, integrationPoint4}; //tworzymy element uniwersalny

    //Punkty całkowania po powierzchni
    private double[] surfaceIntegrationPoints = new double[]{-1 / Math.sqrt(3), -1};

    private LocalCoordinatesPoint surface1IP1 = new LocalCoordinatesPoint(surfaceIntegrationPoints[0], surfaceIntegrationPoints[1], integrationPointsWeights);
    private LocalCoordinatesPoint surface1IP2 = new LocalCoordinatesPoint(-surfaceIntegrationPoints[0], surfaceIntegrationPoints[1], integrationPointsWeights);

    private LocalCoordinatesPoint surface2IP1 = new LocalCoordinatesPoint(-surfaceIntegrationPoints[1], surfaceIntegrationPoints[0], integrationPointsWeights);
    private LocalCoordinatesPoint surface2IP2 = new LocalCoordinatesPoint(-surfaceIntegrationPoints[1], -surfaceIntegrationPoints[0], integrationPointsWeights);

    private LocalCoordinatesPoint surface3IP1 = new LocalCoordinatesPoint(-surfaceIntegrationPoints[0], -surfaceIntegrationPoints[1], integrationPointsWeights);
    private LocalCoordinatesPoint surface3IP2 = new LocalCoordinatesPoint(surfaceIntegrationPoints[0], -surfaceIntegrationPoints[1], integrationPointsWeights);

    private LocalCoordinatesPoint surface4IP1 = new LocalCoordinatesPoint(surfaceIntegrationPoints[1], -surfaceIntegrationPoints[0], integrationPointsWeights);
    private LocalCoordinatesPoint surface4IP2 = new LocalCoordinatesPoint(surfaceIntegrationPoints[1], surfaceIntegrationPoints[0], integrationPointsWeights);

    private double[] dxdxi = new double[universalElement2D.length];
    private double[] dxdeta = new double[universalElement2D.length];
    private double[] dydxi = new double[universalElement2D.length];
    private double[] dydeta = new double[universalElement2D.length];
    private double[] jacobianDeterminant = new double[universalElement2D.length];
    private double[][] shapeFunctionsMatrix = new double[shapeFunctionsNumber][shapeFunctionsNumber];
    private double[][] dndx = new double[shapeFunctionsNumber][universalElement2D.length];
    private double[][] dndy = new double[shapeFunctionsNumber][universalElement2D.length];
    private double[][] localHMatrix = new double[shapeFunctionsNumber][shapeFunctionsNumber];
    private double[][] localHBCMatrix = new double[shapeFunctionsNumber][shapeFunctionsNumber];
    private double[] localPVector = new double[shapeFunctionsNumber];
    private double[][] localCMatrix = new double[shapeFunctionsNumber][shapeFunctionsNumber];

    private int conductivity = GlobalData.getConductivity();
    private int alfa = GlobalData.getAlfa();
    private int ro = GlobalData.getDensity();
    private int c = GlobalData.getSpecificHeat();
    private int ambientTemperature = GlobalData.getAmbientTemperature();

    public void shapeFunctionsMatrix() {

        for (int i = 0; i < universalElement2D.length; i++) {
            shapeFunctionsMatrix[i] = new double[]{

                    universalElement2D[i].getShapeFunctions()[0], //n1
                    universalElement2D[i].getShapeFunctions()[1], //n2
                    universalElement2D[i].getShapeFunctions()[2], //n3
                    universalElement2D[i].getShapeFunctions()[3]  //n4
            };

        }

    }

    public double[][] xiDerivativesMatrix() {
        double[][] result = new double[shapeFunctionsNumber][universalElement2D.length];

        for (int i = 0; i < universalElement2D.length; i++) {
            result[i] = new double[]{

                    universalElement2D[i].getXiDerivatives()[0],
                    universalElement2D[i].getXiDerivatives()[1],
                    universalElement2D[i].getXiDerivatives()[2],
                    universalElement2D[i].getXiDerivatives()[3]
            };

        }

        return result;

    }

    public double[][] etaDerivativesMatrix() {
        double[][] result = new double[shapeFunctionsNumber][universalElement2D.length];

        for (int i = 0; i < universalElement2D.length; i++) {
            result[i] = new double[]{

                    universalElement2D[i].getEtaDerivatives()[0],
                    universalElement2D[i].getEtaDerivatives()[1],
                    universalElement2D[i].getEtaDerivatives()[2],
                    universalElement2D[i].getEtaDerivatives()[3]
            };

        }

        return result;

    }

    public void jacobianDeterminant(Element element) {

        for (int i = 0; i < xiDerivativesMatrix().length; i++) {
            for (int j = 0; j < xiDerivativesMatrix()[i].length; j++) {
                dxdxi[i] += xiDerivativesMatrix()[i][j] * element.getElementNodes()[j].getX();
                dydxi[i] += xiDerivativesMatrix()[i][j] * element.getElementNodes()[j].getY();

            }

        }
        for (int i = 0; i < etaDerivativesMatrix().length; i++) {
            for (int j = 0; j < etaDerivativesMatrix()[i].length; j++) {
                dxdeta[i] += etaDerivativesMatrix()[i][j] * element.getElementNodes()[j].getX();
                dydeta[i] += etaDerivativesMatrix()[i][j] * element.getElementNodes()[j].getY();

            }

        }

        for (int i = 0; i < jacobianDeterminant.length; i++) {
            jacobianDeterminant[i] = (dxdxi[i] * dydeta[i]) - (dxdeta[i] * dydxi[i]);                                    //liczymy jakobiany w 4. punktach całkowania (tu są akurat takie same bo mamy elementy prostokątne w siatce)
        }
//        for (int i = 0; i < 4; i++) {
//            System.out.println("jakobian " + (i + 1) + " pc: " + jacobianDeterminant[i]);
//        }

    }

    public void dNdX(Element element) { //zwraca dn1dx dn2dx itd we wszystkich pc

        for (int i = 0; i < dndx.length; i++) {
            for (int j = 0; j < dndx[i].length; j++) {
                dndx[i][j] = (1 / jacobianDeterminant[j]) * ((dydeta[j] * xiDerivativesMatrix()[i][j]) - (dydxi[j] * etaDerivativesMatrix()[i][j]));

            }

        }

    }

    public void dNdy(Element element) {

        for (int i = 0; i < dndy.length; i++) {
            for (int j = 0; j < dndy[i].length; j++) {
                dndy[i][j] = (1 / jacobianDeterminant[j]) * ((dxdxi[j] * etaDerivativesMatrix()[i][j]) - dxdeta[j] * xiDerivativesMatrix()[i][j]);
            }
        }
    }

    public double[][] transposeMatrix(double[][] matrix) {
        double[][] tmp = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                tmp[j][i] = matrix[i][j];
            }
        }
        return tmp;
    }


    public double[][] calculateLocalHMatrix(Element element) { //lokalna macierz H (suma we wszystkich 4 pc)

        double[][][] dndxMultipliedByTransposed = new double[universalElement2D.length][shapeFunctionsNumber][shapeFunctionsNumber];
        double[][][] dndyMultipliedByTransposed = new double[universalElement2D.length][shapeFunctionsNumber][shapeFunctionsNumber];

        double[][][] result = new double[universalElement2D.length][shapeFunctionsNumber][shapeFunctionsNumber];
        double[][] dndyTransposed = transposeMatrix(dndy);
        double[][] dndxTransposed = transposeMatrix(dndx);
        for (int i = 0; i < dndxMultipliedByTransposed.length; i++) {
            for (int j = 0; j < dndxMultipliedByTransposed[i].length; j++) {
                for (int k = 0; k < dndxMultipliedByTransposed[i][j].length; k++) {
                    dndxMultipliedByTransposed[0][j][k] = dndx[0][k] * dndxTransposed[j][0];
                    dndxMultipliedByTransposed[1][j][k] = dndx[1][k] * dndxTransposed[j][1];
                    dndxMultipliedByTransposed[2][j][k] = dndx[2][k] * dndxTransposed[j][2];
                    dndxMultipliedByTransposed[3][j][k] = dndx[3][k] * dndxTransposed[j][3];

                }
            }
        }
        for (int i = 0; i < dndyMultipliedByTransposed.length; i++) {
            for (int j = 0; j < dndyMultipliedByTransposed[i].length; j++) {
                for (int k = 0; k < dndyMultipliedByTransposed[i][j].length; k++) {
                    dndyMultipliedByTransposed[0][j][k] = dndy[0][k] * dndyTransposed[j][0];
                    dndyMultipliedByTransposed[1][j][k] = dndy[1][k] * dndyTransposed[j][1];
                    dndyMultipliedByTransposed[2][j][k] = dndy[2][k] * dndyTransposed[j][2];
                    dndyMultipliedByTransposed[3][j][k] = dndy[3][k] * dndyTransposed[j][3];

                }
            }
        }

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                for (int l = 0; l < result[i][j].length; l++) {

                    result[0][j][l] = (dndxMultipliedByTransposed[0][j][l] + dndyMultipliedByTransposed[0][j][l]) * conductivity * jacobianDeterminant[l];
                    result[1][j][l] = (dndxMultipliedByTransposed[1][j][l] + dndyMultipliedByTransposed[1][j][l]) * conductivity * jacobianDeterminant[l];
                    result[2][j][l] = (dndxMultipliedByTransposed[2][j][l] + dndyMultipliedByTransposed[2][j][l]) * conductivity * jacobianDeterminant[l];
                    result[3][j][l] = (dndxMultipliedByTransposed[3][j][l] + dndyMultipliedByTransposed[3][j][l]) * conductivity * jacobianDeterminant[l];
                }
            }
        }

        for (int i = 0; i < localHMatrix.length; i++) {
            for (int j = 0; j < localHMatrix[i].length; j++) {
                localHMatrix[i][j] = (result[0][i][j] * integrationPointsWeights[0] * integrationPointsWeights[1]) +
                        (result[1][i][j] * integrationPointsWeights[0] * integrationPointsWeights[1]) +
                        (result[2][i][j] * integrationPointsWeights[0] * integrationPointsWeights[1]) +
                        (result[3][i][j] * integrationPointsWeights[0] * integrationPointsWeights[1]);
            }
        }
        return localHMatrix;
    }

    public double[][] calculateLocalCMatrix(Element element) {

        double[][] shapeFunctionsTransposed = transposeMatrix(shapeFunctionsMatrix);
        double[][][] NVectorMultipliedByTransposed = new double[universalElement2D.length][shapeFunctionsMatrix.length][shapeFunctionsMatrix[0].length];
        for (int i = 0; i < NVectorMultipliedByTransposed.length; i++) {
            for (int j = 0; j < NVectorMultipliedByTransposed[i].length; j++) {
                for (int l = 0; l < NVectorMultipliedByTransposed[i][j].length; l++) {

                    NVectorMultipliedByTransposed[0][j][l] = shapeFunctionsMatrix[0][l] * shapeFunctionsTransposed[j][0] * c * ro * jacobianDeterminant[l];
                    NVectorMultipliedByTransposed[1][j][l] = shapeFunctionsMatrix[1][l] * shapeFunctionsTransposed[j][1] * c * ro * jacobianDeterminant[l];
                    NVectorMultipliedByTransposed[2][j][l] = shapeFunctionsMatrix[2][l] * shapeFunctionsTransposed[j][2] * c * ro * jacobianDeterminant[l];
                    NVectorMultipliedByTransposed[3][j][l] = shapeFunctionsMatrix[3][l] * shapeFunctionsTransposed[j][3] * c * ro * jacobianDeterminant[l];
                }
            }
        }
        // double [][]localCMatrix=new double [shapeFunctionsMatrix.length][shapeFunctionsMatrix.length];
        for (int i = 0; i < localCMatrix.length; i++) {
            for (int j = 0; j < localCMatrix[i].length; j++) {
                localCMatrix[i][j] = (NVectorMultipliedByTransposed[0][i][j] * integrationPointsWeights[0] * integrationPointsWeights[1]) +
                        (NVectorMultipliedByTransposed[1][i][j] * integrationPointsWeights[0] * integrationPointsWeights[1]) +
                        (NVectorMultipliedByTransposed[2][i][j] * integrationPointsWeights[0] * integrationPointsWeights[1]) +
                        (NVectorMultipliedByTransposed[3][i][j] * integrationPointsWeights[0] * integrationPointsWeights[1]);
            }
        }

        return localCMatrix;

    }

    public double jacobianDeterminant1D(int nodeIndex1, int nodeIndex2, Element element) {
        double X2MinusX1 = element.getElementNodes()[nodeIndex2].getX() - element.getElementNodes()[nodeIndex1].getX();
        double Y2MinusY1 = element.getElementNodes()[nodeIndex2].getY() - element.getElementNodes()[nodeIndex1].getY();

        return 0.5 * (Math.sqrt(Math.pow(X2MinusX1, 2) + Math.pow(Y2MinusY1, 2)));
    }

    public double[][] matrixHWithBC(Element element) {


        double[][][] HBCforAllSurfaces = new double[4][shapeFunctionsNumber][shapeFunctionsNumber]; //4 macierze h 4x4, po jednej na kazda powierzchnie (zsumowane te w 2pc)
        // double[][] localHBCMatrix = new double[integrationPoints2D.length][integrationPoints2D.length];//todo fix

        double[][][] surface1 = new double[surfaceIntegrationPoints.length][shapeFunctionsNumber][shapeFunctionsNumber]; //0,1
        double[][][] surface2 = new double[surfaceIntegrationPoints.length][shapeFunctionsNumber][shapeFunctionsNumber]; //1,2
        double[][][] surface3 = new double[surfaceIntegrationPoints.length][shapeFunctionsNumber][shapeFunctionsNumber]; //2,3
        double[][][] surface4 = new double[surfaceIntegrationPoints.length][shapeFunctionsNumber][shapeFunctionsNumber]; //3,0


        for (int i = 0; i < surface1.length; i++) {
            for (int j = 0; j < surface1[i].length; j++) {
                for (int k = 0; k < surface1[i][j].length; k++) {
                    surface1[0][j][k] = surface1IP1.getShapeFunctions()[j] * surface1IP1.getShapeFunctions()[k] * alfa;
                    surface1[1][j][k] = surface1IP2.getShapeFunctions()[j] * surface1IP2.getShapeFunctions()[k] * alfa;
                    HBCforAllSurfaces[0][j][k] = (surface1[0][j][k] * integrationPointsWeights[0] * jacobianDeterminant1D(0,1,element)) + (surface1[1][j][k] * integrationPointsWeights[1] *  jacobianDeterminant1D(0,1,element));
                }
            }
        }
        for (int i = 0; i < surface2.length; i++) {
            for (int j = 0; j < surface2[i].length; j++) {
                for (int k = 0; k < surface2[i][j].length; k++) {
                    surface2[0][j][k] = surface2IP1.getShapeFunctions()[j] * surface2IP1.getShapeFunctions()[k] * alfa;
                    surface2[1][j][k] = surface2IP2.getShapeFunctions()[j] * surface2IP2.getShapeFunctions()[k] * alfa;
                    HBCforAllSurfaces[1][j][k] = (surface2[0][j][k] * integrationPointsWeights[0] *  jacobianDeterminant1D(1,2,element)) + (surface2[1][j][k] * integrationPointsWeights[1] *  jacobianDeterminant1D(1,2,element));

                }
            }
        }
        for (int i = 0; i < surface3.length; i++) {
            for (int j = 0; j < surface3[i].length; j++) {
                for (int k = 0; k < surface3[i][j].length; k++) {
                    surface3[0][j][k] = surface3IP1.getShapeFunctions()[j] * surface3IP1.getShapeFunctions()[k] * alfa;
                    surface3[1][j][k] = surface3IP2.getShapeFunctions()[j] * surface3IP2.getShapeFunctions()[k] * alfa;
                    HBCforAllSurfaces[2][j][k] = (surface3[0][j][k] * integrationPointsWeights[0] *  jacobianDeterminant1D(2,3,element)) + (surface3[1][j][k] * integrationPointsWeights[1] *  jacobianDeterminant1D(2,3,element));
                }
            }
        }
        for (int i = 0; i < surface4.length; i++) {
            for (int j = 0; j < surface4[i].length; j++) {
                for (int k = 0; k < surface4[i][j].length; k++) {
                    surface4[0][j][k] = surface4IP1.getShapeFunctions()[j] * surface4IP1.getShapeFunctions()[k] * alfa;
                    surface4[1][j][k] = surface4IP2.getShapeFunctions()[j] * surface4IP2.getShapeFunctions()[k] * alfa;
                    HBCforAllSurfaces[3][j][k] = (surface4[0][j][k] * integrationPointsWeights[0] * jacobianDeterminant1D(3,0,element)) + (surface4[1][j][k] * integrationPointsWeights[1] *  jacobianDeterminant1D(3,0,element));
                }
            }
        }
        //sumujemy wszystkie powierzchnie
        for (int i = 0; i < localHBCMatrix.length; i++) {
            for (int j = 0; j < localHBCMatrix[i].length; j++) {
                localHBCMatrix[i][j] = (HBCforAllSurfaces[0][i][j] * element.getElementNodes()[0].isBoundaryCondition() * element.getElementNodes()[1].isBoundaryCondition()) +
                        (HBCforAllSurfaces[1][i][j] * element.getElementNodes()[1].isBoundaryCondition() * element.getElementNodes()[2].isBoundaryCondition()) +
                        (HBCforAllSurfaces[2][i][j] * element.getElementNodes()[2].isBoundaryCondition() * element.getElementNodes()[3].isBoundaryCondition()) +
                        (HBCforAllSurfaces[3][i][j] * element.getElementNodes()[3].isBoundaryCondition() * element.getElementNodes()[0].isBoundaryCondition());

            }
        }
//        for (int i = 0; i < localHBCMatrix.length; i++) {
//
//            System.out.println(Arrays.toString(localHBCMatrix[i]));
//
//        }
        return localHBCMatrix;
    }

    public double[] PVector(Element element) {

        double[][] PVectorForAllSurfaces = new double[4][shapeFunctionsNumber]; //4 wektory 1x4, po jednym na każdą powierzchnię
        double[][] surface1 = new double[2][shapeFunctionsNumber];//wektor dla 1 powierzchni w 2 punktach calkowania
        double[][] surface2 = new double[2][shapeFunctionsNumber];
        double[][] surface3 = new double[2][shapeFunctionsNumber];
        double[][] surface4 = new double[2][shapeFunctionsNumber];
        for (int i = 0; i < surface1.length; i++) {
            for (int j = 0; j < surface1[i].length; j++) {
                surface1[0][j] = -alfa * surface1IP1.getShapeFunctions()[j] * ambientTemperature;
                surface1[1][j] = -alfa * surface1IP2.getShapeFunctions()[j] * ambientTemperature;
                PVectorForAllSurfaces[0][j] = (surface1[0][j] * integrationPointsWeights[0] *  jacobianDeterminant1D(0,1,element)) + (surface1[1][j] * integrationPointsWeights[1] *  jacobianDeterminant1D(0,1,element));
            }
        }
        for (int i = 0; i < surface2.length; i++) {
            for (int j = 0; j < surface2[i].length; j++) {
                surface2[0][j] = -alfa * surface2IP1.getShapeFunctions()[j] * ambientTemperature;
                surface2[1][j] = -alfa * surface2IP2.getShapeFunctions()[j] * ambientTemperature;
                PVectorForAllSurfaces[1][j] = (surface2[0][j] * integrationPointsWeights[0] *  jacobianDeterminant1D(1,2,element)) + (surface2[1][j] * integrationPointsWeights[1] *  jacobianDeterminant1D(1,2,element));
            }
        }
        for (int i = 0; i < surface3.length; i++) {
            for (int j = 0; j < surface3[i].length; j++) {
                surface3[0][j] = -alfa * surface3IP1.getShapeFunctions()[j] * ambientTemperature;
                surface3[1][j] = -alfa * surface3IP2.getShapeFunctions()[j] * ambientTemperature;
                PVectorForAllSurfaces[2][j] = (surface3[0][j] * integrationPointsWeights[0] * jacobianDeterminant1D(2,3,element)) + (surface3[1][j] * integrationPointsWeights[1] *  jacobianDeterminant1D(2,3,element));
            }
        }
        for (int i = 0; i < surface4.length; i++) {
            for (int j = 0; j < surface4[i].length; j++) {
                surface4[0][j] = -alfa * surface4IP1.getShapeFunctions()[j] * ambientTemperature;
                surface4[1][j] = -alfa * surface4IP2.getShapeFunctions()[j] * ambientTemperature;
                PVectorForAllSurfaces[3][j] = (surface4[0][j] * integrationPointsWeights[0] *  jacobianDeterminant1D(3,0,element)) + (surface4[1][j] * integrationPointsWeights[1] *  jacobianDeterminant1D(3,0,element));
            }
        }
        for (int i = 0; i < localPVector.length; i++) {
            localPVector[i] = (PVectorForAllSurfaces[0][i] * element.getElementNodes()[0].isBoundaryCondition() * element.getElementNodes()[1].isBoundaryCondition()) +
                    (PVectorForAllSurfaces[1][i] * element.getElementNodes()[1].isBoundaryCondition() * element.getElementNodes()[2].isBoundaryCondition()) +
                    (PVectorForAllSurfaces[2][i] * element.getElementNodes()[2].isBoundaryCondition() * element.getElementNodes()[3].isBoundaryCondition()) +
                    (PVectorForAllSurfaces[3][i] * element.getElementNodes()[3].isBoundaryCondition() * element.getElementNodes()[0].isBoundaryCondition());

        }
//        System.out.println("p vector");
//        System.out.println(Arrays.toString(localPVector));


        return localPVector;
    }

    public double[][] globalHMatrix(Element[] elements) throws FileNotFoundException {
        double[][] globalHMatrix = new double[GlobalData.getNumberOfNodes()][GlobalData.getNumberOfNodes()]; //?

        for (int i = 0; i < globalHMatrix.length; i++) {
            Arrays.fill(globalHMatrix[i], 0);
        }
        for (int i = 0; i < elements.length; i++) {
            int[] nodeIDs = new int[4];
            double[][] localHMatrixForSpecificElement = calculateLocalHMatrix(elements[i]);
            double[][] localHBCMatrixForSpecificElement = matrixHWithBC(elements[i]);
            for (int j = 0; j < nodeIDs.length; j++) {
                nodeIDs[j] = elements[i].getElementNodes()[j].getNodeIndex() - 1;
            }
            for (int z = 0; z < nodeIDs.length; z++) {
                for (int k = 0; k < nodeIDs.length; k++) {
                    globalHMatrix[nodeIDs[z]][nodeIDs[k]] += localHMatrixForSpecificElement[z][k] + localHBCMatrixForSpecificElement[z][k]; //czy to zadziala jak h jest globalne xddd ja tego nie widze
                }
            }

        }
//        for (int i = 0; i < globalHMatrix.length; i++) {
//            System.out.println(Arrays.toString(globalHMatrix[i]));
//        }
        return globalHMatrix;
    }

    public double[][] globalCMatrix(Element[] elements) throws FileNotFoundException {
        double[][] globalCMatrix = new double[GlobalData.getNumberOfNodes()][GlobalData.getNumberOfNodes()]; //?
        for (int i = 0; i < globalCMatrix.length; i++) {
            Arrays.fill(globalCMatrix[i], 0);
        }
        for (int i = 0; i < elements.length; i++) {
            int[] nodeIDs = new int[4];
            double[][] localCMatrixForSpecificElement = calculateLocalCMatrix(elements[i]);
            for (int j = 0; j < nodeIDs.length; j++) {
                nodeIDs[j] = elements[i].getElementNodes()[j].getNodeIndex() - 1;
            }
            for (int z = 0; z < nodeIDs.length; z++) {
                for (int k = 0; k < nodeIDs.length; k++) {

                    globalCMatrix[nodeIDs[z]][nodeIDs[k]] += localCMatrixForSpecificElement[z][k];
                }
            }

        }
//        for (int i = 0; i < globalCMatrix.length; i++) {
//            System.out.println(Arrays.toString(globalCMatrix[i]));
//        }
        return globalCMatrix;


    }

    public double[] globalPVector(Element[] elements) throws FileNotFoundException {
        double[] globalPVector = new double[GlobalData.getNumberOfNodes()];
        Arrays.fill(globalPVector, 0);
        for (int i = 0; i < elements.length; i++) {
            int[] nodeIDs = new int[4];
            double[] localPVectorForSpecificElement = PVector(elements[i]);
            for (int j = 0; j < nodeIDs.length; j++) {
                nodeIDs[j] = elements[i].getElementNodes()[j].getNodeIndex() - 1;
            }
            for (int k = 0; k < nodeIDs.length; k++) {
                globalPVector[nodeIDs[k]] += localPVectorForSpecificElement[k];
            }
        }
//        System.out.println(Arrays.toString(globalPVector));
        return globalPVector;
    }

}