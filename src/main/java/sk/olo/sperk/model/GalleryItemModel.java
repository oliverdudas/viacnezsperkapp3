package sk.olo.sperk.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Created by olo on 19. 6. 2015.
 */
public class GalleryItemModel {

    public static final String KIND = "GalleryItem";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_PUT = "put";

    private String parentKey;
    private String identifier;

    private String crudAction;

    private String gphotoId;
    private Long index;
    private String imageUrl;
    private String thumbUrl;
    private Long timestamp;

    public Entity toEntity() {
        if (parentKey == null) {
            throw new RuntimeException("MISSING_PARENT_KEY");
        }
        Entity entity = new Entity(KIND, KeyFactory.stringToKey(parentKey));
        entity.setProperty("gphotoId", gphotoId);
        entity.setProperty("index", index);
        entity.setProperty("imageUrl", imageUrl);
        entity.setProperty("thumbUrl", thumbUrl);
        entity.setProperty("timestamp", timestamp);
        return entity;
    }

    public static GalleryItemModel createModel(Entity entity) {
        GalleryItemModel itemModel = new GalleryItemModel();
        itemModel.setIdentifier(KeyFactory.keyToString(entity.getKey()));
        itemModel.setParentKey(KeyFactory.keyToString(entity.getKey().getParent()));
        itemModel.setGphotoId(entity.getProperty("gphotoId") != null ? (String) entity.getProperty("gphotoId") : null);
        itemModel.setIndex(entity.getProperty("index") != null ? (Long) entity.getProperty("index") : null);
        itemModel.setImageUrl(entity.getProperty("imageUrl") != null ? (String) entity.getProperty("imageUrl") : null);
        itemModel.setThumbUrl(entity.getProperty("thumbUrl") != null ? (String) entity.getProperty("thumbUrl") : null);
        itemModel.setTimestamp(entity.getProperty("timestamp") != null ? (Long) entity.getProperty("timestamp") : null);
        return itemModel;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String key) {
        this.identifier = key;
    }

    public String getGphotoId() {
        return gphotoId;
    }

    public void setGphotoId(String gphotoId) {
        this.gphotoId = gphotoId;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getCrudAction() {
        return crudAction;
    }

    public void setCrudAction(String crudAction) {
        this.crudAction = crudAction;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
