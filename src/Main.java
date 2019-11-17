import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
//        Grid gridTest = new Grid();
//        gridTest.gridBuilder();
//
//        gridTest.findElementCoordinates(15);
        Matrix matrix = new Matrix();
        matrix.shapeFunctionsMatrix();
        matrix.xiDerivativesMatrix();
        matrix.etaDerivativesMatrix();



    }

}
