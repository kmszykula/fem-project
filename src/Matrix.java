public class Matrix {

    private double xi = 1 / Math.sqrt(3);
    private double eta = 1 / Math.sqrt(3);
    private double k = 30;//conductivity//tu ma być czy nie tu ma być? hmmm
    private int[] integrationPointsWeights = new int[]{1, 1};
    private UniversalElement integrationPoint1 = new UniversalElement(-xi, -eta, integrationPointsWeights);
    private UniversalElement integrationPoint2 = new UniversalElement(xi, -eta, integrationPointsWeights);
    private UniversalElement integrationPoint3 = new UniversalElement(xi, eta, integrationPointsWeights);
    private UniversalElement integrationPoint4 = new UniversalElement(-xi, eta, integrationPointsWeights);
    private UniversalElement[] integrationPoints2D = new UniversalElement[]{integrationPoint1, integrationPoint2, integrationPoint3, integrationPoint4};
    private double[] dxdxi = new double[integrationPoints2D.length];
    private double[] dxdeta = new double[integrationPoints2D.length];
    private double[] dydxi = new double[integrationPoints2D.length];
    private double[] dydeta = new double[integrationPoints2D.length];
    private double[] jacobianDeterminant = new double[integrationPoints2D.length];
    private double[][] dndx = new double[integrationPoints2D.length][integrationPoints2D.length];
    private double[][] dndy = new double[integrationPoints2D.length][integrationPoints2D.length];
    private double[][][] dndxMultipliedByTransposed = new double[integrationPoints2D.length][integrationPoints2D.length][integrationPoints2D.length];
    private double[][][] dndyMultipliedByTransposed = new double[integrationPoints2D.length][integrationPoints2D.length][integrationPoints2D.length];


    public double[][] shapeFunctionsMatrix() {
        double[][] result = new double[4][4];

        for (int i = 0; i < integrationPoints2D.length; i++) {
            result[i] = new double[]{

                    integrationPoints2D[i].getShapeFunctions()[0], //n1
                    integrationPoints2D[i].getShapeFunctions()[1], //n2
                    integrationPoints2D[i].getShapeFunctions()[2], //n3
                    integrationPoints2D[i].getShapeFunctions()[3]  //n4
            };

        }
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                System.out.println("N" + (i + 1) + " value in " + (j + 1) + " integration point: " + result[i][j]);
//            }
//        }

        return result;

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
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                System.out.println("dN" + (i + 1) + "dXi value in " + (j + 1) + " integration point: " + result[i][j]);
//            }
//        }

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
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                System.out.println("dN" + (i + 1) + "dEta value in " + (j + 1) + " integration point: " + result[i][j]);
//            }
//        }

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
        for (int i = 0; i < 4; i++) { //to tak na sztywno bo test
            System.out.println("jakobian " + (i + 1) + " pc: " + jacobianDeterminant[i]);
        }

        return jacobianDeterminant;
    }

    public double[][] dNdX(Element element) { //zwraca dn1dx dn2dx itd we wszystkich pc
        //  double [][]dndx = new double [integrationPoints2D.length][integrationPoints2D.length];//todo zmienic bo to nie jest liczba pc!!!
        for (int i = 0; i < dndx.length; i++) {
            for (int j = 0; j < dndx[i].length; j++) {
                dndx[i][j] = (1 / jacobianDeterminant[j]) * ((dydeta[j] * xiDerivativesMatrix()[i][j]) - (dydxi[j] * etaDerivativesMatrix()[i][j]));

            }

        }

        return dndx;
    }

    public double[][] dNdy(Element element) {
        // double [][]dndy=new double [integrationPoints2D.length][integrationPoints2D.length];
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

    public double[][][] multiplydNdxByTransposed(Element element) {//4 macierze 4x4
        double[][] transposedMatrix = transposeMatrix(dndx);
        //double[][][] dndxMultipliedByTransposed  = new double[integrationPoints2D.length][integrationPoints2D.length][integrationPoints2D.length];
        for (int i = 0; i < dndxMultipliedByTransposed .length; i++) {
            for (int j = 0; j < dndxMultipliedByTransposed [i].length; j++) {
                for (int k = 0; k < dndxMultipliedByTransposed [i][j].length; k++) {
                    dndxMultipliedByTransposed [0][j][k] = dndx[0][k] * transposedMatrix[j][0] * jacobianDeterminant[k];
                    dndxMultipliedByTransposed [1][j][k] = dndx[1][k] * transposedMatrix[j][1] * jacobianDeterminant[k];
                    dndxMultipliedByTransposed [2][j][k] = dndx[2][k] * transposedMatrix[j][2] * jacobianDeterminant[k];
                    dndxMultipliedByTransposed [3][j][k] = dndx[3][k] * transposedMatrix[j][3] * jacobianDeterminant[k];

                }
            }
        }

        return dndxMultipliedByTransposed ;
    }

    public double[][][] multiplydNdyByTransposed(Element element) {//4 macierze 4x4
        double[][] transposedMatrix = transposeMatrix(dndy);
        //double[][][] dndyMultipliedByTransposed = new double[integrationPoints2D.length][integrationPoints2D.length][integrationPoints2D.length];
        for (int i = 0; i < dndyMultipliedByTransposed.length; i++) {
            for (int j = 0; j < dndyMultipliedByTransposed[i].length; j++) {
                for (int k = 0; k < dndyMultipliedByTransposed[i][j].length; k++) {
                    dndyMultipliedByTransposed[0][j][k] = dndy[0][k] * transposedMatrix[j][0] * jacobianDeterminant[k];
                    dndyMultipliedByTransposed[1][j][k] = dndy[1][k] * transposedMatrix[j][1] * jacobianDeterminant[k];
                    dndyMultipliedByTransposed[2][j][k] = dndy[2][k] * transposedMatrix[j][2] * jacobianDeterminant[k];
                    dndyMultipliedByTransposed[3][j][k] = dndy[3][k] * transposedMatrix[j][3] * jacobianDeterminant[k];

                }
            }
        }

        return dndyMultipliedByTransposed;
    }

    public double[][] calculateHMatrix(Element element) {
//todo dodac to co wczesniej policzone do sb i pomnozyc przez k,znowu maja byc 4 macierze 4x4
        double[][][] result = new double[integrationPoints2D.length][integrationPoints2D.length][integrationPoints2D.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                for (int l = 0; l < result[i][j].length; l++) {
                    result[0][j][l] = (dndxMultipliedByTransposed[0][j][l]+dndyMultipliedByTransposed[0][j][l])*k; //todo tu mnozyc x jak zeby to mialo rece i nogi
                    result[1][j][l] = (dndxMultipliedByTransposed[1][j][l]+dndyMultipliedByTransposed[1][j][l])*k;
                    result[2][j][l] = (dndxMultipliedByTransposed[2][j][l]+dndyMultipliedByTransposed[2][j][l])*k;
                    result[3][j][l] = (dndxMultipliedByTransposed[3][j][l]+dndyMultipliedByTransposed[3][j][l])*k;
                }
            }
        }
        double localHMatrix[][]=new double[result.length][result.length];
        for (int i = 0; i <localHMatrix.length ; i++) {
            for (int j = 0; j <localHMatrix[i].length ; j++) {
                localHMatrix[i][j]=result[0][i][j]+result[1][i][j]+result[2][i][j]+result[3][i][j];//trzeba to zmienic bo za glowe sie idzie zlapac XDDD
            }
        }
        return localHMatrix;
    }
}
