import java.util.Objects;

public class GlobalData {
    //TODO wczytać z pliku? w konstruktorze?
    private int height;
    private int width;
    private int numberHeight; //6
    private int numberWidth; //4
    private int numberOfElements;//15: (numberHeight-1)(numberWidth-1), liczba elementów
    private int numberOfNodes; //24 - liczba węzłów numberHeight*numberWidth

    public GlobalData(int height, int width, int numberHeight, int numberWidth, int numberOfElements, int numberOfNodes) {
        this.height = height;
        this.width = width;
        this.numberHeight = numberHeight;
        this.numberWidth = numberWidth;
        this.numberOfElements = numberOfElements;
        this.numberOfNodes = numberOfNodes;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getNumberHeight() {
        return numberHeight;
    }

    public void setNumberHeight(int numberHeight) {
        this.numberHeight = numberHeight;
    }

    public int getNumberWidth() {
        return numberWidth;
    }

    public void setNumberWidth(int numberWidth) {
        this.numberWidth = numberWidth;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlobalData that = (GlobalData) o;
        return height == that.height &&
                width == that.width &&
                numberHeight == that.numberHeight &&
                numberWidth == that.numberWidth &&
                numberOfElements == that.numberOfElements &&
                numberOfNodes == that.numberOfNodes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width, numberHeight, numberWidth, numberOfElements, numberOfNodes);
    }

    @Override
    public String toString() {
        return "GlobalData{" +
                "height=" + height +
                ", width=" + width +
                ", numberHeight=" + numberHeight +
                ", numberWidth=" + numberWidth +
                ", numberOfElements=" + numberOfElements +
                ", numberOfNodes=" + numberOfNodes +
                '}';
    }
}
