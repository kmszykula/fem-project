
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GlobalData {


    private static double height; //h
    private static double width; //b
    private static int nodeHeight; //nh - liczba wezlow na wysokosc
    private static int nodeWidth; //nb  - liczba wezlow na szerokosc
    private static int numberOfElements;
    private static int numberOfNodes;
    private static int initialTemperature;
    private static int simulationTime;
    private static int simulationStepTime;
    private static int ambientTemperature;
    private static int specificHeat;
    private static int conductivity;
    private static int density;
    private static int alfa;


    public GlobalData() throws FileNotFoundException {
       File file = new File("C:\\Users\\asus\\fem-grid\\src\\mes.txt");
        Scanner scanner = new Scanner(file);
        height = Double.parseDouble(scanner.nextLine());
        width = Double.parseDouble(scanner.nextLine());
        nodeHeight = Integer.parseInt(scanner.nextLine()); //ilosc wezlow na wysokosc
        nodeWidth = Integer.parseInt(scanner.nextLine()); //ilosc wezlow na szerokosc
        initialTemperature = Integer.parseInt(scanner.nextLine());
        simulationTime = Integer.parseInt(scanner.nextLine());
        simulationStepTime = Integer.parseInt(scanner.nextLine());
        ambientTemperature = Integer.parseInt(scanner.nextLine());
        specificHeat = Integer.parseInt(scanner.nextLine());
        conductivity = Integer.parseInt(scanner.nextLine());
        density = Integer.parseInt(scanner.nextLine());
        alfa = Integer.parseInt(scanner.nextLine());
        numberOfElements = (nodeHeight - 1) * (nodeWidth - 1);
        numberOfNodes = nodeHeight * nodeWidth;

    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        GlobalData.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(int width) {
        GlobalData.width = width;
    }

    public int getNodeHeight() {
        return nodeHeight;
    }

    public void setNodeHeight(int nodeHeight) {
        GlobalData.nodeHeight = nodeHeight;
    }

    public int getNodeWidth() {
        return nodeWidth;
    }

    public void setNodeWidth(int nodeWidth) {
        GlobalData.nodeWidth = nodeWidth;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        GlobalData.numberOfElements = numberOfElements;
    }

    public static int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        GlobalData.numberOfNodes = numberOfNodes;
    }
    public static int getAlfa() {
        return alfa;
    }

    public static void setAlfa(int alfa) {
        GlobalData.alfa = alfa;
    }


    public static int getInitialTemperature() {
        return initialTemperature;
    }

    public static void setInitialTemperature(int initialTemperature) {
        GlobalData.initialTemperature = initialTemperature;
    }

    public static int getSimulationTime() {
        return simulationTime;
    }

    public static void setSimulationTime(int simulationTime) {
        GlobalData.simulationTime = simulationTime;
    }

    public static int getSimulationStepTime() {
        return simulationStepTime;
    }

    public static void setSimulationStepTime(int simulationStepTime) {
        GlobalData.simulationStepTime = simulationStepTime;
    }

    public static int getAmbientTemperature() {
        return ambientTemperature;
    }

    public static void setAmbientTemperature(int ambientTemperature) {
        GlobalData.ambientTemperature = ambientTemperature;
    }

    public static int getSpecificHeat() {
        return specificHeat;
    }

    public static void setSpecificHeat(int specificHeat) {
        GlobalData.specificHeat = specificHeat;
    }

    public static int getConductivity() {
        return conductivity;
    }

    public static void setConductivity(int conductivity) {
        GlobalData.conductivity = conductivity;
    }

    public static int getDensity() {
        return density;
    }

    public static void setDensity(int density) {
        GlobalData.density = density;
    }

}
