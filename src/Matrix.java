public class Matrix {

    private double xi = -(1 / Math.sqrt(3));
    private double eta = 1 / Math.sqrt(3);
    private int[] integrationPointsWeights = new int[]{1, 1};
    private UniversalElement integrationPoint1 = new UniversalElement(xi, -eta, integrationPointsWeights);
    private UniversalElement integrationPoint2 = new UniversalElement(-xi, -eta, integrationPointsWeights);
    private UniversalElement integrationPoint3 = new UniversalElement(-xi, eta, integrationPointsWeights);
    private UniversalElement integrationPoint4 = new UniversalElement(xi, eta, integrationPointsWeights);
    private UniversalElement[] integrationPoints2D = new UniversalElement[]{integrationPoint1, integrationPoint2, integrationPoint3, integrationPoint4};

    public double[][] shapeFunctionsMatrix() {
        double[][] outputMatrix = new double[4][4];

        for (int i = 0; i < integrationPoints2D.length; i++) {
            outputMatrix[i] = new double[]{

                    integrationPoints2D[i].getShapeFunctions()[0], //n1
                    integrationPoints2D[i].getShapeFunctions()[1], //n2
                    integrationPoints2D[i].getShapeFunctions()[2], //n3
                    integrationPoints2D[i].getShapeFunctions()[3]  //n4
            };

        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println("N" + (i + 1) + " value in " + (j + 1) + " integration point: " + outputMatrix[i][j]);
            }
        }

        return outputMatrix;

    }

    public double[][] xiDerivativesMatrix() {
        double[][] outputMatrix = new double[4][4];

        for (int i = 0; i < integrationPoints2D.length; i++) {
            outputMatrix[i] = new double[]{

                    integrationPoints2D[i].getXiDerivatives()[0],
                    integrationPoints2D[i].getXiDerivatives()[1],
                    integrationPoints2D[i].getXiDerivatives()[2],
                    integrationPoints2D[i].getXiDerivatives()[3]
            };

        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println("dN" + (i + 1) + "dXi value in " + (j + 1) + " integration point: " + outputMatrix[i][j]);
            }
        }

        return outputMatrix;

    }

    public double[][] etaDerivativesMatrix() {
        double[][] outputMatrix = new double[4][4];

        for (int i = 0; i < integrationPoints2D.length; i++) {
            outputMatrix[i] = new double[]{

                    integrationPoints2D[i].getEtaDerivatives()[0],
                    integrationPoints2D[i].getEtaDerivatives()[1],
                    integrationPoints2D[i].getEtaDerivatives()[2],
                    integrationPoints2D[i].getEtaDerivatives()[3]
            };

        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println("dN" + (i + 1) + "dEta value in " + (j + 1) + " integration point: " + outputMatrix[i][j]);
            }
        }

        return outputMatrix;

    }


}
