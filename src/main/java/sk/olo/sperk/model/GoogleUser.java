package sk.olo.sperk.model;

/**
 * Created by olo on 18. 6. 2016.
 */
public class GoogleUser {

    private String id;
    private String email;
    private String name;
    private String given_name;
    private String hd;

    public GoogleUser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    @Override
    public String toString() {
        return "GoogleUser{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", given_name='" + given_name + '\'' +
                ", hd='" + hd + '\'' +
                '}';
    }
}
