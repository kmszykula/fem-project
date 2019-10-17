import java.util.Arrays;

public class Element {
    private int[] ids =new int[4];

    public Element(int[] ids) {
        this.ids = ids;
    }

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return Arrays.equals(ids, element.ids);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(ids);
    }

    @Override
    public String toString() {
        return "Element{" +
                "ids=" + Arrays.toString(ids) +
                '}';
    }
}
