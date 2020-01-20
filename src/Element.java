


public class Element {

    private int elementIndex;
    private Node[] elementNodes = new Node[4];

    public void setNodes(Node node1, Node node2, Node node3, Node node4){
        elementNodes[0]=node1;
        elementNodes[1]=node2;
        elementNodes[2]=node3;
        elementNodes[3]=node4;
    }

    public Element(int elementIndex) {
        this.elementIndex = elementIndex;

    }

    public int getElementIndex() {
        return elementIndex;
    }

    public void setElementIndex(int elementIndex) {
        this.elementIndex = elementIndex;
    }

    public Node[] getElementNodes() {
        return elementNodes;
    }

    public void setElementNodes(Node[] elementNodes) {
        this.elementNodes = elementNodes;
    }

    @Override
    public String toString() {
        return "Element " +
                elementIndex;
    }
}
