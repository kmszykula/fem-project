public class Matrix {
    private double xi = -(1 / Math.sqrt(3));
    private double eta = 1 / Math.sqrt(3);

    public double[][] xiDerivativesMatrix() {
        double[][] outputMatrix = new double[4][4];
        UniversalElement universalElement=new UniversalElement(xi,eta);

        for (int i = 0; i < 4; i++) {//? zeby tu nie bylo 4 tylko jakas zmienna TODO


            //TODO dobra czyli jednak musze se stworzyc obiekt który ma pola xi i eta i przekazywać mu te pkt calk w konstrultprze
            // outputMatrix[i][j]=universalelement(xi,eta,ipweigts,????? no chyba jednak nie tak:)))))))
            //todo tablica 1d i prsypisanie naraz 4 obiektow
            outputMatrix[i]=new double[]{
                    //universalElement.getXiDerivatives()[0]
            }

        }

        return outputMatrix;

    }



}
