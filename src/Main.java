import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Grid gridTest = new Grid();
        gridTest.gridBuilder();
       System.out.println( gridTest.findElement(15));
    }

}
