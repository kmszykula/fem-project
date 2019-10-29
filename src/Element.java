import java.util.Arrays;

public class Element {


    private int elementIndex;
    private int[] nodesIDs = new int[4];

    public void setNodesIDs(int index, double nH) {
        nodesIDs[0] = index;
        nodesIDs[1] = nodesIDs[0] + (int) nH;
        nodesIDs[2] = nodesIDs[1] + 1;
        nodesIDs[3] = nodesIDs[0] + 1;

    }

    public int getElementIndex() {
        return elementIndex;
    }

    public void setElementIndex(int elementIndex) {
        this.elementIndex = elementIndex;
    }


    public Element(int elementIndex) {
        this.elementIndex = elementIndex;

    }

    public int[] getNodesIDs() {
        return nodesIDs;
    }




    @Override
    public String toString() {
        return "Element " +
                elementIndex +
                ": nodes = " + Arrays.toString(nodesIDs);
    }
}
