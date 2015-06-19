package sk.olo.sperk.model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

import java.util.Date;

/**
 * Created by olo on 19. 6. 2015.
 */
public class GalleryItemModel {

    public static final String KIND = "GalleryItem";

    private String parentKey;
    private String key;

    private String gphotoId;
    private Integer index;
    private String imageUrl;
    private String thumbUrl;

    public Entity toEntity(String parentKeyName) {
        if (parentKey == null) {
            throw new RuntimeException("MISSING_PARENT_KEY");
        }
        if (parentKeyName == null) {
            throw new RuntimeException("MISSING_PARENT_KEY_NAME");
        }
        Entity entity = new Entity(KIND, parentKeyName, KeyFactory.stringToKey(parentKey));
        entity.setProperty("gphotoId", gphotoId);
        entity.setProperty("index", index);
        entity.setProperty("imageUrl", imageUrl);
        entity.setProperty("thumbUrl", thumbUrl);
        return entity;
    }

    public static GalleryItemModel createModel(Entity entity) {
        GalleryItemModel itemModel = new GalleryItemModel();
        itemModel.setKey(KeyFactory.keyToString(entity.getKey()));
        itemModel.setParentKey(KeyFactory.keyToString(entity.getKey().getParent()));
        itemModel.setGphotoId(entity.getProperty("gphotoId") != null ? (String) entity.getProperty("gphotoId") : null);
        itemModel.setIndex(entity.getProperty("index") != null ? (Integer) entity.getProperty("index") : null);
        itemModel.setImageUrl(entity.getProperty("imageUrl") != null ? (String) entity.getProperty("imageUrl") : null);
        itemModel.setThumbUrl(entity.getProperty("thumbUrl") != null ? (String) entity.getProperty("thumbUrl") : null);
        return itemModel;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGphotoId() {
        return gphotoId;
    }

    public void setGphotoId(String gphotoId) {
        this.gphotoId = gphotoId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
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
}
