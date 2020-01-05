import java.util.Arrays;

public class Matrix {

    private double xi = 1 / Math.sqrt(3);
    private double eta = 1 / Math.sqrt(3);

    private int[] integrationPointsWeights = new int[]{1, 1};

    private UniversalElement integrationPoint1 = new UniversalElement(-xi, -eta, integrationPointsWeights);
    private UniversalElement integrationPoint2 = new UniversalElement(xi, -eta, integrationPointsWeights);
    private UniversalElement integrationPoint3 = new UniversalElement(xi, eta, integrationPointsWeights);
    private UniversalElement integrationPoint4 = new UniversalElement(-xi, eta, integrationPointsWeights);
    private UniversalElement[] integrationPoints2D = new UniversalElement[]{integrationPoint1, integrationPoint2, integrationPoint3, integrationPoint4};
    //todo fix them sizes
    private double[] dxdxi = new double[integrationPoints2D.length];
    private double[] dxdeta = new double[integrationPoints2D.length];
    private double[] dydxi = new double[integrationPoints2D.length];
    private double[] dydeta = new double[integrationPoints2D.length];
  private double[] jacobianDeterminant = new double[integrationPoints2D.length];
    private double[][] shapeFunctionsMatrix = new double[integrationPoints2D.length][integrationPoints2D.length];
    private double[][] dndx = new double[integrationPoints2D.length][integrationPoints2D.length];
    private double[][] dndy = new double[integrationPoints2D.length][integrationPoints2D.length];
    private double[][] localCMatrix = new double[4][4]; //todo ujednolicic z h


    public double[][] shapeFunctionsMatrix() {
        //double[][] shapeFunctionsMatrix = new double[4][4];

        for (int i = 0; i < integrationPoints2D.length; i++) {
            shapeFunctionsMatrix[i] = new double[]{

                    integrationPoints2D[i].getShapeFunctions()[0], //n1
                    integrationPoints2D[i].getShapeFunctions()[1], //n2
                    integrationPoints2D[i].getShapeFunctions()[2], //n3
                    integrationPoints2D[i].getShapeFunctions()[3]  //n4
            };

        }
        return shapeFunctionsMatrix;

    }

    public double[][] xiDerivativesMatrix() {
        double[][] result = new double[4][4];

        for (int i = 0; i < integrationPoints2D.length; i++) {
            result[i] = new double[]{

                    integrationPoints2D[i].getXiDerivatives()[0],
                    integrationPoints2D[i].getXiDerivatives()[1],
                    integrationPoints2D[i].getXiDerivatives()[2],
                    integrationPoints2D[i].getXiDerivatives()[3]
            };

        }

        return result;

    }

    public double[][] etaDerivativesMatrix() {
        double[][] result = new double[4][4];

        for (int i = 0; i < integrationPoints2D.length; i++) {
            result[i] = new double[]{

                    integrationPoints2D[i].getEtaDerivatives()[0],
                    integrationPoints2D[i].getEtaDerivatives()[1],
                    integrationPoints2D[i].getEtaDerivatives()[2],
                    integrationPoints2D[i].getEtaDerivatives()[3]
            };

        }

        return result;

    }

    public double[] jacobianDeterminant(Element element) {

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
            jacobianDeterminant[i] = (dxdxi[i] * dydeta[i]) - (dxdeta[i] * dydxi[i]); //liczymy jakobiany w 4. punktach całkowania (tu są akurat takie same bo mamy elementy prostokątne w siatce)
        }
//        for (int i = 0; i < 4; i++) { //to tak na sztywno bo test
//            System.out.println("jakobian " + (i + 1) + " pc: " + jacobianDeterminant[i]);
//        }

        return jacobianDeterminant;
    }

    public double[][] dNdX(Element element) { //zwraca dn1dx dn2dx itd we wszystkich pc

        for (int i = 0; i < dndx.length; i++) {
            for (int j = 0; j < dndx[i].length; j++) {
                dndx[i][j] = (1 / jacobianDeterminant[j]) * ((dydeta[j] * xiDerivativesMatrix()[i][j]) - (dydxi[j] * etaDerivativesMatrix()[i][j]));

            }

        }

        return dndx;
    }

    public double[][] dNdy(Element element) {

        for (int i = 0; i < dndy.length; i++) {
            for (int j = 0; j < dndy[i].length; j++) {
                dndy[i][j] = (1 / jacobianDeterminant[j]) * ((dxdxi[j] * etaDerivativesMatrix()[i][j]) - dxdeta[j] * xiDerivativesMatrix()[i][j]);
            }
        }
        return dndy;
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

        double[][][] dndxMultipliedByTransposed = new double[integrationPoints2D.length][integrationPoints2D.length][integrationPoints2D.length];
        double[][][] dndyMultipliedByTransposed = new double[integrationPoints2D.length][integrationPoints2D.length][integrationPoints2D.length];
        int conductivity = 30;
        double[][][] result = new double[integrationPoints2D.length][integrationPoints2D.length][integrationPoints2D.length];
        double[][] transposedMatrix = transposeMatrix(dndy);
        double[][] transposedMatrix1 = transposeMatrix(dndx);
        for (int i = 0; i < dndxMultipliedByTransposed.length; i++) {
            for (int j = 0; j < dndxMultipliedByTransposed[i].length; j++) {
                for (int k = 0; k < dndxMultipliedByTransposed[i][j].length; k++) {
                    dndxMultipliedByTransposed[0][j][k] = dndx[0][k] * transposedMatrix1[j][0];
                    dndxMultipliedByTransposed[1][j][k] = dndx[1][k] * transposedMatrix1[j][1];
                    dndxMultipliedByTransposed[2][j][k] = dndx[2][k] * transposedMatrix1[j][2];
                    dndxMultipliedByTransposed[3][j][k] = dndx[3][k] * transposedMatrix1[j][3];

                }
            }
        }
        for (int i = 0; i < dndyMultipliedByTransposed.length; i++) {
            for (int j = 0; j < dndyMultipliedByTransposed[i].length; j++) {
                for (int k = 0; k < dndyMultipliedByTransposed[i][j].length; k++) {
                    dndyMultipliedByTransposed[0][j][k] = dndy[0][k] * transposedMatrix[j][0];
                    dndyMultipliedByTransposed[1][j][k] = dndy[1][k] * transposedMatrix[j][1];
                    dndyMultipliedByTransposed[2][j][k] = dndy[2][k] * transposedMatrix[j][2];
                    dndyMultipliedByTransposed[3][j][k] = dndy[3][k] * transposedMatrix[j][3];

                }
            }
        }

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                for (int l = 0; l < result[i][j].length; l++) {

                    result[0][j][l] = (dndxMultipliedByTransposed[0][j][l] + dndyMultipliedByTransposed[0][j][l]) * conductivity * jacobianDeterminant[l]; //todo tu mnozyc x jak zeby to mialo rece i nogi
                    result[1][j][l] = (dndxMultipliedByTransposed[1][j][l] + dndyMultipliedByTransposed[1][j][l]) * conductivity * jacobianDeterminant[l];
                    result[2][j][l] = (dndxMultipliedByTransposed[2][j][l] + dndyMultipliedByTransposed[2][j][l]) * conductivity * jacobianDeterminant[l];
                    result[3][j][l] = (dndxMultipliedByTransposed[3][j][l] + dndyMultipliedByTransposed[3][j][l]) * conductivity * jacobianDeterminant[l];
                }
            }
        }
        double localHMatrix[][] = new double[result.length][result.length]; //co
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

        int ro = 7800;
        int c = 700;
        double[][] shapeFunctionsTransposed = transposeMatrix(shapeFunctionsMatrix);
        double[][][] multiplicationResult = new double[integrationPoints2D.length][shapeFunctionsMatrix.length][shapeFunctionsMatrix[0].length];
        //[c] = c*ro*{N}{N}^T
        for (int i = 0; i < multiplicationResult.length; i++) {
            for (int j = 0; j < multiplicationResult[i].length; j++) {
                for (int l = 0; l < multiplicationResult[i][j].length; l++) {

                    multiplicationResult[0][j][l] = shapeFunctionsMatrix[0][l] * shapeFunctionsTransposed[j][0] * c * ro * jacobianDeterminant[l];
                    multiplicationResult[1][j][l] = shapeFunctionsMatrix[1][l] * shapeFunctionsTransposed[j][1] * c * ro * jacobianDeterminant[l];
                    multiplicationResult[2][j][l] = shapeFunctionsMatrix[2][l] * shapeFunctionsTransposed[j][2] * c * ro * jacobianDeterminant[l];
                    multiplicationResult[3][j][l] = shapeFunctionsMatrix[3][l] * shapeFunctionsTransposed[j][3] * c * ro * jacobianDeterminant[l];
                }
            }
        }
        // double [][]localCMatrix=new double [shapeFunctionsMatrix.length][shapeFunctionsMatrix.length];
        for (int i = 0; i < localCMatrix.length; i++) {
            for (int j = 0; j < localCMatrix[i].length; j++) {
                localCMatrix[i][j] = (multiplicationResult[0][i][j] * integrationPointsWeights[0] * integrationPointsWeights[1]) +
                        (multiplicationResult[1][i][j] * integrationPointsWeights[0] * integrationPointsWeights[1]) +
                        (multiplicationResult[2][i][j] * integrationPointsWeights[0] * integrationPointsWeights[1]) +
                        (multiplicationResult[3][i][j] * integrationPointsWeights[0] * integrationPointsWeights[1]);
            }
        }

        return localCMatrix;

    }

    public double[][] matrixHWithBC(Element element) {

        double[] surfaceIntegrationPoints = new double[]{-1 / Math.sqrt(3), -1}; //nowe pc po powierzchni
        double[][][] HBCforAllSurfaces = new double[4][4][4]; //4 macierze h 4x4, po jednej na kazda powierzchnie (zsumowane te w 2pc)
        double [][]finalHBC=new double [integrationPoints2D.length][integrationPoints2D.length];//todo fix
        int alfa = 25; //współczynnik konwekcyjnej wymiany ciepła
        double[][][] surface1 = new double[2][4][4];
        double[][][] surface2 = new double[2][4][4];
        double[][][] surface3 = new double[2][4][4];
        double[][][] surface4 = new double[2][4][4];
        UniversalElement surface1IP1 = new UniversalElement(surfaceIntegrationPoints[0], surfaceIntegrationPoints[1], integrationPointsWeights);
        UniversalElement surface1IP2 = new UniversalElement(-surfaceIntegrationPoints[0], surfaceIntegrationPoints[1], integrationPointsWeights);
        UniversalElement surface2IP1 = new UniversalElement(-surfaceIntegrationPoints[1], surfaceIntegrationPoints[0], integrationPointsWeights);
        UniversalElement surface2IP2 = new UniversalElement(-surfaceIntegrationPoints[1], -surfaceIntegrationPoints[0], integrationPointsWeights);
        UniversalElement surface3IP1 = new UniversalElement(-surfaceIntegrationPoints[0], -surfaceIntegrationPoints[1], integrationPointsWeights);
        UniversalElement surface3IP2 = new UniversalElement(surfaceIntegrationPoints[0], -surfaceIntegrationPoints[1], integrationPointsWeights);
        UniversalElement surface4IP1 = new UniversalElement(surfaceIntegrationPoints[1], -surfaceIntegrationPoints[0], integrationPointsWeights);
        UniversalElement surface4IP2 = new UniversalElement(surfaceIntegrationPoints[1], surfaceIntegrationPoints[0], integrationPointsWeights);

        for (int i = 0; i < surface1.length; i++) {
            for (int j = 0; j < surface1[i].length; j++) {
                for (int k = 0; k < surface1[i][j].length; k++) {
                    surface1[0][j][k] = surface1IP1.getShapeFunctions()[j] * surface1IP1.getShapeFunctions()[k] * alfa;
                    surface1[1][j][k] = surface1IP2.getShapeFunctions()[j] * surface1IP2.getShapeFunctions()[k] * alfa;
                   HBCforAllSurfaces[0][j][k]=(surface1[0][j][k]*integrationPointsWeights[0]*dxdxi[k])+(surface1[1][j][k]*integrationPointsWeights[1]*dxdxi[k]);
                }
            }
        }
        for (int i = 0; i < surface2.length; i++) {
            for (int j = 0; j < surface2[i].length; j++) {
                for (int k = 0; k < surface2[i][j].length; k++) {
                    surface2[0][j][k] = surface2IP1.getShapeFunctions()[j] * surface2IP1.getShapeFunctions()[k] * alfa; //??
                    surface2[1][j][k] = surface2IP2.getShapeFunctions()[j] * surface2IP2.getShapeFunctions()[k] * alfa;
                    HBCforAllSurfaces[1][j][k]=(surface2[0][j][k]*integrationPointsWeights[0]*dxdxi[k])+(surface2[1][j][k]*integrationPointsWeights[1]*dxdxi[k]);

                }
            }
        }
        for (int i = 0; i < surface3.length; i++) {
            for (int j = 0; j < surface3[i].length; j++) {
                for (int k = 0; k < surface3[i][j].length; k++) {
                    surface3[0][j][k] = surface3IP1.getShapeFunctions()[j] * surface3IP1.getShapeFunctions()[k] * alfa; //??
                    surface3[1][j][k] = surface3IP2.getShapeFunctions()[j] * surface3IP2.getShapeFunctions()[k] * alfa;
                    HBCforAllSurfaces[2][j][k]=(surface3[0][j][k]*integrationPointsWeights[0]*dxdxi[k])+(surface3[1][j][k]*integrationPointsWeights[1]*dxdxi[k]);
                }
            }
        }
        for (int i = 0; i < surface4.length; i++) {
            for (int j = 0; j < surface4[i].length; j++) {
                for (int k = 0; k < surface4[i][j].length; k++) {
                    surface4[0][j][k] = surface4IP1.getShapeFunctions()[j] * surface4IP1.getShapeFunctions()[k] * alfa; //??
                    surface4[1][j][k] = surface4IP2.getShapeFunctions()[j] * surface4IP2.getShapeFunctions()[k] * alfa;
                    HBCforAllSurfaces[3][j][k]=(surface4[0][j][k]*integrationPointsWeights[0]*dxdxi[k])+(surface4[1][j][k]*integrationPointsWeights[1]*dxdxi[k]);
                }
            }
        }
        for (int i = 0; i <finalHBC.length ; i++) {
            for (int j = 0; j <finalHBC[i].length ; j++) {
                finalHBC[i][j]=(HBCforAllSurfaces[0][i][j]*element.getElementNodes()[0].isBoundaryCondition()*element.getElementNodes()[1].isBoundaryCondition())+
                        (HBCforAllSurfaces[1][i][j]*element.getElementNodes()[1].isBoundaryCondition()*element.getElementNodes()[2].isBoundaryCondition())+
                        (HBCforAllSurfaces[2][i][j]*element.getElementNodes()[2].isBoundaryCondition()*element.getElementNodes()[3].isBoundaryCondition())+
                        (HBCforAllSurfaces[3][i][j]*element.getElementNodes()[3].isBoundaryCondition()*element.getElementNodes()[0].isBoundaryCondition());

            }
        }
        for (int i = 0; i <finalHBC.length ; i++) {

                System.out.println(Arrays.toString(finalHBC[i]));

        }
        return finalHBC;
    }


}
