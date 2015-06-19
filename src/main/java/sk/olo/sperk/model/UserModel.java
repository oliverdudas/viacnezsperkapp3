package sk.olo.sperk.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

import java.util.Date;

/**
 * Created by olo on 17. 6. 2015.
 */
public class UserModel {

    public static final String KIND = "User";
    public static final String GALLERY_ITEM_KEY_NAME = "galleryItems";

    private String key;

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Long age;
    private Integer bornYear;
    private String residence;
    private String socialInfo;
    private Date created;
    private String createdBy;
    private Date modified;
    private String modifiedBy;
    private String mainURL;
    private Text content;

    public Entity toEntity(Key key) {
        Entity entity = new Entity(key);
        entity.setProperty("username", username);
        entity.setProperty("password", password);
        entity.setProperty("firstname", firstname);
        entity.setProperty("lastname", lastname);
        entity.setProperty("age", age);
        entity.setProperty("bornYear", bornYear);
        entity.setProperty("residence", residence);
        entity.setProperty("socialInfo", socialInfo);
        entity.setProperty("created", created);
        entity.setProperty("createdBy", createdBy);
        entity.setProperty("modified", modified);
        entity.setProperty("modifiedBy", modifiedBy);
        entity.setProperty("mainURL", mainURL);
        entity.setProperty("content", content);
        return entity;
    }

    public static UserModel createModel(Entity entity) {
        UserModel userModel = new UserModel();
        userModel.setKey(KeyFactory.keyToString(entity.getKey()));
        userModel.setUsername(entity.getProperty("username") != null ? (String) entity.getProperty("username") : null);
        userModel.setPassword(entity.getProperty("password") != null ? (String) entity.getProperty("password") : null);
        userModel.setFirstname(entity.getProperty("firstname") != null ? (String) entity.getProperty("firstname") : null);
        userModel.setLastname(entity.getProperty("lastname") != null ? (String) entity.getProperty("lastname") : null);
        userModel.setAge(entity.getProperty("age") != null ? (Long) entity.getProperty("age") : null);
        userModel.setBornYear(entity.getProperty("bornYear") != null ? (Integer) entity.getProperty("bornYear") : null);
        userModel.setResidence(entity.getProperty("residence") != null ? (String) entity.getProperty("residence") : null);
        userModel.setSocialInfo(entity.getProperty("socialInfo") != null ? (String) entity.getProperty("socialInfo") : null);
        userModel.setCreated(entity.getProperty("created") != null ? (Date) entity.getProperty("created") : null);
        userModel.setCreatedBy(entity.getProperty("createdBy") != null ? (String) entity.getProperty("createdBy") : null);
        userModel.setModified(entity.getProperty("modified") != null ? (Date) entity.getProperty("modified") : null);
        userModel.setModifiedBy(entity.getProperty("modifiedBy") != null ? (String) entity.getProperty("modifiedBy") : null);
        userModel.setMainURL(entity.getProperty("mainURL") != null ? (String) entity.getProperty("mainURL") : null);
        userModel.setContent(entity.getProperty("content") != null ? (Text) entity.getProperty("content") : null);
        return userModel;
    }

    public UserModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Integer getBornYear() {
        return bornYear;
    }

    public void setBornYear(Integer bornYear) {
        this.bornYear = bornYear;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getSocialInfo() {
        return socialInfo;
    }

    public void setSocialInfo(String socialInfo) {
        this.socialInfo = socialInfo;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getMainURL() {
        return mainURL;
    }

    public void setMainURL(String mainURL) {
        this.mainURL = mainURL;
    }

    public Text getContent() {
        return content;
    }

    public void setContent(Text content) {
        this.content = content;
    }

}
