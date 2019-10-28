import java.io.FileNotFoundException;

public class Grid {

    private GlobalData globalData;
    private Node[] nodes;
    private Element[] elements;

    private boolean isBoundaryCondition(double x, double y) {
        if (x == 0 || y == 0 || x == globalData.getWidth() || y == globalData.getHeight()) {
            return true;
        } else {
            return false;
        }
    }

    public Grid() throws FileNotFoundException {
        this.globalData = new GlobalData();
        this.nodes = new Node[globalData.getNumberOfNodes()];
        this.elements = new Element[globalData.getNumberOfElements()];
    }

    public Node[] nodeBuilder() {
        double deltaX = (double) globalData.getWidth() / (double) (globalData.getNodeWidth() - 1); //lengths of elements
        double deltaY = (double) globalData.getHeight() / (double) (globalData.getNodeHeight() - 1);
        int nodeIndex = 0;
        for (int i = 0; i < globalData.getNodeWidth(); i++) {
            for (int j = 0; j < globalData.getNodeHeight(); j++) {
                double x = i * deltaX;
                double y = j * deltaY;
                boolean BC = isBoundaryCondition(x, y);
                nodes[nodeIndex] = new Node(nodeIndex + 1, x, y, 0, BC);
                nodeIndex++;
            }
        }
        return nodes;

    }

    public Element[] elementBuilder() {
        int elementIndex = 0;

        for (int i = 0; i < globalData.getNodeWidth() - 1; i++) {
            for (int j = 0; j < globalData.getNodeHeight() - 1; j++) {
                //TODO dodać flagę
                int[] ids = new int[4];
                ids[0] = elementIndex + 1; //bottom left
                ids[1] = ids[0] + globalData.getNodeHeight();//bottom right
                ids[2] = ids[1] + 1;//top right
                ids[3] = ids[0] + 1; //top left

                Element element = new Element(elementIndex + 1, ids);
                elements[elementIndex] = element;
                elementIndex++;
//                if (elementIndex % globalData.getNodeHeight() == 0) {
//                    elementIndex++;
//                }

            }


        }
        return elements;
    }


    public void gridBuilder() {
        nodeBuilder();
        elementBuilder();
    }

    public Element findElement(int elementIndex) {
        //TODO find id coords
        for (Element e : elements) {
            if (e.getElementIndex() == elementIndex) {
                return e;
            }
        }
        System.err.println("Element with this index does not exist");
        return null;
    }


    public GlobalData getGlobalData() {
        return globalData;
    }

    public void setGlobalData(GlobalData globalData) {
        this.globalData = globalData;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    public Element[] getElements() {
        return elements;
    }

    public void setElements(Element[] elements) {
        this.elements = elements;
    }


}
