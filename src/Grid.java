public class Grid {
    GlobalData globalData = new GlobalData();
    public Node[] nodes;
    public Element[] elements;

    public Grid() {
        this.nodes = new Node[globalData.getNumberOfNodes()];
        this.elements = new Element[globalData.getNumberOfElements()];
    }
}
