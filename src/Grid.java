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
        int index = 1;
        int elementIndex = 1;
        for (int i = 0; i < globalData.getNumberOfElements(); i++) {
            elements[i] = new Element(elementIndex);
            elementIndex++;
            //System.out.println(elements[i].getElementIndex());
            elements[i].setNodesIDs(index, globalData.getNodeHeight());
            index++;
            if (index % globalData.getNodeHeight() == 0) {
                index++;
            }
        }
        return elements;

    }

    public void findElementCoordinates(int elementIndex) {
        for (Element e : elements) {
            if (e.getElementIndex() == elementIndex) {
                System.out.println(e); //?
                for (Node n : nodes) {
                    for (int i = 0; i < e.getNodesIDs().length; i++) {
                        if (e.getNodesIDs()[i] == n.getNodeIndex()) {
                            System.out.println(n.getNodeIndex() + " x: " + n.getX() + ", y: " + n.getY() +", boundary condition: "+n.isBoundaryCondition()); //TODO zmienic ale jak
                        }
                    }
                }

            }//TODO handle wrong ID error
        }
    }


    public void gridBuilder() {
        nodeBuilder();
        elementBuilder();
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
