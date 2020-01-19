

import java.io.FileNotFoundException;

public class Grid {

    private GlobalData globalData;
    private Node[] nodes;
    private Element[] elements;


    private int isBoundaryCondition(double x, double y) {
        if (x == 0 || y == 0 || x == globalData.getWidth() || y == globalData.getHeight()) {
            return 1;
        } else {
            return 0;
        }
    }

    public Grid() throws FileNotFoundException {
        this.globalData = new GlobalData();
        this.nodes = new Node[GlobalData.getNumberOfNodes()];
        this.elements = new Element[globalData.getNumberOfElements()];
    }

    public Node[] nodeBuilder() {
        double deltaX = (double) globalData.getWidth() / (double) (globalData.getNodeWidth() - 1); //lengths of elements
        double deltaY = (double) globalData.getHeight() / (double) (globalData.getNodeHeight() - 1);


        int nodeIndex = 1;
        for (int i = 0; i < globalData.getNodeWidth(); i++) {
            for (int j = 0; j < globalData.getNodeHeight(); j++) {
                double x = i * deltaX;
                double y = j * deltaY;
                int BC = isBoundaryCondition(x, y);
                nodes[nodeIndex-1] = new Node(nodeIndex, x, y, 0, BC);
                nodeIndex++;
            }
        }
        return nodes;

    }

    public Element[] elementBuilder() {

        int index = 1;
        for (int i = 0; i < globalData.getNumberOfElements(); i++) {

            elements[i] = new Element(i + 1);

            elements[i].setNodes(
                    nodes[index-1],
                    nodes[index -1+ globalData.getNodeHeight()],
                    nodes[index -1+ globalData.getNodeHeight() + 1],
                    nodes[index]
            );
            index++;
            if (index % globalData.getNodeHeight() == 0) {
                index++;
            }
        }
//        for (Element e : elements) {
//            System.out.println("element No: " + e.getElementIndex()); //npe
//            for (int i = 0; i < e.getElementNodes().length; i++) {
//                System.out.println("Has nodes: Node id: " + e.getElementNodes()[i].getNodeIndex() + " coordinates: X: " + e.getElementNodes()[i].getX() + " Y: " + e.getElementNodes()[i].getY()+" Boundary condition: "+e.getElementNodes()[i].isBoundaryCondition()+" Temperature: "+e.getElementNodes()[i].getTemperature());
//            }
//        }
        return elements;

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
