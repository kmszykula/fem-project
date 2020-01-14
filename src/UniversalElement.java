import lombok.Getter;

@Getter
public class UniversalElement {
    private int[] integrationPointsWeights;
    private double[] shapeFunctions;
    private double[] xiDerivatives;
    private double[] etaDerivatives;

    public double N1(double xi, double eta) {
        return 0.25 * (1 - xi) * (1 - eta);
    }

    public double N2(double xi, double eta) {
        return 0.25 * (1 + xi) * (1 - eta);
    }

    public double N3(double xi, double eta) {
        return 0.25 * (1 + xi) * (1 + eta);
    }

    public double N4(double xi, double eta) {
        return 0.25 * (1 - xi) * (1 + eta);
    }

    public double dN1dXi(double xi, double eta) {
        return (-0.25) * (1 - eta);
    }

    public double dN2dXi(double xi, double eta) {
        return 0.25 * (1 - eta);
    }

    public double dN3dXi(double xi, double eta) {
        return 0.25 * (1 + eta);
    }

    public double dN4dXi(double xi, double eta) {
        return (-0.25) * (1 + eta);
    }

    public double dN1dEta(double xi, double eta) {
        return (-0.25) * (1 - xi);
    }

    public double dN2dEta(double xi, double eta) {
        return -(0.25) * (1 + xi);
    }

    public double dN3dEta(double xi, double eta) {
        return 0.25 * (1 + xi);
    }

    public double dN4dEta(double xi, double eta) {
        return 0.25 * (1 - xi);
    }





    public UniversalElement(double xi, double eta, int[] integrationPointsWeights) {

        this.integrationPointsWeights = integrationPointsWeights;
        this.shapeFunctions = new double[]{N1(xi, eta), N2(xi, eta), N3(xi, eta), N4(xi, eta)};
        this.xiDerivatives = new double[]{dN1dXi(xi, eta), dN2dXi(xi, eta), dN3dXi(xi, eta), dN4dXi(xi, eta)};
        this.etaDerivatives = new double[]{dN1dEta(xi, eta), dN2dEta(xi, eta), dN3dEta(xi, eta), dN4dEta(xi, eta)};

    }


}
