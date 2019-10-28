import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.Arrays;

public class Element {


    private int elementIndex;
    private int[] ids = new int[4];

    public int getElementIndex() {
        return elementIndex;
    }

    public void setElementIndex(int elementIndex) {
        this.elementIndex = elementIndex;
    }


    public Element(int elementIndex, int[] ids) {
        this.elementIndex = elementIndex;
        this.ids = ids;
    }

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }


    @Override
    public String toString() {
        return "Element " +
                elementIndex +
                ": nodes = " + Arrays.toString(ids);
    }
}
