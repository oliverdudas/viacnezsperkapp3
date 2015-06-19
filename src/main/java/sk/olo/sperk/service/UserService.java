package sk.olo.sperk.service;

import com.google.appengine.api.datastore.Entity;
import sk.olo.sperk.model.GalleryItemModel;
import sk.olo.sperk.model.UserModel;

import java.util.List;

/**
 * Created by olo on 17. 6. 2015.
 */
public interface UserService {
    UserModel getUserByUsername(String username);

    String putUser(UserModel userModel);

    UserModel getUser(String key);

    UserModel getFullUser(String key);

    List<UserModel> getUsers();

    String putGalleryItem(GalleryItemModel galleryItemModel);

    String storePerson();

    List<Entity> loadPersonByAncestor(String id);
}
