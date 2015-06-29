package sk.olo.sperk.model;

/**
 * Created by olo on 18. 6. 2015.
 */
public class KeyModel {

    private String identifier;

    public KeyModel() {
    }

    public KeyModel(String key) {
        this.identifier = key;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String key) {
        this.identifier = key;
    }
}
