import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Grid gridTest = new Grid();
        gridTest.gridBuilder();
//        System.out.println(gridTest.findElement(15));
//        Node[] node = gridTest.nodeBuilder();
//        for (Node n : node) {
//            System.out.println(n);
//        }

//        Element []element=gridTest.elementBuilder();
//        for (Element e:element){
//            System.out.println(e);
//        }
        gridTest.findElementCoordinates(15);
    }

}
