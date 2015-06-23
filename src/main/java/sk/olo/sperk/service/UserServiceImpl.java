package sk.olo.sperk.service;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import sk.olo.sperk.model.GalleryItemModel;
import sk.olo.sperk.model.KeyModel;
import sk.olo.sperk.model.RoleModel;
import sk.olo.sperk.model.UserModel;
import sk.olo.sperk.persinstence.ToolsDatastore;
import sk.olo.sperk.persinstence.ToolsPersistence;

import java.util.ArrayList;
import java.util.Date;
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
    public String putRole(RoleModel roleModel) {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.putRole(roleModel);
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

    @Override
    public String createTestUser() {
        UserModel testUser = new UserModel();
        testUser.setUsername("test");
        testUser.setPassword("test");
        testUser.setContent(new Text("BLaaaaaaaaaaaaaaaaaaaaa laaaaaaaaaaa laaaaaaaaaaaa"));
        testUser.setAge(12L);
        testUser.setBornYear(2003L);
        testUser.setCreated(new Date());
        testUser.setCreatedBy("Admin");
        testUser.setFirstname("Lola");
        testUser.setLastname("Boba");
        testUser.setMainURL("https://lh4.googleusercontent.com/-tAdw_fwOze8/Utf1nVevFwI/AAAAAAAAGXs/-ksQSqXpKh0/amina%25252C%2525207y.JPG");
        testUser.setModified(new Date());
        testUser.setModifiedBy("Admin");
        testUser.setResidence("Mtopa");
        testUser.setSocialInfo("social info");

        ArrayList<GalleryItemModel> galleryItems = new ArrayList<GalleryItemModel>();

        GalleryItemModel item1 = new GalleryItemModel();
        item1.setImageUrl("https://lh3.googleusercontent.com/-vf-SoeYz_e0/Ut5B9oDy_pI/AAAAAAAAGe8/SqoUY0REeho/amina.JPG");
        item1.setThumbUrl("https://lh3.googleusercontent.com/-vf-SoeYz_e0/Ut5B9oDy_pI/AAAAAAAAGe8/SqoUY0REeho/s144/amina.JPG");
        item1.setGphotoId("aaaaaaaaaaaaa");
        item1.setIndex(5L);
        galleryItems.add(item1);

        GalleryItemModel item2 = new GalleryItemModel();
        item2.setImageUrl("https://lh5.googleusercontent.com/-2XBq5Kun_gg/UzUtW46AucI/AAAAAAAAHrs/eLSDpdVp8pM/our%252520kids%252520022.JPG");
        item2.setThumbUrl("https://lh5.googleusercontent.com/-2XBq5Kun_gg/UzUtW46AucI/AAAAAAAAHrs/eLSDpdVp8pM/s144/our%252520kids%252520022.JPG");
        item2.setGphotoId("aaaaaaaaaaaaa");
        item2.setIndex(5L);
        galleryItems.add(item2);

        testUser.setGalleryItems(galleryItems);

        return putFullUser(testUser);
    }

    @Override
    public String putFullUser(UserModel userModel) {
        ToolsPersistence persistence = new ToolsDatastore();
        return persistence.putFullUser(userModel);
    }
}
