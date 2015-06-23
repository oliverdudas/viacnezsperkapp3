package sk.olo.sperk.persinstence;

import com.google.appengine.api.datastore.Entity;
import sk.olo.sperk.model.GalleryItemModel;
import sk.olo.sperk.model.KeyModel;
import sk.olo.sperk.model.RoleModel;
import sk.olo.sperk.model.UserModel;

import java.util.List;

/**
 * Created by olo on 17. 6. 2015.
 */
public interface ToolsPersistence {
    UserModel getUserByUsername(String username);

    String putUser(UserModel userModel);

    UserModel getUser(String key);

    UserModel getFullUser(String key);

    List<UserModel> getUsers();

    String putGalleryItem(GalleryItemModel galleryItemModel);

    String putRole(RoleModel roleModel);

    String storePersonWithImages();

    List<Entity> loadPersonGroupByAncestor(String id);

    String putFullUser(UserModel userModel);
}
