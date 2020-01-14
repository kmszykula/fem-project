import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class GlobalData {


    private double height;
    private double width;
    private int nodeHeight;
    private int nodeWidth;
    private int numberOfElements;
    private int numberOfNodes;



    public GlobalData() throws FileNotFoundException {
        File file = new File("C:\\Users\\asus\\fem-grid\\src\\mes.txt");
        Scanner scanner = new Scanner(file);
        this.height = Double.parseDouble(scanner.nextLine());
        this.width = Double.parseDouble(scanner.nextLine());
        this.nodeHeight =  Integer.parseInt(scanner.nextLine()); //ilosc wezlow na wysokosc
        this.nodeWidth = Integer.parseInt(scanner.nextLine()); //ilosc wezlow na szerokosc
        this.numberOfElements = (nodeHeight - 1) * (nodeWidth - 1);
        this.numberOfNodes = nodeHeight * nodeWidth;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getNodeHeight() {
        return nodeHeight;
    }

    public void setNodeHeight(int nodeHeight) {
        this.nodeHeight = nodeHeight;
    }

    public int getNodeWidth() {
        return nodeWidth;
    }

    public void setNodeWidth(int nodeWidth) {
        this.nodeWidth = nodeWidth;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }



    @Override
    public String toString() {
        return "GlobalData{" +
                "height=" + height +
                ", width=" + width +
                ", numberHeight=" + nodeHeight +
                ", numberWidth=" + nodeWidth +
                ", numberOfElements=" + numberOfElements +
                ", numberOfNodes=" + numberOfNodes +
                '}';
    }
}
