import lombok.Getter;

@Getter
public class UniversalElement { //element w układzie (xi, eta)

    private double[] integrationPoints =new double[]{-(1 / Math.sqrt(3)), 1 / Math.sqrt(3)}; //i wartosci z tego przypisywac do xi eta z odp znakiem?
    private int[] integrationPointsWeights=new int[]{1,1};
    private double xi;
    private double eta;
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
    }//czy do tego mają byc gettery czy neipotrzebne sa

    public double dN4dEta(double xi, double eta) {
        return 0.25 * (1 - xi);
    }


    public UniversalElement(double xi, double eta) {

        this.xi = integrationPoints[0];
        this.eta = integrationPoints[1];
        this.shapeFunctions= new double[]{N1(xi, eta), N2(xi, eta), N3(xi, eta), N4(xi, eta)};
        this.xiDerivatives= new double[]{dN1dXi(xi, eta), dN2dXi(xi, eta), dN3dXi(xi, eta), dN4dXi(xi, eta)};
        this.etaDerivatives=new double[]{dN1dEta(xi, eta), dN2dEta(xi, eta), dN3dEta(xi, eta), dN4dEta(xi, eta)}; //chyba nie mogę tego tak zrobić XDD zabije sie zaraz
        //not sure about this
    }



}
