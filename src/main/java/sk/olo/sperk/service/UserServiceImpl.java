package sk.olo.sperk.service;

import com.google.appengine.api.datastore.Entity;
import sk.olo.sperk.model.GalleryItemModel;
import sk.olo.sperk.model.UserModel;
import sk.olo.sperk.persinstence.ToolsDatastore;
import sk.olo.sperk.persinstence.ToolsPersistence;

import java.util.List;

/**
 * Created by olo on 17. 6. 2015.
 */
public class UserServiceImpl implements UserService {

    @Override
    public UserModel getUserByUsername(String username) {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.getUserByUsername(username);
    }

    @Override
    public String putUser(UserModel userModel) {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.putUser(userModel);
    }

    @Override
    public UserModel getUser(String key) {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.getUser(key);
    }

    @Override
    public UserModel getFullUser(String key) {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.getFullUser(key);
    }

    @Override
    public List<UserModel> getUsers() {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.getUsers();
    }

    @Override
    public String putGalleryItem(GalleryItemModel galleryItemModel) {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.putGalleryItem(galleryItemModel);
    }

    @Override
    public String storePerson() {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.storePersonWithImages();
    }

    @Override
    public List<Entity> loadPersonByAncestor(String key) {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.loadPersonGroupByAncestor(key);
    }
}
