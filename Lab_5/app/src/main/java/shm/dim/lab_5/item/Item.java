package shm.dim.lab_5.item;

public class Item {

    private String key;
    private String value;

    public Item() {
        key = new String();
        value = new String();
    }

    public Item(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}