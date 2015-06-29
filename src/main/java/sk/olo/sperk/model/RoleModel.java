package sk.olo.sperk.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Created by olo on 21. 6. 2015.
 */
public class RoleModel {

    public static final String KIND = "Role";

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    private String parentKey;
    private String identifier;

    private String name;

    public RoleModel() {
    }

    public Entity toEntity() {
        if (parentKey == null) {
            throw new RuntimeException("MISSING_PARENT_KEY");
        }
        Entity entity = new Entity(KIND, KeyFactory.stringToKey(parentKey));
        entity.setProperty("name", name);
        return entity;
    }

    public static RoleModel createModel(Entity entity) {
        RoleModel roleModel = new RoleModel();
        roleModel.setIdentifier(KeyFactory.keyToString(entity.getKey()));
        roleModel.setParentKey(KeyFactory.keyToString(entity.getKey().getParent()));
        roleModel.setName(entity.getProperty("name") != null ? (String) entity.getProperty("name") : null);
        return roleModel;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String key) {
        this.identifier = key;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
